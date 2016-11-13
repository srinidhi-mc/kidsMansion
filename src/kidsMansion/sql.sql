SELECT STU.ID, STU.NAME, STU.CLASS, STU.EMAIL_1, STU.EMAIL_2 from KM.STUDENTS STU 
where NAME like '%Ad%' and active =1 order by STU.NAME;

SELECT STU.ID, STU.NAME, STU.CLASS, STU.EMAIL_1, STU.EMAIL_2, PD.TOTAL,PD.PAID_DATE ,PD.TYPE, PD.STATUS, PD.MONTH, PD.TERM  FROM KM.STUDENTS STU INNER JOIN PAYMENT_DETAILS PD ON STU.ID = PD.STUDENT_ID  AND NAME like '%AADITHYA%'  and YEAR = 14 and active = 1 order by STATUS, NAME asc
select * from KM.payment_details;

SELECT STU.ID, STU.NAME, STU.CLASS, STU.EMAIL_1, STU.EMAIL_2, PD.TOTAL,PD.PAID_DATE ,PD.TYPE, PD.STATUS, PD.MONTH, PD.TERM 
FROM KM.STUDENTS STU INNER JOIN KM.PAYMENT_DETAILS PD ON STU.ID = PD.STUDENT_ID  AND NAME like '%Ad%' 
 and active = 1 order by STATUS, NAME asc
 
 SELECT STU.ID, STU.NAME, STU.CLASS, STU.EMAIL_1, STU.EMAIL_2, STU.* FROM KM.STUDENTS STU  where STU.CLASS ='DayCare' and active = 1 order by  NAME asc
 
 select * from students where class = 'DayCare';
 
 
 select name, email_1, EMAIL_2 from students where class = 'NURSERY' and YEAR= 16 and active = 1 order by name asc;
 
 -- update students set year = null where class = 'DayCare';

select * from KM.students;

select Name, Email_1, Email_2 from students where students.`YEAR` = 15 and class = 'UKG' and students.ACTIVE = 1 order by name asc;

SELECT STU.ID, STU.NAME, STU.CLASS, STU.EMAIL_1, STU.EMAIL_2 FROM KM.STUDENTS STU  where STU.CLASS ='DayCare' and active = 1 order by  NAME asc

-- update  KM.students set active = 1 where CLASS ='DayCare';

select * from expense;

select * from cheques;

SELECT LAST_INSERT_ID();

select max(id) from payment_details where payment_details.STUDENT_ID = 182;

SELECT * FROM payment_Details WHERE id IS NULL;

select * from payment_details where comments like '%153902%';

select * from students where id = 192;
-- delete from cheques;

--  INSERT INTO KM.EXPENSE (NAME,DESCRIPTION,MONTH,AMOUNT) VALUES ('Srinidhi','Salary','AUG',25000)
--  delete from 

select STUDENT_ID, MONTH, TOTAL, NAME   from KM.Payment_Details  Pay  
INNER JOIN  KM.STUDENTS STU on STU.ID = PAY.STUDENT_ID
where STATUS = 0 and UPPER(MONTH) = UPPER('') and UPPER(Pay.CLASS) = UPPER('DAYCARE') order by NAME asc;

select stu.name, pd.ACTIVITY,pd.CLASS,pd.COMMENTS, pd.STATUS FROM students stu 
inner join payment_details pd on pd.STUDENT_ID = stu.id where UPPER(Pd.CLASS) <> UPPER('DAYCARE') 
order by STU.CLASS,pd.status desc;

select sum(ACTIVITY) from payment_details where STATUS = 1 and UPPER(CLASS) <> UPPER('DAYCARE') and TERM=2;

select s.id, pd.id, s.NAME, pd.CLASS, pd.term from payment_details pd
right outer join students s on s.ID = pd.Student_id
where pd.CLASS = 'NURSERY' and s.id is null ;

select * from students where class = 'NURSERY' and Year =15;

select * from KM.MAIL_INFO WHERE MAIL_SENT = 'N'

select * from KM.Payment_details where total > 13000

select * from KM.Students where  UPPER(CLASS) <> UPPER('DAYCARE');

--update km.students set year = 14,  Active = 1 where UPPER(CLASS) <> UPPER('DAYCARE') ;
-- delete from KM.students;

select * from payment_details where student_id = 341

SELECT STU.ID, STU.NAME, STU.CLASS, STU.EMAIL_1, STU.EMAIL_2 FROM KM.STUDENTS STU   WHERE NAME like '%HEZ%' and active = 1 order by NAME asc


select s.NAME, s.CLASS,pd.ID, pd.STUDENT_ID, pd.CLASS, pd.TERM, pd.STATUS, pd.TYPE, pd.PAID_DATE, pd.MONTH,
pd.COMMENTS, pd.BOOK, pd.ACTIVITY, pd.TERM_FEES, pd.ADMISSION, pd.TRANSPORT, pd.DAYCARE, pd.ART, pd.DANCE,
pd.SNACKS, pd.LUNCH, pd.BF, pd.ADHOC, pd.OTHERS, pd.TOTAL,  pd.UNIFORM, pd.SENTMAIL from km.payment_details pd
inner join students s on s.ID = pd.STUDENT_ID where pd.comments like '%05141930001443%'


select s.NAME, s.CLASS,pd.ID, pd.STUDENT_ID, pd.CLASS, pd.TERM, pd.STATUS, pd.TYPE, pd.PAID_DATE, pd.MONTH,
pd.COMMENTS, pd.BOOK, pd.ACTIVITY, pd.TERM_FEES, pd.ADMISSION, pd.TRANSPORT, pd.DAYCARE, pd.ART, pd.DANCE,
pd.SNACKS, pd.LUNCH, pd.BF, pd.ADHOC, pd.OTHERS, pd.TOTAL,  pd.UNIFORM, pd.SENTMAIL from km.payment_details pd
inner join students s on s.ID = pd.STUDENT_ID where pd.TYPE = 'NETBANKING' and PD.CLASS <> 'DayCare'
and pd.TERM = 2 and S.YEAR = 15 order by  s.CLASS, s.name
;


select name, email_1, email_2 from students where class = 'Nursery' and YEAR= 16 and active = 1;

select * from students where email_1 like '%su%' or EMAIL_2 like '%su%';

-- update students set year = null where UPPER(CLASS) = UPPER('DAYCARE') and year is not null ;

-- INSERT INTO KM.STUDENTS (  NAME , CLASS, EMAIL_1, EMAIL_2, active , CREATED_DATE, UPDATED_DATE ) 
       VALUES ( UPPER('AA2'),'DayCare','srinidhi.mc@gmail.com','vinaya.srinidhi@gmail.com',16,1, NOW(), NOW() )