/*
 * $Id: ProjectExpenseTempForSanctionDaoTest.java,v 1.2 2010/01/23 13:55:41 cvs3 Exp $
 * created by    : jiwoong
 * creation-date : 2008. 8. 1.
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package test;

import junit.framework.TestCase;
import kr.co.kmac.pms.sanction.projectexpense.dao.ProjectExpenseTempForSanctionDao;
import kr.co.kmac.pms.sanction.projectexpense.data.ExpenseRealTimeResultDetailForSanction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoong
 * @version $Id: ProjectExpenseTempForSanctionDaoTest.java,v 1.2 2010/01/23 13:55:41 cvs3 Exp $
 */

public class ProjectExpenseTempForSanctionDaoTest extends TestCase {
	private ApplicationContext ctx;

	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("src/resources/log4j.properties");
		super.setUp();
		ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext/applicationContext-*.xml");
	}

	public void testCreateSanctionTemplate() throws Exception {
		ProjectExpenseTempForSanctionDao dao = (ProjectExpenseTempForSanctionDao) ctx.getBean("projectExpenseTempForSanctionDao");
		dao.delete("TT");
		ExpenseRealTimeResultDetailForSanction ob = new ExpenseRealTimeResultDetailForSanction();
		ob.setSalarySeq("TT");
		ob.setSalaryYear("TT");
		ob.setSalaryMonth("TT");
		ob.setSalaryName("TT");
		ob.setSalarySsn("TT");
		ob.setSalaryTotalRealTimeSalary(10000);
		ob.setSalaryProjectCode("TT");
		ob.setSalaryProjectName("TT");
		ob.setSalaryPreportCount("TT");
		ob.setSalaryRealTimeSalaryEachProject(10000);
		ob.setSalaryDept("TT");
		ob.setSalarySeq("TT");
		ob.setSalaryIsHolding("TT");

		dao.insert(ob);
		System.out.println(dao.select("TT"));
		dao.delete("TT");

	}

}
