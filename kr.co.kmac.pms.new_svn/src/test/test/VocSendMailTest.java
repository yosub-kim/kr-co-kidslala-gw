package test;

import junit.framework.TestCase;
import kr.co.kmac.pms.service.scheduler.batch.VocMailSendScheduleService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

public class VocSendMailTest extends TestCase {
	private ApplicationContext ctx;
	private Log logger = null;
	private VocMailSendScheduleService vocMailSendScheduleService;

	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("src/resources/log4j.properties");
		super.setUp();
		logger = LogFactory.getLog(getClass());
		ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext/applicationContext-*.xml");
		vocMailSendScheduleService = (VocMailSendScheduleService) ctx.getBean("vocMailSendScheduleService");
	}

	public void testProjectDao1() throws Exception {
		vocMailSendScheduleService.vocMailSend();
	}

}
