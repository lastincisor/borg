CREATE TABLE `t` ( 
    `id` int(11) NOT NULL, 
    `a` int(11) DEFAULT NULL, 
    `b` int(11) DEFAULT NULL, 
    PRIMARY KEY (`id`), 
    KEY `a` (`a`), 
    KEY `b` (`b`) ) 
ENGINE=InnoDB;

delimiter ;; 
create procedure idata() 
begin declare i int; 
set i=1; while(i<=100000)do 
insert into t values(i, i, i); 
set i=i+1; end while; end;; 
delimiter ; 
call idata();


delete from t;

explain select * from t where a between 10000 and 20000;


CREATE TABLE `t1` ( `id` int(11) NOT NULL, `c` int(11) DEFAULT NULL, `d` int(11) DEFAULT NULL, PRIMARY KEY (`id`), KEY `c` (`c`) ) ENGINE=InnoDB;
insert into t1 values(0,0,0),(5,5,5), (10,10,10),(15,15,15),(20,20,20),(25,25,25);