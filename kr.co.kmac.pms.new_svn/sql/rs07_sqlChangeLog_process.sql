/*******************************************************************
 * 프로세스 관련 변경 내용
 *******************************************************************/
  
CREATE TABLE [dbo].[ProcessTemplate](
	[processTemplateCode] [varchar](5) NOT NULL,
	[processTemplateName] [varchar](200) NOT NULL,
 CONSTRAINT [PK_ProcessTemplate] PRIMARY KEY CLUSTERED 
(
	[processTemplateCode] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
/*************************************************************************************************************************************/


insert into ProcessTemplate
select * from outputTemplate

/*************************************************************************************************************************************/



CREATE TABLE [dbo].[ProcessTemplateDetail](
	[processTemplateCode] [varchar](5) NOT NULL,
	[workSeq] [int] NOT NULL,
	[activityName] [varchar](100) NOT NULL,
	[level] [int] NOT NULL,
	[parentWorkSeq] [int] NULL,
	[workType] [int] NULL,
	[editable] [bit] NULL,
	[necessary] [bit] NULL,
 CONSTRAINT [PK_ProcessTemplateDetail] PRIMARY KEY CLUSTERED 
(
	[processTemplateCode] ASC,
	[workSeq] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[ProcessTemplateDetail]  WITH CHECK ADD  CONSTRAINT [FK_ProcessTemplateDetail_ProcessTemplate] FOREIGN KEY([processTemplateCode])
REFERENCES [dbo].[ProcessTemplate] ([processTemplateCode])
GO
ALTER TABLE [dbo].[ProcessTemplateDetail] CHECK CONSTRAINT [FK_ProcessTemplateDetail_ProcessTemplate]



ALTER TABLE dbo.ProcessTemplateDetail  WITH CHECK ADD  CONSTRAINT FK_ProcessTemplateDetail_ProcessTemplate FOREIGN KEY(processTypeCode)
REFERENCES dbo.ProcessTemplate (processTypeCode)

ALTER TABLE dbo.ProcessTemplateDetail CHECK CONSTRAINT FK_ProcessTemplateDetail_ProcessTemplate


/*************************************************************************************************************************************/

insert into processTemplateDetail
select processTypeCode, workSeq, workName, 0, '-1', '1', '1', '1' from outputTemplateDetail
group by processTypeCode, workSeq, workName
/*************************************************************************************************************************************/



CREATE TABLE [dbo].[ProcessTemplateAttach](
	[processTemplateCode] [varchar](5) NOT NULL,
	[workSeq] [int] NOT NULL,
	[attachSeq] [int] NOT NULL,
	[outputName] [varchar](100) NULL,
	[outputType] [varchar](50) NULL,
	[bizType] [varchar](50) NULL,
	[necessary] [varchar](3) NULL,
 CONSTRAINT [PK_ProcessTemplateAttach] PRIMARY KEY CLUSTERED 
(
	[processTemplateCode] ASC,
	[workSeq] ASC,
	[attachSeq] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

ALTER TABLE [dbo].[ProcessTemplateAttach]  WITH CHECK ADD  CONSTRAINT [FK_ProcessTemplateAttach_ProcessTemplateDetail] FOREIGN KEY([processTemplateCode], [workSeq])
REFERENCES [dbo].[ProcessTemplateDetail] ([processTemplateCode], [workSeq])
GO
ALTER TABLE [dbo].[ProcessTemplateAttach] CHECK CONSTRAINT [FK_ProcessTemplateAttach_ProcessTemplateDetail]
/*************************************************************************************************************************************/


insert into ProcessTemplateAttach
select	processTypeCode, workSeq, 
		row_number() over(partition by processTypeCode, workSeq order by processTypeCode, workSeq ) as rowNumber, 
		outputName, outputType, outputBusType, isEssential from outputTemplateDetail
where outputName is not null




