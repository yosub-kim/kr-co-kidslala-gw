/*
 * $Id: SmManagerTest.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 * created by    : jiwoong
 * creation-date : 2008. 8. 1.
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package test;

import java.util.List;

import junit.framework.TestCase;
import kr.co.kmac.pms.common.org.data.OrgUnit;
import kr.co.kmac.pms.common.org.manager.IOrgUnitManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoong
 * @version $Id: SmManagerTest.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 */

public class SmManagerTest extends TestCase {
	private ApplicationContext ctx;
	private Log logger = null;

	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("src/resources/log4j.properties");
		super.setUp();
		logger = LogFactory.getLog(getClass());
		ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext/applicationContext-*.xml");
	}

	public void testCreateSanctionTemplate() throws Exception {
		IOrgUnitManager manager = (IOrgUnitManager) ctx.getBean("orgUnitManager");
		List<OrgUnit> list = manager.find();
		for (Object object : list) {
			System.out.println(object.toString());
		}
	}

}
