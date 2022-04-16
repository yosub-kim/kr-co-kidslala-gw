package test;

import junit.framework.TestCase;
import kr.co.kmac.pms.common.ajax.JSONWriter;
import kr.co.kmac.pms.service.scheduler.batch.BizplayInfoSendService;
import kr.co.kmac.pms.service.scheduler.dao.BizplayInfoSendDao;
import kr.co.kmac.pms.service.scheduler.data.bizplay.dvsn.BizplayDeptInfoSet;
import kr.co.kmac.pms.service.scheduler.data.bizplay.empl.BizplayExpertPoolInfoSet;
import kr.co.kmac.pms.service.scheduler.data.bizplay.pjt.BizplayProjectInfoSet;
import kr.co.kmac.pms.service.scheduler.manager.BizplayInfoSendManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

public class BizplayInfoSendDaoTest extends TestCase {
	private ApplicationContext ctx;
	private Log logger = null;
	private BizplayInfoSendDao bizplayInfoSendDao;
	private BizplayInfoSendManager bizplayInfoSendManager;
	private BizplayInfoSendService bizplayInfoSendService;

	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("src/resources/log4j.properties");
		super.setUp();
		logger = LogFactory.getLog(getClass());
		ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext/applicationContext-*.xml");
		bizplayInfoSendDao = (BizplayInfoSendDao) ctx.getBean("bizplayInfoSendDao");
		bizplayInfoSendManager = (BizplayInfoSendManager) ctx.getBean("bizplayInfoSendManager");
		bizplayInfoSendService = (BizplayInfoSendService) ctx.getBean("bizplayInfoSendService");
	}

	public void testProjectDao1() throws Exception {
		// BizplayExpertPoolInfoSet bizplayExpertPoolInfoSet = bizplayInfoSendManager.expertPoolJsonObject("C");
		// BizplayDeptInfoSet bizplayDeptInfoSet = bizplayInfoSendManager.deptInfoJsonObject("C");
		// BizplayProjectInfoSet bizplayProjectInfoSet = bizplayInfoSendManager.projectInfoJsonObject("C");

		// JSONWriter writer = new JSONWriter();
		// String res1 = writer.write(bizplayExpertPoolInfoSet);
		// String res2 = writer.write(bizplayDeptInfoSet);
		// String res3 = writer.write(bizplayProjectInfoSet);

		// System.out.println("1:" + res1);
		// System.out.println("2:" + res2);
		// System.out.println("3:" + res3);

		// bizplayInfoSendService.sendDeptInfoToBizplay();
		// bizplayInfoSendService.sendExpertPoolInfoToBizplay();
		//bizplayInfoSendService.sendProjectInfoToBizplay();
		 bizplayInfoSendService.bizplayInfoSend();
	}

}
