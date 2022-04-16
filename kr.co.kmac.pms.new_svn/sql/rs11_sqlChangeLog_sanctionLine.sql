/*******************************************************************
 * 결재선 관련 내용
 *******************************************************************/


CREATE TABLE dbo.SanctionLine(
	id varchar(56) NOT NULL,
	registerSsn varchar(13), 			
	registerDept varchar(13), 			
	registerName varchar(64),				
	teamManagerSsn varchar(13), 	
	teamManagerDept varchar(13), 	
	teamManagerName varchar(64), 	
	cfoSsn varchar(13), 
	cfoDept varchar(13), 
	cfoName varchar(64),		
	cioSsn varchar(13), 
	cioDept varchar(13), 
	cioName varchar(64),		
	ceoSsn varchar(13),	
	ceoDept varchar(13),	
	ceoName varchar(64),		
	assistant1Ssn varchar(13), 
	assistant1Dept varchar(13), 
	assistant1Name varchar(64), 
	assistant2Ssn varchar(13), 
	assistant2Dept varchar(13), 
	assistant2Name varchar(64), 
	modifyDate dateTime
	
	CONSTRAINT PK_SanctionLine PRIMARY KEY CLUSTERED 
	(
		id ASC
	)
) 

-- 검토, 승인
insert into sanctionLine
select	ssn as id, ssn as registerSsn, dept as registerDept, name as registerName,
		/*--------------------------------------------------------------------------------------------------------------------------*/
		isNull(
		(case companyPosition 
			when '10TM' then '' when '08CF' then '' when '09CJ' then '' when '07CC' then '' 
		 else
			(select ssn from expertPool where dept = e.dept and companyPosition = '10TM') 
		 end), '') as teamManagerSsn,	-- 검토자 ssn
		isNull(
		(case companyPosition 
			when '10TM' then '' when '08CF' then '' when '09CJ' then '' when '07CC' then '' 
		 else
			(select dept from expertPool where dept = e.dept and companyPosition = '10TM') 
		 end), '') as teamManagerDept,	-- 검토자 부서
		isNull(
		(case companyPosition 
			when '10TM' then '' when '08CF' then '' when '09CJ' then '' when '07CC' then '' 
		 else
			(select name from expertPool where dept = e.dept and companyPosition = '10TM') 
		 end), '') as teamManagerName,	-- 검토자 이름
		/*--------------------------------------------------------------------------------------------------------------------------*/
		isNull(
		(case companyPosition 
			when '08CF' then '' when '09CJ' then '' when '07CC' then '' 
			when '10CM' then (select ssn from expertPool where dept = e.dept and companyPosition = '06CA')			
		 else
			(select ssn from expertPool where left(dept,3) = left(e.dept,3) and companyPosition in ('08CF', '09CJ', '07CC')) 
		 end), '') as cfoSsn,	-- 승인자 ssn
		isNull(
		(case companyPosition 
			when '08CF' then '' when '09CJ' then '' when '07CC' then '' 
			when '10CM' then (select dept from expertPool where dept = e.dept and companyPosition = '06CA')
		 else
			(select dept from expertPool where left(dept,3) = left(e.dept,3) and companyPosition in ('08CF', '09CJ', '07CC')) 
		 end), '') as cfoDept,	-- 승인자 부서
		isNull(
		(case companyPosition 
			when '08CF' then '' when '09CJ' then '' when '07CC' then ''
			when '10CM' then (select name from expertPool where dept = e.dept and companyPosition = '06CA')
		 else
			(select name from expertPool where left(dept,3) = left(e.dept,3) and companyPosition in ('08CF', '09CJ', '07CC')) 
		 end), '') as cfoName,	-- 승인자 이름
		/*--------------------------------------------------------------------------------------------------------------------------*/
		'' as cioSsn, '' as cioDept, '' as cioName,
		(select ssn  from expertPool where name = '김종립') as ceoSsn,
		(select dept from expertPool where name = '김종립') as ceoDept,
		(select name from expertPool where name = '김종립') as ceoName
		,''[assistant1Ssn]
		,''[assistant1Dept]
		,''[assistant1Name]
		,''[assistant2Ssn]
		,''[assistant2Dept]
		,''[assistant2Name]
		,getDate() as modifyDate
from expertPool e
where jobClass = 'A'
and enable = '1' 
and companyPosition not in ('40AT', '61DT', '01HC', '42BT')
order by dept, companyPosition

-- C&C센터 센터장 겸직처리
update sanctionLine 
set cfoSsn = '6504051037212',
	cfoDept = '2120',
	cfoName = '한상록'
where left(registerDept, 3) = '237'

-- 협의
-- 전략/HR 담당
update sanctionLine
set [assistant1Ssn]  = (select ssn  from expertPool where dept = '2032' and enable = '1'),
	[assistant1Dept] = (select dept from expertPool where dept = '2032' and enable = '1'),
	[assistant1Name] = (select name from expertPool where dept = '2032' and enable = '1')
where left(registerDept, 3) = '211' or left(registerDept, 3) = '231' or left(registerDept, 3) = '215'

-- CS/마케팅 담당
update sanctionLine
set [assistant1Ssn]  = (select ssn  from expertPool where dept = '2033' and enable = '1'),
	[assistant1Dept] = (select dept from expertPool where dept = '2033' and enable = '1'),
	[assistant1Name] = (select name from expertPool where dept = '2033' and enable = '1')
where left(registerDept, 3) = '212' or left(registerDept, 3) = '213' or left(registerDept, 3) = '237' or left(registerDept, 3) = '214'

-- 생산혁신 담당
update sanctionLine
set [assistant1Ssn]  = (select ssn  from expertPool where dept = '2034' and enable = '1'),
	[assistant1Dept] = (select dept from expertPool where dept = '2034' and enable = '1'),
	[assistant1Name] = (select name from expertPool where dept = '2034' and enable = '1')
where left(registerDept, 3) = '216' or left(registerDept, 3) = '238' or left(registerDept, 3) = '239'

-- 인재개발 담당
update sanctionLine
set [assistant1Ssn]  = (select ssn  from expertPool where dept = '2035' and enable = '1'),
	[assistant1Dept] = (select dept from expertPool where dept = '2035' and enable = '1'),
	[assistant1Name] = (select name from expertPool where dept = '2035' and enable = '1')
where left(registerDept, 3) = '232' or left(registerDept, 3) = '236'  