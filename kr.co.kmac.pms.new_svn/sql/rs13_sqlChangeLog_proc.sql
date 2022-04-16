/*******************************************************************
 * 추가된 저장 프로시져	
 *******************************************************************/




create PROC [dbo].[sp_kmacPms_projectSalarySum]
@projectCode	varchar(7)
as
declare @SQL nvarchar(4000)
begin 
set @SQL = 'SELECT * 
			FROM (
					select	--projectCode, 
							--pm.projectName, 
							year + ''년 '' + month + ''월''as date, 
							--p.chargeSsn, 
							e.name as 이름,
							convert(bigint, sum(convert(money, amount))) as salarySum
					 from	ProjectTeachingFeeMDetail p, expertpool e, project pm
					 where	p.chargeSsn = e.ssn 
					  and	p.projectCode = pm.projectCode
					  and	p.projectCode = ''' + @projectCode + '''
					  and	approvalYn = ''Y''	
					  and	billSendYn = ''Y''
					group	by p.projectCode, p.year, p.month, p.chargeSsn, e.name
				  --order	by 2,3
			) t
			PIVOT (sum(salarySum) for date in ('

Declare @Header varchar(200)
set @Header = ''

DECLARE tnames_cursor CURSOR
FOR
	 select year + '년 ' + month + '월'as date
	 from ProjectTeachingFeeMDetail p
	where projectCode = @projectCode
	  and approvalYn = 'Y' 
	  and billSendYn = 'Y'
	 group by p.projectCode, p.year, p.month
OPEN tnames_cursor;

DECLARE @varHeaderDate varchar(12)
FETCH NEXT FROM tnames_cursor INTO @varHeaderDate
WHILE (@@FETCH_STATUS = 0)

BEGIN
 set @Header = @Header + '['+ @varHeaderDate + ']'
   FETCH NEXT FROM tnames_cursor INTO @varHeaderDate
END;

CLOSE tnames_cursor;
DEALLOCATE tnames_cursor;
set @Header = REPLACE(@Header,'][','],[')
set @SQL = @SQL + @Header + ')) as pvt'
print(@sql)
exec (@SQL)

end
