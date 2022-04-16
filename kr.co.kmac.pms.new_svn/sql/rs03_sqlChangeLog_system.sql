/*******************************************************************
 * 시스템 관리
 *******************************************************************/

CREATE TABLE [dbo].[RoleDetail](
	[roleNum] [varchar](50) COLLATE Korean_Wansung_CI_AS NOT NULL,
	[nodeKey] [varchar](4) COLLATE Korean_Wansung_CI_AS NOT NULL,
	[menuNum] [varchar](50) COLLATE Korean_Wansung_CI_AS NULL,
	[ordSeq] [nchar](10) COLLATE Korean_Wansung_CI_AS NULL,
	[pNodeKey] [varchar](4) COLLATE Korean_Wansung_CI_AS NULL,
 CONSTRAINT [PK_RoleDetail] PRIMARY KEY CLUSTERED 
(
	[roleNum] ASC,
	[nodeKey] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [IX_RoleDetail] UNIQUE NONCLUSTERED 
(
	[roleNum] ASC,
	[nodeKey] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[RoleDetail]  WITH NOCHECK ADD  CONSTRAINT [FK_RoleDetail_Menu] FOREIGN KEY([menuNum])
REFERENCES [dbo].[Menu] ([menuNum])
GO
ALTER TABLE [dbo].[RoleDetail] CHECK CONSTRAINT [FK_RoleDetail_Menu]
GO
ALTER TABLE [dbo].[RoleDetail]  WITH CHECK ADD  CONSTRAINT [FK_RoleDetail_Role] FOREIGN KEY([roleNum])
REFERENCES [dbo].[Role] ([roleNum])





-- =============================================
-- Author:		<남민호>
-- Create date: 2009.08.16
-- Description:	 GadGet 테이블 생성
-- =============================================

/****** 개체:  Table [dbo].[GadGet]    스크립트 날짜: 08/16/2009 15:45:03 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[GadGet](
	[gadgetId] [varchar](3) COLLATE Korean_Wansung_CI_AS NOT NULL,
	[gadgetName] [varchar](100) COLLATE Korean_Wansung_CI_AS NULL,
	[sqlText] [text] COLLATE Korean_Wansung_CI_AS NULL,
	[linkUrl] [varchar](300) COLLATE Korean_Wansung_CI_AS NULL,
	[fixedYN] [varchar](1) COLLATE Korean_Wansung_CI_AS NULL,
	[gadgetType] [varchar](1) COLLATE Korean_Wansung_CI_AS NULL,
	[useYN] [varchar](1) COLLATE Korean_Wansung_CI_AS NULL,
 CONSTRAINT [PK_GadGet] PRIMARY KEY CLUSTERED 
(
	[gadgetId] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF




-- =============================================
-- Author:		<남민호>
-- Create date: 2009.08.16
-- Description:	 getGadgetId 함수 생성
-- =============================================

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION getGadgetId()
RETURNS varchar(3)
AS
BEGIN
	-- Declare the return variable here
	DECLARE @gadgetId varchar(3)

	-- Add the T-SQL statements to compute the return value here
	SELECT @gadgetId = RIGHT('000' + CAST(CAST(IsNULL(MAX(gadgetId), '000') AS int) + 1 AS varchar(3)), 3)
	FROM GadGet

	-- Return the result of the function
	RETURN @gadgetId

END
GO







-- =============================================
-- Author:		<남민호>
-- Create date: 2009.08.26
-- Description:	 
-- =============================================

UPDATE CMTABLEDATA
SET key_2 = LEFT(key_1,1)
WHERE table_Name = 'SPECIAL_FIELD_CODE'







-- =============================================
-- Author:		<남민호>
-- Create date: 2009.09.14
-- Description:	 게시판 공지 가장 맨위 리스트로 
-- =============================================

alter table StandardBBS add  topArticle varchar(1) 













-- =============================================
-- Author:		<남민호>
-- Create date: 2009.09.14
-- Description:	가젯 관련 내용
-- =============================================


/****** 개체:  Table [dbo].[GadGet]    스크립트 날짜: 12/13/2009 18:45:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[GadGet](
	[gadgetId] [varchar](3) NOT NULL,
	[gadgetName] [varchar](100) NULL,
	[sqlText] [text] NULL,
	[linkUrl] [varchar](300) NULL,
	[ordSeq] [int] NULL,
	[fixedYN] [varchar](1) NULL,
	[gadgetType] [varchar](1) NULL,
	[useYN] [varchar](1) NULL,
 CONSTRAINT [PK_GadGet] PRIMARY KEY CLUSTERED 
(
	[gadgetId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF




/****** 개체:  Table [dbo].[MyGadGet]    스크립트 날짜: 12/13/2009 18:45:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MyGadGet](
	[userId] [varchar](50) NOT NULL,
	[gadgetId] [varchar](50) NOT NULL,
	[ordseq] [int] NOT NULL,
 CONSTRAINT [PK_MyGadGet] PRIMARY KEY CLUSTERED 
(
	[userId] ASC,
	[gadgetId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF



