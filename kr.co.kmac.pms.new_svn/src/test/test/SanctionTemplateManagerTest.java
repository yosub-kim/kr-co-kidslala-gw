/*
 * $Id: SanctionTemplateManagerTest.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 * created by    : jiwoong
 * creation-date : 2008. 8. 1.
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package test;

import junit.framework.TestCase;
import kr.co.kmac.pms.system.sanction.data.SanctionTemplate;
import kr.co.kmac.pms.system.sanction.manager.SanctionTemplateManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoong
 * @version $Id: SanctionTemplateManagerTest.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 */

public class SanctionTemplateManagerTest extends TestCase {
	private ApplicationContext ctx;
	private Log logger = null;

	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("src/resources/log4j.properties");
		super.setUp();
		logger = LogFactory.getLog(getClass());
		ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext/applicationContext-*.xml");
	}

	public void testCreateSanctionTemplate() throws Exception {
		SanctionTemplateManager manager = (SanctionTemplateManager) ctx.getBean("sanctionTemplateManager");
		SanctionTemplate template = new SanctionTemplate();
		template.setApprovalType("aaa");
		template.setApprovalName("aaa");
		template.setHasAssistMember(true);
		template.setHasAttach(true);
		template.setHasRefMember(true);
		template.setHasSptTeamMng(true);
		template.setHasWholeApprove(true);
		template.setTemplateText("asdfasdfasdf");
		template.setCreateUser("asd1");
		template.setUpdateUser("asd2");
		manager.createSanctionTemplate(template);
	}
//	public void testGetSanctionTemplate() throws Exception {
//		SanctionTemplateManager manager = (SanctionTemplateManager) ctx.getBean("sanctionTemplateManager");
//		List<SanctionTemplate> list = manager.getSanctionTemplate();
//		for (SanctionTemplate sanctionTemplate : list) {
//			System.out.println(sanctionTemplate.toString());
//		}
//	}
//
//	public void testDeleteSanctionTemplate() throws Exception {
//		SanctionTemplateManager manager = (SanctionTemplateManager) ctx.getBean("sanctionTemplateManager");
//		manager.deleteSanctionTemplate("aaa");
//	}
}
