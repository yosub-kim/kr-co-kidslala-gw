/*
 * $Id: SacntionListDaoTest.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 * created by    : jiwoong
 * creation-date : 2008. 8. 1.
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package test;

import junit.framework.TestCase;
import kr.co.kmac.pms.sanction.line.dao.SanctionLineDao;
import kr.co.kmac.pms.sanction.line.data.SanctionLine;
import kr.co.kmac.pms.sanction.line.form.SanctionLineForm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

/**
 * TODO Ŭ���� ����
 * 
 * @author jiwoong
 * @version $Id: SacntionListDaoTest.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 */

public class SacntionListDaoTest extends TestCase {
	private ApplicationContext ctx;
	private Log logger = null;

	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("src/resources/log4j.properties");
		super.setUp();
		logger = LogFactory.getLog(getClass());
		ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext/applicationContext-*.xml");
	}

	public void testCreateSanctionTemplate() throws Exception {
		SanctionLineDao dao = (SanctionLineDao) ctx.getBean("sanctionLineDao");
		SanctionLine sanctionLine = new SanctionLineForm();
		sanctionLine.setId("~~~");
		sanctionLine.setRegisterSsn("~~~");
		sanctionLine.setRegisterDept("~~~");
		sanctionLine.setRegisterName("~~~");
		sanctionLine.setTeamManagerSsn("~~~");
		sanctionLine.setTeamManagerDept("~~~");
		sanctionLine.setTeamManagerName("~~~");
		sanctionLine.setCfoSsn("~~~");
		sanctionLine.setCfoDept("~~~");
		sanctionLine.setCfoName("~~~");
		sanctionLine.setAssistant1Ssn("~~~");
		sanctionLine.setAssistant1Dept("~~~");
		sanctionLine.setAssistant1Name("~~~");
		sanctionLine.setAssistant2Ssn("~~~");
		sanctionLine.setAssistant2Dept("~~~");
		sanctionLine.setAssistant2Name("~~~");
		sanctionLine.setCeoSsn("~~~");
		sanctionLine.setCeoDept("~~~");
		sanctionLine.setCeoName("~~~");

		dao.insert(sanctionLine);
		dao.update(sanctionLine);
		System.out.println(dao.select("~~~"));
		dao.delete("~~~");

	}

}
