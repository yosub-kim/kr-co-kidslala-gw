package test;

import junit.framework.TestCase;
import kr.co.kmac.pms.service.scheduler.batch.MonthlyReportScheduleService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

public class MonthlyReportScheduleServiceTest extends TestCase {
	private ApplicationContext ctx;
	private Log logger = null;
	private MonthlyReportScheduleService monthlyReportScheduleService;

	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("src/resources/log4j.properties");
		super.setUp();
		logger = LogFactory.getLog(getClass());
		ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext/applicationContext-*.xml");
		monthlyReportScheduleService = (MonthlyReportScheduleService) ctx.getBean("monthlyReportScheduleService");
	}

	public void testProjectDao1() throws Exception {
		monthlyReportScheduleService.assignMonthlyReport();
	}

}
