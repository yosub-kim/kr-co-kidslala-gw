package test;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Log4jConfigurer;

public class XStreamTest extends TestCase {
	private Log logger = null;

	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("src/resources/log4j.properties");
		super.setUp();
		logger = LogFactory.getLog(getClass());
	}

	public void testCreateSanctionTemplate() throws Exception {
//		String xml = ""
//				+ "	<ganttChart projectCode=\"0904085\" maxWorkSeq=\"6\" projectStartDate=\"20090506\" projectEndDate=\"20090603\">"
//				+ "		<item workName=\"새 업무\" workSeq=\"9\" parentWorkSeq=\"\" startDate=\"\" endDate=\"\" realEndDate=\"\" projectCode=\"0904085\" contentId=\"\"/>"
//				+ "		<item workName=\"교육실시\" workSeq=\"6\" parentWorkSeq=\"-1\" startDate=\"2009-01-06\" endDate=\"2009-01-29\" realEndDate=\"\" projectCode=\"0904085\" contentId=\"TASK55947518\">"
//				+ "			<item workName=\"새 업무\" workSeq=\"11\" parentWorkSeq=\"\" startDate=\"\" endDate=\"\" realEndDate=\"\" projectCode=\"0904085\" contentId=\"\"/>"
//				+ "			<item workName=\"새 업무\" workSeq=\"12\" parentWorkSeq=\"\" startDate=\"\" endDate=\"\" realEndDate=\"\" projectCode=\"0904085\" contentId=\"\"/>"
//				+ "		</item>                                                                                                    "
//				+ "	</ganttChart>                                                                                                  ";
//		GanttChart ganttChart = GanttChart.fromXML(xml, XStreamTest.class.getClassLoader());
//		System.out.println(ganttChart.toString());
	}
}