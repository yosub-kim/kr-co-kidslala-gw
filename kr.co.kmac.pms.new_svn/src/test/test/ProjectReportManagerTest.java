package test;

import java.util.List;

import junit.framework.TestCase;
import kr.co.kmac.pms.project.preport.data.ProjectReportPlan;
import kr.co.kmac.pms.project.preport.manager.ProjectReportInfoManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

public class ProjectReportManagerTest extends TestCase {
	private ApplicationContext ctx;
	private Log logger = null;

	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("src/resources/log4j.properties");
		super.setUp();
		logger = LogFactory.getLog(getClass());
		ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext/applicationContext-*.xml");
	}

	public void testProjectReport() throws Exception {
		ProjectReportInfoManager manager = (ProjectReportInfoManager) ctx.getBean("projectReportInfoManager");
		List<ProjectReportPlan> list = manager.getProjectReportInfo("0501115", "2006", "07");
		for (ProjectReportPlan projectReportPlan : list) {
			System.out.println(projectReportPlan.toString());
		}
	}

}
