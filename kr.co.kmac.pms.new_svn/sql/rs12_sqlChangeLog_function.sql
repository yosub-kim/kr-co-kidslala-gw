/*******************************************************************
 * 추가된 사용자 함수
 *******************************************************************/




CREATE FUNCTION [dbo].[getCmTableDataValue]
(
 @key1		VARCHAR(64),
 @tableName VARCHAR(64)
)
 RETURNS VARCHAR(64)
AS
BEGIN
  DECLARE @data1 VARCHAR(64)
  BEGIN
   SELECT @data1 = data_1
   FROM cmTableData
   WHERE table_name = @tableName
	 AND key_1 = @key1
   END
  RETURN @data1
 END

 
 
 
 CREATE FUNCTION [dbo].[getExpertPoolName]
(
 @ssn		VARCHAR(64)
)
 RETURNS VARCHAR(64)
AS
BEGIN
  DECLARE @name VARCHAR(64)
  BEGIN
   SELECT @name = name
   FROM expertPool
   WHERE ssn = @ssn
   END
  RETURN @name
 END
 
 
 
 
 
create FUNCTION [dbo].[getExpertCompanyPosition]
(	
	@ssn		VARCHAR(20)
 )
 RETURNS VARCHAR(56)
AS
BEGIN
 
  DECLARE @position VARCHAR(56)

  BEGIN
   SELECT @position = position
   FROM (
			select isNull(companyPosition, companyPositionname ) as position from expertpool
			where ssn = @ssn
   ) F
  END

  RETURN @position
 END


 
 
 

-- =============================================
-- Author:		<남민호>
-- Create date: 2009.07.08
-- Description:	New Key Value Return
-- =============================================
CREATE FUNCTION [dbo].[getNewNodeKey]
(
	-- Add the parameters for the function here
	@roleNum varchar(50)
)
RETURNS varchar(4)
AS
BEGIN
	-- Declare the return variable here
	DECLARE @nodeKey varchar(4)

	-- Add the T-SQL statements to compute the return value here
	SELECT @nodeKey = Right('0000' + CAST(ISNULL(MAX(cast(NodeKey as int)), 1)+1 AS varchar(4)),4) FROM RoleDetail WHERE roleNum = @roleNum

	-- Return the result of the function
	RETURN @nodeKey

END


-- =============================================
-- Author:		<남민호>
-- Create date: 2009.07.08
-- Description:	
-- =============================================
CREATE FUNCTION [dbo].[getNewOrdSeq]
(
	-- Add the parameters for the function here
	@pNodeKey varchar(4)
)
RETURNS int
AS
BEGIN
	-- Declare the return variable here
	DECLARE @ordSeq int

	-- Add the T-SQL statements to compute the return value here
	SELECT @ordSeq = (ISNULL(MAX(OrdSeq),0) + 1) FROM RoleDetail WHERE pNodeKey = @pNodeKey

	-- Return the result of the function
	RETURN @ordSeq

END



	
-- =============================================
-- Author:		<남민호>
-- Create date: 2009.07.23
-- Description:	 스케쥴 테이블의 seq 칼럽을 값을 생성.
-- =============================================
CREATE FUNCTION [dbo].[getScheduleSeq]
(
	-- Add the parameters for the function here
	@ssn varchar(50)
	, @year varchar(4)
	, @month varchar(2)
	, @day  varchar(2)
)
RETURNS int
AS
BEGIN
	-- Declare the return variable here
	DECLARE @Seq int

	-- Add the T-SQL statements to compute the return value here
	SELECT  @Seq = (IsNULL(MAX(seq),0) + 1) FROM schedule WHERE ssn = @ssn AND [year]=@year AND [month]=@month AND [day]=@day
	-- Return the result of the function
	RETURN @Seq

END




-- =============================================
-- Author:		<남민호>
-- Create date: 2009.09.03
-- Description:	cmtabledata 의 테이블 이름 기준으로 리턴
-- =============================================
CREATE FUNCTION dbo.ufnGetTable
(	
	-- Add the parameters for the function here
	@varTableName varchar(40)
)
RETURNS TABLE 
AS
RETURN 
(
select * from cmtabledata
WHERE TABLE_NAME = @varTableName
)
GO






-- =============================================
-- Author:		<남민호>
-- Create date: 2009.09.03
-- Description:	 cbo 와 조직을 맵핑테이블 리턴.
-- =============================================


ALTER FUNCTION [dbo].[ufnGetDept_Break_Down]
(	
	-- Add the parameters for the function here
)
RETURNS TABLE 
AS
RETURN 
(
select key_1 as seq, key_2 as cboCode, data_1 AS buCode, data_2 AS puCode from dbo.ufnGetTable('DEPT_BREAK_DOWN')
)

-- =============================================
-- Author:		<남민호>
-- Create date: 2009.09.03
-- Description:	 마이가젯 고객 리스트를 위한조건
-- =============================================


set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go

ALTER FUNCTION [dbo].[ufnGetMyGadgetCustomerCondition](@userSsn varchar(20))
RETURNS @retTable TABLE 
(
    -- Columns returned by the function
    DeptCode varchar(10)
)
AS 
begin

	declare @topDept varchar(10)
	SELECT  top 1 @topDept = SMGroup.id
	FROM       SMGroupUser INNER JOIN
				  SMGroup ON LEFT(SMGroupUser.groupId, 3) = LEFT(SMGroup.id, 3)
	WHERE     (SMGroupUser.userId = @userSsn)

	begin
		INSERT @retTable
			SELECT  SMGroup.id AS groupCode
			FROM    SMGroupUser INNER JOIN
					  SMGroup ON LEFT(SMGroupUser.groupId, 3) = LEFT(SMGroup.id, 3)
			WHERE  (SMGroupUser.userId = @userSsn)
		INSERT @retTable
			select cboCode from dbo.ufnGetDept_Break_Down() where (buCode = @topDept) OR (puCode = @topDept)
		INSERT @retTable
			select buCode from dbo.ufnGetDept_Break_Down() where (buCode = @topDept) OR (puCode = @topDept)
		INSERT @retTable
			select puCode from dbo.ufnGetDept_Break_Down() where (buCode = @topDept) OR (puCode = @topDept)
	end
	return
end



  
-- =============================================
-- Author:		<임영훈>
-- Create date: 2009.12.14
-- Description:	 온라인 예산 등록시 비즈니스 유형 코드 매핑 함수(누락되어 추가함)
-- =============================================
CREATE FUNCTION getConvertBusinessTypeCode_ERP  
(  
 @key1  VARCHAR(64)  
)  
RETURNS VARCHAR(64)  
AS  
BEGIN  
    
 -- 리턴할 변수 선언  
 DECLARE @data1 VARCHAR(64)  
  
 -- 키에 해당하는 TASKID 검색  
 BEGIN  
  SELECT @data1 = (case @key1 when '01'   -- 국내컨설팅  
            then 'BTA'  
          when '02'   -- 해외컨설팅  
            then 'BTA'  
          when '36'   -- 사내교육(컨)  
            then 'BTA'  
          when '37'   -- 사내교육(컨)_면세  
            then 'BTA'  
          when '05'   -- 진흥행사  
            then 'BTB'  
          when '06'   -- 해외연수  
            then 'BTF'  
          when '08'   -- 리서치  
            then 'BTD'  
          when '16'   -- 인증사업  
            then 'BTC'  
          when '03'   -- 공개교육(단)  
            then 'BTE'  
          when '04'   -- 사내교육(일)  
            then 'BTE'  
          when '07'   -- 국내연수  
            then 'BTE'  
          when '10'   -- 공개교육(단)_면세  
            then 'BTE'  
          when '18'   -- 사내교육(일)_면세  
            then 'BTE'  
          when '25'   -- 세미나  
            then 'BTE'  
          when '26'   -- 인증사업  
            then 'BTC'  
          when '29'   -- 공개교육(기타)  
            then 'BTE'  
          when '31'   -- 공개교육(기타)  
            then 'BTE'   
          when '34'   -- 공개교육(장)  
            then 'BTE'  
          when '35'   -- 공개교육(장)_면세  
            then 'BTE'  
          when '09'   -- 리더스클럽  
            then 'BTI'  
          when '12'   -- 광고대행  
            then 'BTG'  
          when '13'   -- 이노베이션월드  
            then 'BTG'  
          when '14'   -- 광고  
            then 'BTG'  
          when '15'   -- 도서판매  
            then 'BTG'  
          when '17'   -- C.E제작판매  
            then 'BTG'  
          when '22'   -- 출판  
            then 'BTG'  
          when '23'   -- 출판(면세)  
            then 'BTG'  
          when '11'   -- 출판  
            then 'BTG'  
      end)      
 END  
 RETURN @data1  
END





-- =============================================
-- Author:		<임영훈>
-- Create date: 2009.12.14
-- Description:	 온라인 예산 등록시 산업 유형 코드 매핑 함수(누락되어 추가함)
-- =============================================
CREATE FUNCTION getConvertIndustryCode_ERP  
(  
 @key1  VARCHAR(64)  
)  
RETURNS VARCHAR(64)  
AS  
BEGIN  
    
 -- 리턴할 변수 선언  
 DECLARE @data1 VARCHAR(64)  
  
 -- 키에 해당하는 TASKID 검색  
 BEGIN  
  SELECT @data1 = (case @key1 when '01' then 'ITA'  
          when '02' then 'ITB'  
          when '03' then 'ITC'  
          when '04' then 'ITD'  
          when '05' then 'ITE' end  
  )      
 END  
 RETURN @data1  
END