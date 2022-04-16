/*******************************************************************
 * �߰��� ����� �Լ�
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
-- Author:		<����ȣ>
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
-- Author:		<����ȣ>
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
-- Author:		<����ȣ>
-- Create date: 2009.07.23
-- Description:	 ������ ���̺��� seq Į���� ���� ����.
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
-- Author:		<����ȣ>
-- Create date: 2009.09.03
-- Description:	cmtabledata �� ���̺� �̸� �������� ����
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
-- Author:		<����ȣ>
-- Create date: 2009.09.03
-- Description:	 cbo �� ������ �������̺� ����.
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
-- Author:		<����ȣ>
-- Create date: 2009.09.03
-- Description:	 ���̰��� �� ����Ʈ�� ��������
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
-- Author:		<�ӿ���>
-- Create date: 2009.12.14
-- Description:	 �¶��� ���� ��Ͻ� ����Ͻ� ���� �ڵ� ���� �Լ�(�����Ǿ� �߰���)
-- =============================================
CREATE FUNCTION getConvertBusinessTypeCode_ERP  
(  
 @key1  VARCHAR(64)  
)  
RETURNS VARCHAR(64)  
AS  
BEGIN  
    
 -- ������ ���� ����  
 DECLARE @data1 VARCHAR(64)  
  
 -- Ű�� �ش��ϴ� TASKID �˻�  
 BEGIN  
  SELECT @data1 = (case @key1 when '01'   -- ����������  
            then 'BTA'  
          when '02'   -- �ؿ�������  
            then 'BTA'  
          when '36'   -- �系����(��)  
            then 'BTA'  
          when '37'   -- �系����(��)_�鼼  
            then 'BTA'  
          when '05'   -- �������  
            then 'BTB'  
          when '06'   -- �ؿܿ���  
            then 'BTF'  
          when '08'   -- ����ġ  
            then 'BTD'  
          when '16'   -- �������  
            then 'BTC'  
          when '03'   -- ��������(��)  
            then 'BTE'  
          when '04'   -- �系����(��)  
            then 'BTE'  
          when '07'   -- ��������  
            then 'BTE'  
          when '10'   -- ��������(��)_�鼼  
            then 'BTE'  
          when '18'   -- �系����(��)_�鼼  
            then 'BTE'  
          when '25'   -- ���̳�  
            then 'BTE'  
          when '26'   -- �������  
            then 'BTC'  
          when '29'   -- ��������(��Ÿ)  
            then 'BTE'  
          when '31'   -- ��������(��Ÿ)  
            then 'BTE'   
          when '34'   -- ��������(��)  
            then 'BTE'  
          when '35'   -- ��������(��)_�鼼  
            then 'BTE'  
          when '09'   -- ������Ŭ��  
            then 'BTI'  
          when '12'   -- �������  
            then 'BTG'  
          when '13'   -- �̳뺣�̼ǿ���  
            then 'BTG'  
          when '14'   -- ����  
            then 'BTG'  
          when '15'   -- �����Ǹ�  
            then 'BTG'  
          when '17'   -- C.E�����Ǹ�  
            then 'BTG'  
          when '22'   -- ����  
            then 'BTG'  
          when '23'   -- ����(�鼼)  
            then 'BTG'  
          when '11'   -- ����  
            then 'BTG'  
      end)      
 END  
 RETURN @data1  
END





-- =============================================
-- Author:		<�ӿ���>
-- Create date: 2009.12.14
-- Description:	 �¶��� ���� ��Ͻ� ��� ���� �ڵ� ���� �Լ�(�����Ǿ� �߰���)
-- =============================================
CREATE FUNCTION getConvertIndustryCode_ERP  
(  
 @key1  VARCHAR(64)  
)  
RETURNS VARCHAR(64)  
AS  
BEGIN  
    
 -- ������ ���� ����  
 DECLARE @data1 VARCHAR(64)  
  
 -- Ű�� �ش��ϴ� TASKID �˻�  
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