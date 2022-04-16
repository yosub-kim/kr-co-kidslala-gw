package test;

import junit.framework.TestCase;
import kr.co.kmac.pms.service.scheduler.batch.ProjectReportScheduleService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

public class ProjectReportServiceTest extends TestCase {
	private ApplicationContext ctx;

	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("src/resources/log4j.properties");
		super.setUp();
		ctx = new FileSystemXmlApplicationContext(
				"WebContent/WEB-INF/applicationContext/applicationContext-*.xml");
	}

	public void testProjectReport() throws Exception {
		ProjectReportScheduleService service = (ProjectReportScheduleService) ctx
				.getBean("projectReportScheduleService");

		service.assignProjectReportUntilToday();
	}

}
