package test;

import java.util.List;

import junit.framework.TestCase;
import kr.co.kmac.pms.project.master.dao.ProjectDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

public class ProjectDaoTest extends TestCase {
	private ApplicationContext ctx;
	private Log logger = null;
	private ProjectDao projectDao;

	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("src/resources/log4j.properties");
		super.setUp();
		logger = LogFactory.getLog(getClass());
		ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext/applicationContext-*.xml");
		projectDao = (ProjectDao) ctx.getBean("projectDao");
	}

	public void testProjectDao1() throws Exception {
		// public Project select(String projectCode) throws DataAccessException;
		System.out.println(this.projectDao.select("0901260"));
	}

	public void testProjectDao2() throws Exception {
		// public void insert(Project project) throws DataAccessException;
	}

	public void testProjectDao3() throws Exception {
		// public void delete(String projectCode) throws DataAccessException;
	}

	public void testProjectDao4() throws Exception {
		// public void update(Project project) throws DataAccessException;
	}

	public void testProjectDao5() throws Exception {
		// public ProjectSimpleInfo getProjectSimpleInfo(String projectCode) throws DataAccessException;
		System.out.println(this.projectDao.getProjectSimpleInfo("0901260"));
		System.out.println(this.projectDao.getProjectSimpleInfo("090126011"));
	}

	public void testProjectDao6() throws Exception {
		// public List<String> getEndProcessTaskList(String projectCode) throws DataAccessException;
		List<String> list = this.projectDao.getEndProcessTaskList("0904219");
		for (String string : list) {
			System.out.println(string);
		}
	}

	public void testProjectDao7() throws Exception {
		// public void updateProjectState(String projectCode, String state) throws DataAccessException;
		this.projectDao.updateProjectState("1001106", "3");
	}

	public void testProjectDao8() throws Exception {
		// public void updateProjectEndTaskState(String projectCode, String state) throws DataAccessException;
		this.projectDao.updateProjectEndTaskState("0904219", "");
	}

	public void testProjectDao9() throws Exception {
		// public void updateBudgetState(String projectCode) throws DataAccessException;
		this.projectDao.updateBudgetState("1001106");
	}

	public void testProjectDao10() throws Exception {
		// public void sendBudgetState(String projectCode) throws DataAccessException;
		// this.projectDao.sendBudgetState(projectCode);
	}

	public void testProjectDao11() throws Exception {
		// public List<ProjectDelayInfo> getProjectDelayInfo(String projectCode, String delayType) throws DataAccessException;
		System.out.println(this.projectDao.getProjectDelayInfo("0901260", null));
		System.out.println(this.projectDao.getProjectDelayInfo("090126011", null));
	}

	public void testProjectDao12() throws Exception {
		// public boolean isConnectedProject(String projectCode) throws ProjectException;
		System.out.println(this.projectDao.isConnectedProject("1005246"));
	}

}
