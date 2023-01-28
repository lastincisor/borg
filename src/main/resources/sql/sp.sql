BEGIN
    DECLARE json_objects LONGTEXT;
    -- Do not track the current thread, it will kill the stack
    UPDATE performance_schema.threads
       SET instrumented = 'NO'
     WHERE processlist_id = CONNECTION_ID();
    SET SESSION group_concat_max_len=@@global.max_allowed_packet;
    -- Select the entire stack of events
    SELECT GROUP_CONCAT(CONCAT( '{'
              , CONCAT_WS( ', '
              , CONCAT('"nesting_event_id": "', IF(nesting_event_id IS NULL, '0', nesting_event_id), '"')
              , CONCAT('"event_id": "', event_id, '"')
              -- Convert from picoseconds to microseconds
              , CONCAT( '"timer_wait": ', ROUND(timer_wait/1000000, 2))  
              , CONCAT( '"event_info": "'
                  , CASE
                        WHEN event_name NOT LIKE 'wait/io%' THEN REPLACE(SUBSTRING_INDEX(event_name, '/', -2), '\', '\\')
                        WHEN event_name NOT LIKE 'wait/io/file%' OR event_name NOT LIKE 'wait/io/socket%' THEN REPLACE(SUBSTRING_INDEX(event_name, '/', -4), '\', '\\')
                        ELSE event_name
                    END
                  , '"'
              )
              -- Always dump the extra wait information gathered for statements
              , CONCAT( '"wait_info": "', IFNULL(wait_info, ''), '"')
              -- If debug is enabled, add the file:lineno information for waits
              , CONCAT( '"source": "', IF(true AND event_name LIKE 'wait%', IFNULL(wait_info, ''), ''), '"')
              -- Depending on the type of event, name it appropriately
              , CASE 
                     WHEN event_name LIKE 'wait/io/file%'      THEN '"event_type": "io/file"'
                     WHEN event_name LIKE 'wait/io/table%'     THEN '"event_type": "io/table"'
                     WHEN event_name LIKE 'wait/io/socket%'    THEN '"event_type": "io/socket"'
                     WHEN event_name LIKE 'wait/synch/mutex%'  THEN '"event_type": "synch/mutex"'
                     WHEN event_name LIKE 'wait/synch/cond%'   THEN '"event_type": "synch/cond"'
                     WHEN event_name LIKE 'wait/synch/rwlock%' THEN '"event_type": "synch/rwlock"'
                     WHEN event_name LIKE 'wait/lock%'         THEN '"event_type": "lock"'
                     WHEN event_name LIKE 'statement/%'        THEN '"event_type": "stmt"'
                     WHEN event_name LIKE 'stage/%'            THEN '"event_type": "stage"'
                     WHEN event_name LIKE '%idle%'             THEN '"event_type": "idle"'
                     ELSE '' 
                END                   
            )
            , '}'
          )
          ORDER BY event_id ASC SEPARATOR ',') event
    INTO json_objects
    FROM (
          -- Select all statements, with the extra tracing information available
          (SELECT thread_id, event_id, event_name, timer_wait, timer_start, nesting_event_id, 
                  CONCAT(sql_text, '\n',
                         'errors: ', errors, '\n',
                         'warnings: ', warnings, '\n',
                         'lock time: ', ROUND(lock_time/1000000, 2),'us\n',
                         'rows affected: ', rows_affected, '\n',
                         'rows sent: ', rows_sent, '\n',
                         'rows examined: ', rows_examined, '\n',
                         'tmp tables: ', created_tmp_tables, '\n',
                         'tmp disk tables: ', created_tmp_disk_tables, '\n',
                         'select scan: ', select_scan, '\n',
                         'select full join: ', select_full_join, '\n',
                         'select full range join: ', select_full_range_join, '\n',
                         'select range: ', select_range, '\n',
                         'select range check: ', select_range_check, '\n', 
                         'sort merge passes: ', sort_merge_passes, '\n',
                         'sort rows: ', sort_rows, '\n',
                         'sort range: ', sort_range, '\n',
                         'sort scan: ', sort_scan, '\n',
                         'no index used: ', IF(no_index_used, 'TRUE', 'FALSE'), '\n',
                         'no good index used: ', IF(no_good_index_used, 'TRUE', 'FALSE'), '\n'
                         ) AS wait_info
             FROM performance_schema.events_statements_history_long WHERE thread_id = thd_id)
          UNION 
          -- Select all stages
          (SELECT thread_id, event_id, event_name, timer_wait, timer_start, nesting_event_id, null AS wait_info
             FROM performance_schema.events_stages_history_long WHERE thread_id = thd_id) 
          UNION
          -- Select all events, adding information appropriate to the event
          (SELECT thread_id, event_id, 
                  CONCAT(event_name , 
                         IF(event_name NOT LIKE 'wait/synch/mutex%', IFNULL(CONCAT(' - ', operation), ''), ''), 
                         IF(number_of_bytes IS NOT NULL, CONCAT(' ', number_of_bytes, ' bytes'), ''),
                         IF(event_name LIKE 'wait/io/file%', '\n', ''),
                         IF(object_schema IS NOT NULL, CONCAT('\nObject: ', object_schema, '.'), ''), 
                         IF(object_name IS NOT NULL, 
                            IF (event_name LIKE 'wait/io/socket%',
                                -- Print the socket if used, else the IP:port as reported
                                CONCAT(IF (object_name LIKE ':0%', @@socket, object_name)),
                                object_name),
                            ''),
                         IF(index_name IS NOT NULL, CONCAT(' Index: ', index_name), ''),'\n'
                         ) AS event_name,
                  timer_wait, timer_start, nesting_event_id, source AS wait_info
             FROM performance_schema.events_waits_history_long WHERE thread_id = thd_id)) events 
    ORDER BY event_id;
    RETURN CONCAT('{', 
                  CONCAT_WS(',', 
                            '"rankdir": "LR"',
                            '"nodesep": "0.10"',
                            CONCAT('"stack_created": "', NOW(), '"'),
                            CONCAT('"mysql_version": "', VERSION(), '"'),
                            CONCAT('"mysql_user": "', CURRENT_USER(), '"'),
                            CONCAT('"events": [', IFNULL(json_objects,''), ']')
                           ),
                  '}');
END



BEGIN    
 -- Set configuration options     
 IF (@sys.debug IS NULL) THEN         
 SET @sys.debug = sys.sys_get_config('debug', 'OFF');     
 END IF;     
 -- Verify the query exists     
 -- The shortest possible query is "DO 1"     
 IF (in_query IS NULL OR LENGTH(in_query) < 4) THEN        
 SIGNAL SQLSTATE '45000'           
 SET MESSAGE_TEXT = "The @sys.execute_prepared_stmt.sql must contain a query";     
 END IF;     
 SET @sys.execute_prepared_stmt.sql = in_query;     
 IF (@sys.debug = 'ON') THEN         
 SELECT @sys.execute_prepared_stmt.sql AS 'Debug';     
 END IF;     
 PREPARE sys_execute_prepared_stmt FROM @sys.execute_prepared_stmt.sql;     
 EXECUTE sys_execute_prepared_stmt;     
 DEALLOCATE PREPARE sys_execute_prepared_stmt;     
 SET @sys.execute_prepared_stmt.sql = NULL; 
 END