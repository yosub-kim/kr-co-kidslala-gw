/*******************************************************************
 * ������ ����, ���������� ���� �߰� 
 *******************************************************************/

ALTER TABLE CMTABLEDATA ADD  SEQ VARCHAR(10)

ALTER TABLE SMROLE ADD  SEQ VARCHAR(10)

ALTER TABLE SMGROUP  ADD  SEQ VARCHAR(10)

ALTER TABLE ExpertPool  ADD  SEQ VARCHAR(10)
/*************************************************************************************************************************************/

ALTER VIEW dbo.SMUser
AS
SELECT	ssn AS id,
		userId as loginId, 
		password, 
		enable AS enabled, 
		name, 
		email, 
		companyPosition AS pos, 
		'' AS ssn, 
		remark AS description,
		seq 
FROM      ExpertPool




-- =============================================
-- Author:		<����ȣ>
-- Create date: 2009.08.03
-- Description:	 vUserInfo �� ����
-- =============================================
CREATE VIEW [dbo].[vUserInfo]
AS
SELECT     TOP (100) PERCENT E.userId, E.ssn, E.name AS userName, D.name AS posName, G.id AS groupId, G.name AS groupName, G.groupType, G.enabled, 
                      G.description AS groupDescription, G.parentId AS groupParentId, G.path AS groupPath, G.depth AS groupDepth, G.seq AS groupSeq, E.jobclass
FROM         MiracomBpmsDB.dbo.SMGroupUser AS U LEFT OUTER JOIN
                      MiracomBpmsDB.dbo.SMGroup AS G ON U.groupId = G.id LEFT OUTER JOIN
                      dbo.ExpertPool AS E ON U.userId = E.ssn LEFT OUTER JOIN
                      MiracomBpmsDB.dbo.SMUserRole AS R ON R.userId = U.userId LEFT OUTER JOIN
                      MiracomBpmsDB.dbo.SMRole AS D ON R.roleId = D.id
ORDER BY groupSeq

GO
-- =============================================
-- Author:		<����ȣ>
-- Create date: 2009.08.03
-- Description:	 vUserInfo �� ����
-- =============================================
ExpertPool ���̺�
[deptName] [varchar(100)] NULL
[companyPositionName] [varchar(100)] NULL

--2���� Į�� �߰�  ��  ���� ���� ����
-- ���� : SMGroup , SMRole ���̺��� �Ű������� Ȯ���Ѵ�.

UPDATE ExpertPool
	SET deptName = isNULL((select description from SMgroup WHERE id = dept),dept)
		, companyPositionName = isNull((select name  from SMRole WHERE id = companyPosition), companyPosition)


		
		
		
--SM Group User Mapping		

DROP TABLE smGroupUser

CREATE VIEW smGroupUser AS
select ssn as userId, dept as GroupId from expertpool 
where enable = '1'
and jobclass in ('A','J')


--SM User Role Mapping
DROP TABLE smUserRole

create view smUserRole as 
SELECT ssn as userId, companyPosition as roleId, '' assignerId, '' assignDate FROM EXPERTPOOL
where enable = '1'
and jobclass in ('A','J')

		
		