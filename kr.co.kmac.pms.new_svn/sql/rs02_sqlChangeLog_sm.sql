/*********************************************************
 * SM 관련
 *********************************************************/

-----------------------------------------------------------
--SM 관련 과거 view 삭제
-----------------------------------------------------------
drop view smgroup
drop view smGroupUser
drop view SmRole 
drop view smUser

drop table dbo.smgroup
drop table dbo.SMGroupUser
drop table dbo.SMRole
drop table dbo.SMUserRole

-----------------------------------------------------------
--SM 관련 과거 테이블 생성
-----------------------------------------------------------

/****** 개체:  Table [dbo].[SMGroup]    스크립트 날짜: 12/13/2009 17:36:28 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SMGroup](
	[id] [varchar](50) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[groupType] [int] NOT NULL,
	[enabled] [varchar](1) NOT NULL,
	[memberRule] [varchar](1000) NULL,
	[description] [varchar](1000) NULL,
	[parentId] [varchar](50) NULL,
	[path] [varchar](1000) NULL,
	[depth] [int] NULL,
	[seq] [int] NULL,
 CONSTRAINT [PK_SMGroup] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** 개체:  Table [dbo].[SMGroupUser]    스크립트 날짜: 12/13/2009 17:36:28 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SMGroupUser](
	[groupId] [varchar](50) NOT NULL,
	[userId] [varchar](50) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** 개체:  Table [dbo].[SMUserRole]    스크립트 날짜: 12/13/2009 17:36:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SMUserRole](
	[userId] [varchar](50) NOT NULL,
	[roleId] [varchar](50) NOT NULL,
	[assignerId] [varchar](50) NULL,
	[assignDate] [datetime] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** 개체:  Table [dbo].[SMRole]    스크립트 날짜: 12/13/2009 17:36:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SMRole](
	[id] [varchar](50) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[enable] [varchar](1) NOT NULL,
	[description] [varchar](1000) NULL,
	[rate] [varchar](10) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO

-----------------------------------------------------------
--SM 관련 과거 데이터 import
-----------------------------------------------------------
insert into SMGroup
select * from miracomBpmsDb.dbo.smGroup

insert into SMRole
select * from miracomBpmsDb.dbo.smRole

insert into SMGroupUser
select * from miracomBpmsDb.dbo.SMGroupUser

insert into SMUserRole
select * from miracomBpmsDb.dbo.SMUserRole

