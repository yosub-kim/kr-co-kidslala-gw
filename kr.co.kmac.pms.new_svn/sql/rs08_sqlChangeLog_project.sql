/*******************************************************************
 * 프로젝트 관련 변경 내용
 *******************************************************************/

ALTER TABLE dbo.projectScheduleM ADD level int
ALTER TABLE dbo.projectScheduleM ADD contentId varchar(64)
ALTER TABLE dbo.projectScheduleM ADD workType int
ALTER TABLE dbo.projectScheduleM ADD orderSeq int
update projectScheduleM set level = 0
update projectScheduleM set workType = 0
ALTER TABLE dbo.projectScheduleM ADD parentWorkSeq int
/*************************************************************************************************************************************/

ALTER TABLE dbo.projectScheduleM ADD level int
update projectScheduleM set level = 0
ALTER TABLE dbo.projectScheduleM ADD parentWorkSeq int
/*************************************************************************************************************************************/

update projectScheduleM set orderSeq = workSeq
/*************************************************************************************************************************************/


CREATE TABLE [dbo].[projectScheduleChange](
	[projectCode] [varchar](7) NOT NULL,
	[changeScheduleSeq] [int] NOT NULL,
	[preStartDate] [varchar](8) NOT NULL,
	[preEndDate] [varchar](8) NOT NULL,
	[realStartDate] [varchar](8) NOT NULL,
	[realEndDate] [varchar](8) NOT NULL,
 CONSTRAINT [PK_projectScheduleChange] PRIMARY KEY CLUSTERED 
(
	[projectCode] ASC,
	[changeScheduleSeq] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

-- check 인력변동 관련 내용 체크할 것 yhyim
CREATE TABLE [dbo].[projectMemberAddChange](
	[projectCode] [varchar](7) COLLATE Korean_Wansung_CI_AS NULL,
	[seq] [int] NULL,
	[ssn] [varchar](50) COLLATE Korean_Wansung_CI_AS NULL,
	[name] [varchar](50) COLLATE Korean_Wansung_CI_AS NULL,
	[role] [varchar](50) COLLATE Korean_Wansung_CI_AS NULL,
	[cost] [varchar](50) COLLATE Korean_Wansung_CI_AS NULL,
	[trainingYn] [varchar](50) COLLATE Korean_Wansung_CI_AS NULL,
	[contributionCost] [varchar](50) COLLATE Korean_Wansung_CI_AS NULL,
	[resRate] [varchar](50) COLLATE Korean_Wansung_CI_AS NULL
) ON [PRIMARY]

CREATE TABLE [dbo].[projectMemberRunningChange](
	[projectCode] [varchar](7) COLLATE Korean_Wansung_CI_AS NULL,
	[seq] [int] NULL,
	[ssn] [varchar](50) COLLATE Korean_Wansung_CI_AS NULL,
	[name] [varchar](50) COLLATE Korean_Wansung_CI_AS NULL,
	[role] [varchar](50) COLLATE Korean_Wansung_CI_AS NULL,
	[trainingYn] [varchar](50) COLLATE Korean_Wansung_CI_AS NULL
) ON [PRIMARY]


/*************************************************************************************************************************************/


CREATE TABLE [dbo].[ProjectProgressContents](
	[contentId] [varchar](50) NOT NULL,
	[projectCode] [varchar](7) NOT NULL,
	[taskName] [varchar](256) NULL,
	[title] [varchar](256) NULL,
	[content] [varchar](max) NULL,
	[creatorId] [varchar](50) NULL,
	[createDate] [datetime] NULL,
 CONSTRAINT [PK_ProjectProgressContents] PRIMARY KEY CLUSTERED 
(
	[contentId] ASC,
	[projectCode] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]


/*************************************************************************************************************************************/


ALTER TABLE Project ADD  runningPuDivCode VARCHAR(10)

ALTER TABLE Project ADD  runningPuDeptCode VARCHAR(10)

ALTER TABLE Project ADD  entNo VARCHAR(18)

ALTER TABLE dbo.project ADD runningPuCode varchar(10)
ALTER TABLE dbo.project ADD runningGroupCode varchar(10)


 
/*************************************************************************************************************************************/
 
 
 
CREATE TABLE [dbo].[ProjectExpenseTempForSanction](
	[seq] [varchar](50) NOT NULL,
	[tempYear] [varchar](4) NOT NULL,
	[tempMonth] [varchar](2) NOT NULL,
	[tempName] [varchar](32) NOT NULL,
	[tempSsn] [varchar](32) NOT NULL,
	[tempTotalRealTimeSalary] [varchar](10) NULL,
	[tempProjectCode] [varchar](7) NULL,
	[tempProjectName] [varchar](128) NULL,
	[tempPreportCount] [varchar](10) NULL,
	[tempRealTimeSalaryEachProject] [varchar](10) NULL,
	[tempRealTimeSalaryEachProjectAddedBasicSalary] [varchar](10) NULL,
	[tempExecuted] [varchar](10) NULL,
	[tempPlaned] [varchar](10) NULL,
	[tempDept] [varchar](10) NULL,
	[tempSeq] [varchar](256) NULL,
	[tempIsHolding] [varchar](10) NULL,
 CONSTRAINT [PK_ProjectExpenseTempForSanction] PRIMARY KEY CLUSTERED 
(
	[seq] ASC,
	[tempYear] ASC,
	[tempMonth] ASC,
	[tempName] ASC,
	[tempSsn] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]


/*************************************************************************************************************************************/
ALTER TABLE Project ADD endTaskState VARCHAR(20)




