/*******************************************************************
 * 기준코드 테이블 변경 내용 및 데이터 이관 
 *******************************************************************/

drop view dbo.CmTableData
drop table CmTableData
drop table CMTABLEDEF


/*************************************************************************************************************************************/

CREATE TABLE [dbo].[CMTABLEDEF](
	[idx] [int] NULL,
	[TABLE_NAME] [varchar](40) NOT NULL,
	[TABLE_DESC] [varchar](50) NULL,
	[KEY_1_LABEL] [varchar](20) NULL,
	[KEY_1_TYPE] [varchar](1) NULL,
	[KEY_1_SIZE] [int] NULL,
	[KEY_2_LABEL] [varchar](20) NULL,
	[KEY_2_TYPE] [varchar](1) NULL,
	[KEY_2_SIZE] [int] NULL,
	[KEY_3_LABEL] [varchar](20) NULL,
	[KEY_3_TYPE] [varchar](1) NULL,
	[KEY_3_SIZE] [int] NULL,
	[DATA_1_LABEL] [varchar](20) NULL,
	[DATA_1_TYPE] [varchar](1) NULL,
	[DATA_1_SIZE] [int] NULL,
	[DATA_2_LABEL] [varchar](20) NULL,
	[DATA_2_TYPE] [varchar](1) NULL,
	[DATA_2_SIZE] [int] NULL,
	[DATA_3_LABEL] [varchar](20) NULL,
	[DATA_3_TYPE] [varchar](1) NULL,
	[DATA_3_SIZE] [int] NULL,
	[DATA_4_LABEL] [varchar](20) NULL,
	[DATA_4_TYPE] [varchar](1) NULL,
	[DATA_4_SIZE] [int] NULL,
	[DATA_5_LABEL] [varchar](20) NULL,
	[DATA_5_TYPE] [varchar](1) NULL,
	[DATA_5_SIZE] [int] NULL,
	[DATA_6_LABEL] [varchar](20) NULL,
	[DATA_6_TYPE] [varchar](1) NULL,
	[DATA_6_SIZE] [int] NULL,
	[DATA_7_LABEL] [varchar](20) NULL,
	[DATA_7_TYPE] [varchar](1) NULL,
	[DATA_7_SIZE] [int] NULL,
	[DATA_8_LABEL] [varchar](20) NULL,
	[DATA_8_TYPE] [varchar](1) NULL,
	[DATA_8_SIZE] [int] NULL,
	[DATA_9_LABEL] [varchar](20) NULL,
	[DATA_9_TYPE] [varchar](1) NULL,
	[DATA_9_SIZE] [int] NULL,
	[DATA_10_LABEL] [varchar](20) NULL,
	[DATA_10_TYPE] [varchar](1) NULL,
	[DATA_10_SIZE] [int] NULL,
	[CREATE_USR_ID] [varchar](20) NULL,
	[CREATE_TIME] [datetime] NULL,
	[UPDATE_USR_ID] [varchar](20) NULL,
	[UPDATE_TIME] [datetime] NULL,
 CONSTRAINT [PK__CMTABLEDEF__0CE5D100] PRIMARY KEY CLUSTERED 
(
	[TABLE_NAME] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF

/*************************************************************************************************************************************/


CREATE TABLE [dbo].[CMTABLEDATA](
	[TABLE_NAME] [varchar](40) NOT NULL,
	[KEY_1] [varchar](30) NOT NULL,
	[KEY_2] [varchar](30) NOT NULL,
	[KEY_3] [varchar](30) NOT NULL,
	[DATA_1] [varchar](50) NULL,
	[DATA_2] [varchar](50) NULL,
	[DATA_3] [varchar](50) NULL,
	[DATA_4] [varchar](50) NULL,
	[DATA_5] [varchar](50) NULL,
	[DATA_6] [varchar](50) NULL,
	[DATA_7] [varchar](50) NULL,
	[DATA_8] [varchar](50) NULL,
	[DATA_9] [varchar](50) NULL,
	[DATA_10] [varchar](50) NULL,
	[CREATE_USR_ID] [varchar](20) NULL,
	[CREATE_TIME] [datetime] NULL,
	[UPDATE_USR_ID] [varchar](20) NULL,
	[UPDATE_TIME] [datetime] NULL,
 CONSTRAINT [PK__CMTABLEDATA__0AFD888E] PRIMARY KEY CLUSTERED 
(
	[TABLE_NAME] ASC,
	[KEY_1] ASC,
	[KEY_2] ASC,
	[KEY_3] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[CMTABLEDATA]  WITH CHECK ADD  CONSTRAINT [FK_CMTABLEDATA_CMTABLEDEF] FOREIGN KEY([TABLE_NAME])
REFERENCES [dbo].[CMTABLEDEF] ([TABLE_NAME])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[CMTABLEDATA] CHECK CONSTRAINT [FK_CMTABLEDATA_CMTABLEDEF]



/*************************************************************************************************************************************/

insert into [CMTABLEDEF]
select * from miracomBpmsDb.dbo.[CMTABLEDEF]
/*************************************************************************************************************************************/

insert into [CMTABLEDATA]
select * from miracomBpmsDb.dbo.[CMTABLEDATA]




