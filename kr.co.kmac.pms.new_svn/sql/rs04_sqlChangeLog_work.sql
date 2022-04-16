/*******************************************************************
 * 업무 타입 관련
 *******************************************************************/
drop view wlmTask
drop view wlmTaskAssignee
drop view wlmTaskType 
/*************************************************************************************************************************************/

drop table [WorkType]
/*************************************************************************************************************************************/

CREATE TABLE [dbo].[WorkType](
	[id] [varchar](128) NOT NULL,
	[name] [varchar](256) NOT NULL,
	[formUrl] [varchar](256) NULL,
	[description] [text] NULL,
 CONSTRAINT [PK_WorkType] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

/*************************************************************************************************************************************/

insert into workType
select id, name, '', name from miracomBpmsdb.dbo.wlmtaskType


-------------

select * from (
select 'update ProjectScheduleM set contentId =''TASK'+taskid+''' where projectcode='''+projectcode+''' and workseq='''+ convert(varchar, workseq) + '''' as aa 
from ( 
select  
	(select top 1 workseq from OutputTemplateDetail a 
		where a.taskFormTypeId = c.taskFormTypeId and a.processTypeCode = c.processTypeCode) workseq
	, *
from (
	select 
	 (select b.processTypecode from project b where a.projectcode = b.projectcode )processTypecode
	,*
	from dbo.ProjectTaskFormData a
) c
) d
) aad where aa is not null


/****** 개체:  Table [dbo].[ProjectProgressContents]    스크립트 날짜: 12/31/2009 08:41:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
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

GO
SET ANSI_PADDING OFF

insert into ProjectProgressContents
	select 'TASK'+taskid, projectCode, '', subject, content, creatorId, createDAte
	from dbo.ProjectTaskFormData a
	
	
----------------content Id generate
update PROJECTsCHEDULEm  set contentid = convert(varchar,projectcode)
                                         + convert(varchar,  workseq) 
                                         + convert(varchar, orderseq) 
where contentid is null
	
	
	
	