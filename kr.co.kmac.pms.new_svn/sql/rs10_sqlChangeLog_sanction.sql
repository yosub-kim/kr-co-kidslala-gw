/*******************************************************************
 * 결재관련 테이블 변경 내용 및 데이터 이관 
 *******************************************************************/
/*************************************************************************************************************************************/

drop table dbo.SanctionDocConfirm

drop table dbo.SanctionDocConfirmDef

drop table SanctionDoc

drop table dbo.SanctionDocTypeDef

/*************************************************************************************************************************************/


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SanctionTemplate](
	[approvalType] [varchar](100) NOT NULL,
	[approvalName] [varchar](100) NOT NULL,
	[hasRefMember] [bit] NOT NULL,
	[hasAssistMember] [bit] NOT NULL,
	[assistMemberCnt] [int] NULL,
	[hasSptTeamMng] [bit] NOT NULL,
	[sptTeamMngName] [varchar](50) NULL,
	[sptTeamMngDept] [varchar](50) NULL,
	[sptTeamMngSsn] [varchar](13) NULL,
	[hasCeo] [bit] NULL,
	[ceoName] [varchar](50) NULL,
	[ceoDept] [varchar](50) NULL,
	[ceoSsn] [varchar](13) NULL,
	[hasTeamManager] [bit] NULL,
	[hasCfo] [bit] NULL,
	[hasCio] [bit] NULL,
	[hasWholeApprove] [bit] NOT NULL,
	[hasAttach] [bit] NOT NULL,
	[templateText] [text] NULL,
	[createdDate] [datetime] NULL,
	[createUser] [varchar](50) NULL,
	[updatedDate] [datetime] NULL,
	[updateUser] [varchar](50) NULL,
	[useYn] [bit] NOT NULL,
 CONSTRAINT [PK_SanctionTemplate] PRIMARY KEY CLUSTERED 
(
	[approvalType] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
/*************************************************************************************************************************************/




SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Work](
	[id] [varchar](50) NOT NULL,
	[title] [varchar](256) NOT NULL,
	[assigneeId] [varchar](50) NOT NULL,
	[assignerId] [varchar](50) NOT NULL,
	[state] [varchar](50) NOT NULL,
	[type] [varchar](50) NOT NULL,
	[level] [varchar](50) NOT NULL,
	[refWorkId1] [varchar](50) NULL,
	[refWorkId2] [varchar](50) NULL,
	[refWorkId3] [varchar](50) NULL,
	[document] [text] NULL,
	[draftUserId] [varchar](50) NULL,
	[draftUserDept] [varchar](50) NULL,
	[draftDate] [datetime] NULL,
	[createDate] [datetime] NULL,
	[openDate] [datetime] NULL,
	[executeDate] [datetime] NULL,
 CONSTRAINT [PK_Work] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF



/*************************************************************************************************************************************/



SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SanctionDoc](
	[seq] [int] NOT NULL,
	[projectCode] [varchar](7) NOT NULL,
	[approvalType] [varchar](8) NOT NULL,
	[registerSsn] [varchar](13) NULL,
	[registerDept] [varchar](10) NULL,
	[registerDate] [datetime] NULL,
	[teamManagerSsn] [varchar](13) NULL,
	[teamManagerDept] [varchar](10) NULL,
	[teamManagerDate] [datetime] NULL,
	[cfoSsn] [varchar](13) NULL,
	[cfoDept] [varchar](10) NULL,
	[cfoDate] [datetime] NULL,
	[cioSsn] [varchar](13) NULL,
	[cioDept] [varchar](10) NULL,
	[cioDate] [datetime] NULL,
	[ceoSsn] [varchar](13) NULL,
	[ceoDept] [varchar](10) NULL,
	[ceoDate] [datetime] NULL,
	[assistant1Ssn] [varchar](13) NULL,
	[assistant1Dept] [varchar](10) NULL,
	[assistant1Date] [datetime] NULL,
	[assistant2Ssn] [varchar](13) NULL,
	[assistant2Dept] [varchar](10) NULL,
	[assistant2Date] [datetime] NULL,
	[assistant3Ssn] [varchar](13) NULL,
	[assistant3Dept] [varchar](10) NULL,
	[assistant3Date] [datetime] NULL,
	[assistant4Ssn] [varchar](13) NULL,
	[assistant4Dept] [varchar](10) NULL,
	[assistant4Date] [datetime] NULL,
	[subject] [varchar](500) NULL,
	[content] [text] NULL,
	[rejectReason] [text] NULL,
	[isWholeApproval] [varchar](10) NULL,
	[state] [varchar](50) NULL,
	[reject] [varchar](10) NULL,
	[registerName] [varchar](64) NULL,
	[teamManagerName] [varchar](64) NULL,
	[cfoName] [varchar](64) NULL,
	[cioName] [varchar](64) NULL,
	[ceoName] [varchar](64) NULL,
	[assistant1Name] [varchar](64) NULL,
	[assistant2Name] [varchar](64) NULL,
	[assistant3Name] [varchar](64) NULL,
	[assistant4Name] [varchar](64) NULL,
	[workType] [varchar](64) NULL,
 CONSTRAINT [PK_SanctionDoc_1] PRIMARY KEY CLUSTERED 
(
	[seq] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF



/*************************************************************************************************************************************/




SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SanctionDocCC](
	[seq] [int] NOT NULL,
	[refMemberSsn] [varchar](13) NOT NULL,
	[refMemberName] [varchar](32) NULL,
	[draftRefMailSended] [bit] NULL,
	[approveRefMailSended] [bit] NULL,
	[approveCheckedDate] [datetime] NULL,
 CONSTRAINT [PK_SanctionDocCC] PRIMARY KEY CLUSTERED 
(
	[seq] ASC,
	[refMemberSsn] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[SanctionDocCC]  WITH CHECK ADD  CONSTRAINT [FK_SanctionDocCC_SanctionDoc] FOREIGN KEY([seq])
REFERENCES [dbo].[SanctionDoc] ([seq])
GO
ALTER TABLE [dbo].[SanctionDocCC] CHECK CONSTRAINT [FK_SanctionDocCC_SanctionDoc]



/*************************************************************************************************************************************/




INSERT INTO SanctionDoc
SELECT seq, projectCode, approvalType
      ,registerSsn, NULL as registerDept, registerDate
      ,teamManagerSsn, NULL as teamManagerDept, teamManagerDate
      ,cfoSsn, NULL as cfoDept, cfoDate
      ,'' as cioSsn, NULL as cioDept, '' as cioDate
      ,ceoSsn, NULL as ceoDept, ceoDate
      ,assistant1Ssn, (select dept from expertpool where ssn = assistant1Ssn) assistant1Dept, assistant1Date
      ,assistant2Ssn, (select dept from expertpool where ssn = assistant2Ssn) assistant2Dept, assistant2Date
      ,assistant3Ssn, (select dept from expertpool where ssn = assistant3Ssn) assistant3Dept, assistant3Date
      ,assistant4Ssn, (select dept from expertpool where ssn = assistant4Ssn) assistant4Dept, assistant4Date
      ,subject ,content ,rejectReason ,isWholeApproval ,state ,reject
	  ,(select name from expertpool where ssn = registerSsn) registerName
	  ,(select name from expertpool where ssn = teamManagerSsn) teamManagerName
	  ,(select name from expertpool where ssn = cfoSsn) cfoName
	  ,'' as cioName
	  ,(select name from expertpool where ssn = ceoSsn) ceoName
	  ,(select name from expertpool where ssn = assistant1Ssn) assistant1Name
	  ,(select name from expertpool where ssn = assistant2Ssn) assistant2Name
	  ,(select name from expertpool where ssn = assistant3Ssn) assistant3Name
	  ,(select name from expertpool where ssn = assistant4Ssn) assistant4Name
	  ,'S018809e0a4c4436010a4ead57e4001f' as workType
  FROM ProjectApprovalHeaderDoc a

/*************************************************************************************************************************************/


--update sanctionDoc set workType = 'S018809e0a4c4436010a4ead57e4001f'
UPDATE sanctionDoc SET workType = 'S028809e0a4c4436010a4ead57e4001f' WHERE approvalType IN ('A', 'PA')--실행
UPDATE sanctionDoc SET workType = 'S038809e0a4c4436010a4ead57e4001f' WHERE approvalType LIKE 'B%'--운영
UPDATE sanctionDoc SET workType = 'S048809e0a4c4436010a4ead57e4001f' WHERE approvalType LIKE 'S%'--일정
UPDATE sanctionDoc SET workType = 'S058809e0a4c4436010a4ead57e4001f' WHERE approvalType LIKE 'M%'--인력


UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_COMPLETE'	WHERE [state] = 'APPROVAL_COMPLETE'	-- 종료
UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_COMPLETE'	WHERE right([state],1) = '3'	-- 종료
UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_APPROVE'	WHERE right([state],1) = '2'	-- 승인
UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_REVIEW'	WHERE right([state],1) = '1'	-- 검토
UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_DRAFT'	WHERE right([state],1) = '0'	-- 기안

UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_DRAFT'	WHERE [state] = '4028809e0a4c4436010a4eab4b7a0019'	-- 기안
UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_REVIEW'	WHERE [state] = '4028809e0a4c4436010a4eabc54e001a'	-- 검토
UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_APPROVE'	WHERE [state] = '4028809e0a4c4436010a4eac2753001b'	-- 승인
UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_ASSIST1'	WHERE [state] = '4028809e0a4c4436010a4eac9e39001c'	-- 협의1
UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_ASSIST2'	WHERE [state] = '4028809e0a4c4436010a4eacf84f001d'	-- 협의2
UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_ASSIST3'	WHERE [state] = '4028809e0a4c4436010a4ead57e4001e'	-- 협의3
UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_ASSIST4'	WHERE [state] = '4028809e0a4c4436010a4eadb42d001f'	-- 협의4
UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_CEO'		WHERE [state] = '4028809e0a4c4436010a4eae1b340020'	-- ceo

UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_DRAFT'	WHERE [state] = '8028809e0a4c4436010a4eab4b7a0019'	-- 기안
UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_REVIEW'	WHERE [state] = '8028809e0a4c4436010a4eabc54e001a'	-- 검토
UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_APPROVE'	WHERE [state] = '8028809e0a4c4436010a4eac2753001b'	-- 승인
UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_ASSIST1'	WHERE [state] = '8028809e0a4c4436010a4eacf84f001d'	-- 협의1
UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_ASSIST2'	WHERE [state] = '8028809e0a4c4436010a4ead57e4001e'	-- 협의2
UPDATE sanctionDoc	SET [state] = 'SANCTION_STATE_CEO'		WHERE [state] = '8028809e0a4c4436010a4eac9e39001c'	-- ceo
  

UPDATE sanctionDoc SET registerDate = NULL 		WHERE registerDate = '1900-01-01 00:00:00.000'
UPDATE sanctionDoc SET teamManagerDate = NULL	WHERE teamManagerDate = '1900-01-01 00:00:00.000'
UPDATE sanctionDoc SET cfoDate = NULL 			WHERE cfoDate = '1900-01-01 00:00:00.000'
UPDATE sanctionDoc SET assistant1Date = NULL	WHERE assistant1Date = '1900-01-01 00:00:00.000'
UPDATE sanctionDoc SET assistant2Date = NULL	WHERE assistant2Date = '1900-01-01 00:00:00.000'
UPDATE sanctionDoc SET assistant3Date = NULL	WHERE assistant3Date = '1900-01-01 00:00:00.000'
UPDATE sanctionDoc SET assistant4Date = NULL	WHERE assistant4Date = '1900-01-01 00:00:00.000'
UPDATE sanctionDoc SET ceoDate = NULL 			WHERE ceoDate = '1900-01-01 00:00:00.000'
