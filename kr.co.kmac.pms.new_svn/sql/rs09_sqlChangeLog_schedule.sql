/*******************************************************************
 * 일정 관리
 *******************************************************************/

ALTER TABLE Schedule ADD seq int NULL
ALTER TABLE Schedule ADD idx int IDENTITY(1,1) NOT NULL
ALTER TABLE Schedule ADD secretYN varchar(1) NULL


--
-- seq 칼럽에 값을 세팅.
--
UPDATE Schedule
SET 
   secretYN = 'N'
   ,seq = a.rowNumber
	from (
		select ssn,year,month, day, row_number() over(partition by ssn,year, month,day order by ssn,year,month,day,starthour) as rowNumber,content,startHour,startMin,endHour,endMin, idx
		from schedule
	) a
	inner join Schedule b on a.idx = b.idx
	
--
-- 추가했던 idx열을 제거 하고. (ssn,year,month,day,seq)로 그룹키 설정.
--	