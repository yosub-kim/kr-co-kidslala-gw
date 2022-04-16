package test;

import java.util.List;

import junit.framework.TestCase;
import kr.co.kmac.pms.common.org.data.IGroup;
import kr.co.kmac.pms.common.org.data.OrgUnit;
import kr.co.kmac.pms.common.org.manager.IOrgUnitManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

public class OrgTreeTest extends TestCase {
	private ApplicationContext ctx;
	private Log logger = null;
	private IOrgUnitManager manager;

	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("src/resources/log4j.properties");
		super.setUp();
		logger = LogFactory.getLog(getClass());
		ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext/applicationContext-*.xml");
		manager = (IOrgUnitManager) ctx.getBean("orgUnitManager");
	}

	public void testProcessCategoroy() throws Exception {
		List<OrgUnit> list = manager.findRoot();
		populate(list);
		System.out.println(list);
	}

	private List<OrgUnit> populate(List<OrgUnit> list) {
		for (OrgUnit orgUnit : list) {
			manager.populateMember((OrgUnit) orgUnit);
			System.out.println(orgUnit.toString());
			List<OrgUnit> list2 = orgUnit.getSubGroupList();
			populate(list2);
		}
		return list;
	}
}
