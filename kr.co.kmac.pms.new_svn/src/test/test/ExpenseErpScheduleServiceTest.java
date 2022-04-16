package test;

import junit.framework.TestCase;
import kr.co.kmac.pms.sanction.general.dao.SanctionDocDao;
import kr.co.kmac.pms.service.scheduler.batch.ExpenseErpScheduleService;
import kr.co.kmac.pms.system.processcategory.data.ProcessCategory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

public class ExpenseErpScheduleServiceTest extends TestCase {
	private ApplicationContext ctx;
	private Log logger = null;

	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("src/resources/log4j.properties");
		super.setUp();
		logger = LogFactory.getLog(getClass());
		ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext/applicationContext-*.xml");
	}

	public void testExpenseErpScheduleService() throws Exception {
		ExpenseErpScheduleService service = (ExpenseErpScheduleService) ctx.getBean("expenseErpScheduleService");
		service.updateCostRelatedField();
	}
}
