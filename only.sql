select * from MEMBER where userid like '%test%' or username like '%test%'
select * from member;

select * from follow;

select * from chat_message;
delete from chat_message;
alter table
   chat_message
rename column
 del_status 
TO
status;

select * from post;
select * from alarm;
select * from MEMBER where userid like '%test%' or username like '%test%'

insert into COMMENTS values('안녕','aaaa',33,sysdate,cid_seq.NEXTVAL,0,null);