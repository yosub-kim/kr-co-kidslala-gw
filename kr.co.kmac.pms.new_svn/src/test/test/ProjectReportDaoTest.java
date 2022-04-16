package test;

import junit.framework.TestCase;
import kr.co.kmac.pms.project.preport.dao.ProjectReportDao;
import kr.co.kmac.pms.project.preport.dao.ProjectReportDetailDao;
import kr.co.kmac.pms.project.preport.data.ProjectReportPlan;
import kr.co.kmac.pms.system.processcategory.data.ProcessCategory;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplate;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplateAttach;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplateDetail;
import kr.co.kmac.pms.system.processtemplate.manager.ProcessTemplateManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

public class ProjectReportDaoTest extends TestCase {
	private ApplicationContext ctx;
	private Log logger = null;

	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("src/resources/log4j.properties");
		super.setUp();
		logger = LogFactory.getLog(getClass());
		ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext/applicationContext-*.xml");
	}

	public void testProjectReport() throws Exception {
		ProjectReportDao dao1 = (ProjectReportDao) ctx.getBean("projectReportDao");
		ProjectReportDetailDao dao2 = (ProjectReportDetailDao) ctx.getBean("projectReportDetailDao");

		// ProjectReportPlan plan = new ProjectReportPlan();
		// plan.setBusinessTypeCode("TEST");
		// plan.setDay("1");
		// plan.setMonth("01");
		// plan.setName("AA");
		// plan.setOutputName("AAAA");
		// plan.setProjectCode("AAAAAA");
		// plan.setSsn("AAAA");
		// plan.setTime("1");
		// plan.setWeek("SAT");
		// plan.setWorkName("AA");
		// plan.setWorkSeq("1");
		// plan.setYear("1111");
		//		
		// dao1.insert(plan);
		// dao2.insert(plan);
		System.out.println(dao1.count("05011www25", "2006", "08"));
	}

}
