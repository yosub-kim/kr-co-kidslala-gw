package test;

import java.util.List;

import junit.framework.TestCase;
import kr.co.kmac.pms.service.scheduler.batch.ProjectReportScheduleService;
import kr.co.kmac.pms.service.scheduler.data.ProjectReportScheduleData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

public class ProjectReportScheduleServiceTest extends TestCase {
	private ApplicationContext ctx;
	private Log logger = null;
	private ProjectReportScheduleService manager;

	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("src/resources/log4j.properties");
		super.setUp();
		logger = LogFactory.getLog(getClass());
		ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext/applicationContext-*.xml");
		manager = (ProjectReportScheduleService) ctx.getBean("projectReportScheduleService");
	}

	public void test1() throws Exception {
		// List<ProjectReportScheduleData> list = manager.assignProjectReportUntilToday();
		// for (ProjectReportScheduleData projectReportScheduleData : list) {
		// System.out.println(projectReportScheduleData.toString());
		// }
	}

}
