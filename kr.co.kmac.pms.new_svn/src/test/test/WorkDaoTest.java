/*
 * $Id: WorkDaoTest.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 * created by    : jiwoong
 * creation-date : 2008. 8. 1.
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package test;

import java.util.Date;

import junit.framework.TestCase;
import kr.co.kmac.pms.common.worklist.dao.WorkDao;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.data.WorkConstants;
import kr.co.kmac.pms.sanction.general.data.SanctionConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoong
 * @version $Id: WorkDaoTest.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 */

public class WorkDaoTest extends TestCase {
	private ApplicationContext ctx;
	private Log logger = null;

	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("src/resources/log4j.properties");
		super.setUp();
		logger = LogFactory.getLog(getClass());
		ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext/applicationContext-*.xml");
	}

	public void testCreateSanctionTemplate() throws Exception {
		WorkDao dao = (WorkDao) ctx.getBean("workDao");
		Work work = new Work();
		work.setId("id");
		work.setTitle("title");
		work.setAssigneeId("assigneeId");
		work.setAssignerId("assignerId");
		work.setState(WorkConstants.WORK_STATE_READY);
		work.setLevel(SanctionConstants.SANCTION_STATE_APPROVE);
		work.setType("type");
		work.setRefWorkId1("refWorkId");
		work.setDocument("document");
		work.setDraftUserId("draftUserId");
		work.setDraftUserDept("draftUserDept");
		work.setDraftDate(new Date());
		work.setCreateDate(new Date());
		work.setOpenDate(new Date());
		work.setExecuteDate(new Date());
		dao.createWork(work);
	}

}
