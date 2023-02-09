select a.id,a.username,a.password,a.age,a.sex from user a where a.id > 10 and a.id < 50;

select us.subject,count(us.user_id),sum(us.score),avg(us.score),max(us.score),min(us.score) from user_score us
where us.score > 90 group by us.subject;

select *,rank() over (partition by subject order by score desc) as ranking from user_score;

select us.*,sum(us.score) over (order by us.id) as current_sum,
       avg(us.score) over (order by us.id) as current_avg,
       count(us.score) over (order by us.id) as current_count,
       max(us.score) over (order by us.id) as current_max,
       min(us.score) over (order by us.id) as current_min from user_score us;

select us.*,sum(us.score) over (order by us.id) as current_sum,
       avg(us.score) over (order by us.id) as current_avg,
       count(us.score) over (order by us.id) as current_count,
       max(us.score) over (order by us.id) as current_max,
       min(us.score) over (order by us.id) as current_min,
       u.username ,ua.address,CONCAT(u.username, "-" ,ua.address) as userinfo
from user_score us
left join user u on u.id = us.user_id
left join user_address ua on ua.id = us.user_id;

-- mysql
SELECT DISTINCT us.user_id,u.username ,ua.address,CONCAT(u.username, "-" ,ua.address) as userinfo,
                sum(us.score) from user_score us
    left join user u on u.id = us.user_id
    left join user_address ua on ua.id = us.user_id
group by us.user_id;
-- tidb
SELECT DISTINCT us.user_id,u.username ,ua.address,CONCAT(u.username, "-" ,ua.address) as userinfo,
                sum(us.score) from user_score us
left join user u on u.id = us.user_id
left join user_address ua on ua.id = us.user_id
group by us.user_id,u.username;

-- user score top 10
select a.subject,a.id,a.user_id,u.username, a.score,a.rownum from (
select id,user_id,subject,score,row_number() over (order by score desc) as rownum from user_score) as a
    left join user u on a.user_id = u.id
    inner join user_score as b on a.id=b.id where a.rownum<=10 order by a.rownum ;


-- 科目top10
select a.subject,a.id,a.score,a.rownum from (
    select id,subject,score,row_number() over (partition by subject order by score desc) as rownum from user_score) as a
    inner join user_score as b on a.id=b.id where a.rownum<=10 order by a.subject ;

-- 自身加签两行求平均
-- 自身加签两行求和
select *,u.username,ua.address,CONCAT(u.username, "-" ,ua.address) as userinfo,
       avg(us.score) over (order by us.id rows 2 preceding) as current_avg,
       sum(score) over (order by us.id rows 2 preceding) as current_sum from user_score us
left join user u on u.id = us.user_id
left join user_address ua on ua.id = us.user_id;


select a.id,a.username,a.password,a.age,a.sex from user a where a.id in (select user_id from user_score where score > 90);

select us.user_id,u.username,us.subject,us.score from user_score us
left join user u on u.id = us.user_id
where us.score > 90 group by us.user_id,us.subject,us.score;

select us.user_id,u.username,us.subject,us.score from user_score us
join user u on u.id = us.user_id
where us.score > 90 group by us.user_id,us.subject,us.score;

select a.id,a.username,a.password,a.age,a.sex,ad.address,CONCAT(a.username, "-" ,ad.address) as userinfo from user a
left join user_address ad on a.id = ad.user_id
where a.id > 10 and a.id < 50;

select a.id,a.username,a.password,a.age,a.sex,ad.score from user a
right join user_score ad on a.id = ad.user_id
where a.id > 10 and a.id < 50;

select a.id,a.username,a.password,a.age,a.sex,ad.score from user a
    left join user_score ad on a.id = ad.user_id
where a.id in (select user_id from user_score where score > 90 and score < 99 )
union
select a.id,a.username,a.password,a.age,a.sex,ad.score from user a
    left join user_score ad on a.id = ad.user_id
where a.id in (select user_id from user_score where score > 30 and score < 70 );

create procedure user_left_join_groupBy_pro()
begin
select users.subject,sum(users.score) from (
select us.user_id,u.username,us.subject,us.score
from user_score us
left join user u on u.id = us.user_id
where us.score > 90 ) as users
group by users.subject;
end

call user_left_join_groupBy_pro();
drop procedure user_left_join_groupBy_pro;


create procedure union_pro()
begin
select * from (select a.id,a.username,a.password,a.age,a.sex,ad.score
               from user a left join user_score ad on a.id = ad.user_id
               where a.id in (
                   select user_id
                   from user_score
                   where score > 90 and score < 99 order by ad.score desc,a.age)
               union
               select a.id,a.username,a.password,a.age,a.sex,ad.score
               from user a left join user_score ad on a.id = ad.user_id
               where a.id in (
                   select user_id
                   from user_score
                   where score > 30 and score < 70)) user_info order by user_info.score desc,user_info.age;
end;
call union_pro();
drop procedure union_pro;


create procedure user_right_join_pro()
begin
select a.id,a.username,a.password,a.age,a.sex,ad.score from user a right join user_score ad on a.id = ad.user_id where a.id > 10 and a.id < 50 order by ad.score desc,a.age ;
end

call user_right_join_pro();
drop procedure user_right_join_pro;


create procedure uuser_left_join_pro()
begin
select a.id,a.username,a.password,a.age,a.sex,ad.address,CONCAT(a.username, "-" ,ad.address) as userinfo from user a left join user_address ad on a.id = ad.user_id where a.id > 10 and a.id < 50  order by a.id;
end


select a.id,a.username,a.password,a.age,a.sex from user a where a.id in (select user_id from user_score where score > 90) order by a.age desc,a.id

    DROP table user;
DROP table user_score;
DROP table user_address;

CREATE TABLE `user` ( 
    `id` int(11) NOT NULL, 
    `username` VARCHAR(30) DEFAULT NULL, 
    `password` VARCHAR(30) DEFAULT NULL, 
    `age` int(11) NOT NULL, 
    `sex` int(11) NOT NULL, 
    PRIMARY KEY (`id`), 
    KEY `username` (`username`) ) 
    ENGINE=InnoDB;


CREATE TABLE `user_score` ( 
    `id` int(11) NOT NULL,
    `subject` int(11) NOT NULL,
    `user_id` int(11) NOT NULL, 
    `score` int(11) NOT NULL, 
    PRIMARY KEY (`id`) )
    ENGINE=InnoDB;

CREATE TABLE `user_address` ( 
    `id` int(11) NOT NULL, 
    `user_id` int(11) NOT NULL, 
    `address` VARCHAR(30) DEFAULT NULL, 
    PRIMARY KEY (`id`), 
    KEY `address` (`address`) ) 
    ENGINE=InnoDB;


CREATE TABLE `user_info` (
`id` int(11) NOT NULL,
`user_id` int(11) NOT NULL,
`username` VARCHAR(30) DEFAULT NULL,
`password` VARCHAR(30) DEFAULT NULL,
`age` int(11) NOT NULL,
`sex` int(11) NOT NULL,
`address` VARCHAR(30) DEFAULT NULL,
`subject_1` int(11) NOT NULL,
`score_1` int(11) NOT NULL,
`subject_2` int(11) NOT NULL,
`score_2` int(11) NOT NULL,
`subject_3` int(11) NOT NULL,
`score_3` int(11) NOT NULL,
`subject_4` int(11) NOT NULL,
`score_4` int(11) NOT NULL,
`subject_5` int(11) NOT NULL,
`score_5` int(11) NOT NULL,
PRIMARY KEY (`id`));

insert into user values(0, CONCAT("username-", 0),CONCAT("password-", 0),FLOOR( 15 + RAND() * 23),Mod(0,2));

create procedure test.sp_test() begin select@a; end;
show create procedure test.sp_test;

delimiter ;; 
create procedure insert_data() 
begin declare i int;
declare s_i int;
set s_i=1;
set i=1; while(i<=100)do
insert into user values(i, CONCAT("username-", i),CONCAT("password-", i),FLOOR( 15 + RAND() * 23),Mod(i,2));
insert into user_score values(s_i, 1, i, FLOOR( 40 + RAND() * 100));
set s_i=s_i+1;
insert into user_score values(s_i, 2, i, FLOOR( 40 + RAND() * 100));
set s_i=s_i+1;
insert into user_score values(s_i, 3, i, FLOOR( 40 + RAND() * 100));
set s_i=s_i+1;
insert into user_score values(s_i, 4, i, FLOOR( 40 + RAND() * 100));
set s_i=s_i+1;
insert into user_score values(s_i, 5, i, FLOOR( 40 + RAND() * 100));
set s_i=s_i+1;
insert into user_address values(i, i, CONCAT("useraddress-", i));
set i=i+1; end while; end;;
delimiter ;


call insert_data();

select * from information_schema.ROUTINES where ROUTINE_SCHEMA = 'test';

DROP PROCEDURE insert_data;
delete from user where id > 0;
delete from user_score where id > 0;
delete from user_address where id > 0;


create procedure user_score_rank_pro() begin select *,rank() over (partition by subject order by score desc) as ranking from user_score; end;
call user_score_rank_pro();

create procedure user_info_pro()
select rank() over (partition by user_info.subject_1 order by user_info.score_1 desc) as ranking,
avg(user_info.score_1) over (order by user_info.id rows 2 preceding) as current_avg,
sum(user_info.score_1) over (order by user_info.id rows 2 preceding) as current_sum,
sum(user_info.score_1) over (order by user_info.id) as score_1_sum,
avg(user_info.score_1) over (order by user_info.id) as score_1_avg,
count(user_info.score_1) over (order by user_info.id) as score_1_count,
max(user_info.score_1) over (order by user_info.id) as score_1_max,
min(user_info.score_1) over (order by user_info.id) as score_1_min,
user_info.* from (
select u.id,u.username,
       us1.subject as subject_1,us1.score as score_1,
       us2.subject as subject_2,us2.score as score_2,
       us3.subject as subject_3,us3.score as score_3,
       us4.subject as subject_4,us4.score as score_4,
       us5.subject as subject_5,us5.score as score_5,
       ua.address from user u
left join user_score us1 on us1.user_id = u.id and us1.subject = 1
left join user_score us2 on us2.user_id = u.id and us2.subject = 2
left join user_score us3 on us3.user_id = u.id and us3.subject = 3
left join user_score us4 on us4.user_id = u.id and us4.subject = 4
left join user_score us5 on us5.user_id = u.id and us5.subject = 5
left join test.user_address ua on u.id = ua.user_id
) as user_info;


create procedure build_user_info_pro()
begin
INSERT INTO user_info (id,user_id,username,password,age,sex,address,subject_1,score_1,subject_2,score_2,subject_3,score_3,subject_4,score_4,subject_5,score_5) SELECT * FROM (
select u.id,us1.user_id,u.username,u.password,u.age,u.sex,ua.address,
us1.subject as subject_1,us1.score as score_1,
us2.subject as subject_2,us2.score as score_2,
us3.subject as subject_3,us3.score as score_3,
us4.subject as subject_4,us4.score as score_4,
us5.subject as subject_5,us5.score as score_5
from user u
left join user_score us1 on us1.user_id = u.id and us1.subject = 1
left join user_score us2 on us2.user_id = u.id and us2.subject = 2
left join user_score us3 on us3.user_id = u.id and us3.subject = 3
left join user_score us4 on us4.user_id = u.id and us4.subject = 4
left join user_score us5 on us5.user_id = u.id and us5.subject = 5
left join test.user_address ua on u.id = ua.user_id
) as user_info;
end

drop procedure build_user_info_pro;

call build_user_info_pro();
call user_info_pro();

UPDATE user_info ui SET ui.username = (SELECT CONCAT(u.username, "-" ,ua.address) FROM user u left join user_address ua on u.id = ua.user_id WHERE u.id = 1) where ui.user_id = 1;

select * from user_info where user_id = 1;

select 'score_pro' as score_pro,us.subject,count(us.user_id),sum(us.score),avg(us.score),max(us.score),min(us.score) from user_score us where us.score > 90 group by us.subject;

select 'user_score_rank_pro' as user_score_rank_pro,user_score.*,rank() over (partition by subject order by score desc) as ranking from user_score;

select 'user_win_pro' as user_win_pro,us.*,sum(us.score) over (order by us.id) as current_sum,avg(us.score) over (order by us.id) as current_avg,count(us.score) over (order by us.id) as current_count,
       max(us.score) over (order by us.id) as current_max,min(us.score) over (order by us.id) as current_min from user_score us;

select 'user_win_join_pro' as user_win_join_pro,us.*,
       sum(us.score) over (order by us.id) as current_sum,
       avg(us.score) over (order by us.id) as current_avg,
       count(us.score) over (order by us.id) as current_count,
       max(us.score) over (order by us.id) as current_max,
       min(us.score) over (order by us.id) as current_min,
       u.username ,ua.address,CONCAT(u.username, "-" , ua.address) as userinfo from user_score us
left join user u on u.id = us.user_id
left join user_address ua on ua.id = us.user_id;

SELECT DISTINCT us.user_id,'user_join_groupBy_pro' as user_join_groupBy_pro,u.username ,ua.address,CONCAT(u.username, "-" ,ua.address) as userinfo,sum(us.score) from user_score us
left join user u on u.id = us.user_id left join user_address ua on ua.id = us.user_id group by us.user_id,u.username order by us.user_id


select 'user_score_top10_pro' as user_score_top10_pro,a.subject,a.id,a.user_id,u.username, a.score,a.rownum from (select id,user_id,subject,score,row_number() over (order by score desc) as rownum
                                                                                                                  from user_score) as a left join user u on a.user_id = u.id inner join user_score as b on a.id=b.id where a.rownum<=10 order by a.rownum;

select 'user_fun_pro' as user_fun_pro,us.*,u.username,ua.address,CONCAT(u.username, "-" ,ua.address) as userinfo,
avg(us.score) over (order by us.id rows 2 preceding) as current_avg, sum(score) over (order by us.id rows 2 preceding) as current_sum
from user_score us left join user u on u.id = us.user_id left join user_address ua on ua.id = us.user_id order by u.id;

select 'user_sub_sel_pro' as user_sub_sel_pro,a.id,a.username,a.password,a.age,a.sex from user a where a.id in (select user_id from user_score where score > 90) order by a.age desc,a.id;

select 'user_left_join_groupBy_pro' as user_left_join_groupBy_pro,users.subject,sum(users.score) from
(select us.user_id,u.username,us.subject,us.score from user_score us left join user u on u.id = us.user_id where us.score > 90 ) as users group by users.subject;

select 'user_join_pro' as user_join_pro,users.subject,sum(users.score) from (select us.user_id,u.username,us.subject,us.score
from user_score us join user u on u.id = us.user_id where us.score > 90 ) as users group by users.subject;

select 'user_left_join_pro' as user_left_join_pro,a.id,a.username,a.password,a.age,a.sex,ad.address,CONCAT(a.username, "-" ,ad.address) as userinfo
from user a left join user_address ad on a.id = ad.user_id where a.id > 10 and a.id < 50  order by a.id;

select 'user_right_join_pro' as user_right_join_pro,a.id,a.username,a.password,a.age,a.sex,ad.score from user a right join user_score ad on a.id = ad.user_id where a.id > 10 and a.id < 50 order by ad.score desc,a.age;

select 'union_pro' as union_pro,user_info.* from (select a.id,a.username,a.password,a.age,a.sex,ad.score from user a
left join user_score ad on a.id = ad.user_id where a.id in (
select user_id from user_score where score > 90 and score < 99 order by ad.score desc,a.age)
union
select a.id,a.username,a.password,a.age,a.sex,ad.score from user a
left join user_score ad on a.id = ad.user_id where a.id in (
select user_id from user_score where score > 30 and score < 70))
user_info order by user_info.score desc,user_info.age;

select 'user_top10_pro' as user_top10_pro,a.subject,a.id,a.score,a.rownum from
(select id,subject,score,row_number() over (partition by subject order by score desc) as rownum from user_score) as a
inner join user_score as b on a.id=b.id where a.rownum<=10 order by a.subject;

select 'user_info_pro' as user_info_pro,
       rank() over (partition by user_info.subject_1 order by user_info.score_1 desc) as ranking,
       avg(user_info.score_1) over (order by user_info.id rows 2 preceding) as current_avg,
       sum(user_info.score_1) over (order by user_info.id rows 2 preceding) as current_sum,
       sum(user_info.score_1) over (order by user_info.id) as score_1_sum,
       avg(user_info.score_1) over (order by user_info.id) as score_1_avg,
       count(user_info.score_1) over (order by user_info.id) as score_1_count,
       max(user_info.score_1) over (order by user_info.id) as score_1_max,
       min(user_info.score_1) over (order by user_info.id) as score_1_min,
       user_info.* from (select u.id,u.username,
                                us1.subject as subject_1,us1.score as score_1,
                                us2.subject as subject_2,us2.score as score_2,
                                us3.subject as subject_3,us3.score as score_3,
                                us4.subject as subject_4,us4.score as score_4,
                                us5.subject as subject_5,us5.score as score_5,
                                ua.address from user u
                                    left join user_score us1 on us1.user_id = u.id and us1.subject = 1
                                    left join user_score us2 on us2.user_id = u.id and us2.subject = 2
                                    left join user_score us3 on us3.user_id = u.id and us3.subject = 3
                                    left join user_score us4 on us4.user_id = u.id and us4.subject = 4
                                    left join user_score us5 on us5.user_id = u.id and us5.subject = 5
                                    left join test.user_address ua on u.id = ua.user_id) as user_info;
