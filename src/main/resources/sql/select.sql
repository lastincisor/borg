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


