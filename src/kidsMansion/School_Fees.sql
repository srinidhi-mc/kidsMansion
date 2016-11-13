select s.NAME, pd.TERM, pd.TOTAL, pd.STATUS, pd.BOOK from students s 
inner join payment_details pd on pd.STUDENT_ID = s.ID
and s.CLASS = 'NURSERY' and pd.TERM = 2 and s.ACTIVE =1 and s.YEAR = 15 order by s.NAME asc;

select NAME, ACTIVE from students where class = 'NURSERY' and Year = 15 and ACTIVE = 1 order by name asc;