package test;

import junit.framework.TestCase;
import kr.co.kmac.pms.system.processcategory.data.ProcessCategory;
import kr.co.kmac.pms.system.processcategory.manager.ProcessCategoryManager;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplate;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplateAttach;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplateDetail;
import kr.co.kmac.pms.system.processtemplate.manager.ProcessTemplateManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

public class ProcessTemplateTest extends TestCase {
	private ApplicationContext ctx;
	private Log logger = null;

	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("src/resources/log4j.properties");
		super.setUp();
		logger = LogFactory.getLog(getClass());
		ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext/applicationContext-*.xml");
	}

	public void testProcessCategoroy() throws Exception {
		ProcessCategoryManager manager = (ProcessCategoryManager) ctx.getBean("processCategoryManager");
		ProcessCategory category = new ProcessCategory();
		category.setProcessTemplateCode("TEST~");
		category.setProcessTemplateName("TEST~");
		category.setBusinessTypeCode("businessTypeCode");
		category.setBusinessTypeName("businessTypeName");
		category.setProcessTemplateCode("processCode");
		category.setProcessTemplateName("processName");
		category.setProjectType("projectType");
		category.setProjectTypeName("projectTypeName");
		category.setRunningDivCode("runningDivCode");
		category.setRunningDivName("runningDivName");
		category.setTableName("tableName");

		manager.storeProcessCategory(category);
		manager.updateProcessCategory(category);
		manager.getProcessCategory("TEST!");
		manager.deleteProcessCategory("TEST~");

	}

	public void testProcessTemplate() throws Exception {
		ProcessTemplateManager manager = (ProcessTemplateManager) ctx.getBean("processTemplateManager");

		ProcessTemplate template = new ProcessTemplate();
		template.setProcessTemplateCode("TEST");
		template.setProcessTemplateName("TEST");
		manager.createProcessTemplate(template);
		manager.updateProcessTemplate(template);
		manager.getProcessTemplate("TEST");

		ProcessTemplateDetail detail = new ProcessTemplateDetail();
		detail.setProcessTemplateCode("TEST");
		detail.setProcessTemplateName("TEST");
		detail.setActivityName("TEST");
		detail.setEditable(false);
		detail.setLevel(1);
		detail.setNecessary(false);
		detail.setWorkSeq(1);
		manager.createProcessTemplateDetail(detail);
		manager.updateProcessTemplateDetail(detail);
		manager.getProcessTemplateDetail("TEST");

		ProcessTemplateAttach attach = new ProcessTemplateAttach();
		attach.setAttachSeq(1);
		attach.setNecessary(true);
		attach.setBizType("TEST");
		attach.setOutputName("TEST");
		attach.setOutputType("TEST");
		attach.setProcessTemplateCode("TEST");
		attach.setWorkSeq(1);
		// manager.createProcessTemplateAttach(attach);
		manager.updateProcessTemplateAttach(attach);
		manager.getProcessTemplateAttach("TEST", "1");

		manager.deleteProcessTemplateAttach("TEST", "1", null);
		manager.deleteProcessTemplateDetail("TEST", null);
		manager.deleteProcessTemplate("TEST");
	}
}
