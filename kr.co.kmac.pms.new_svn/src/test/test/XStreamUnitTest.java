package test;

import junit.framework.TestCase;
import kr.co.kmac.pms.project.progress.data.GanttChart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Log4jConfigurer;

public class XStreamUnitTest extends TestCase {
	private ApplicationContext ctx;
	private Log logger = null;

	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("src/resources/log4j.properties");
		super.setUp();
		logger = LogFactory.getLog(getClass());
	}

	public void testProcessCategoroy() throws Exception {
		String xml = ""
				+ "	<ganttChart projectCode=\"0904085\" maxWorkSeq=\"6\" projectStartDate=\"20090506\" projectEndDate=\"20090603\">"
				+ "		<item workName=\"货 诀公\" workSeq=\"9\" parentWorkSeq=\"-1\" startDate=\"20090506\" endDate=\"20090506\" realEndDate=\"20090506\" projectCode=\"0904085\" contentId=\"qwerq\"/>"
				+ "		<item workName=\"货 诀公\" workSeq=\"9\" parentWorkSeq=\"-1\" startDate=\"20090506\" endDate=\"20090506\" realEndDate=\"20090506\" projectCode=\"0904085\" contentId=\"qwerq\"/>"
				+ "		<item workName=\"背腊角矫\" workSeq=\"6\" parentWorkSeq=\"-1\" startDate=\"2009-01-06\" endDate=\"2009-01-29\" realEndDate=\"\" projectCode=\"0904085\" contentId=\"TASK55947518\">"
				+ "			<item workName=\"货 诀公\" workSeq=\"11\" parentWorkSeq=\"6\" startDate=\"20090506\" endDate=\"20090506\" realEndDate=\"20090506\" projectCode=\"0904085\" contentId=\"qwerq\"/>"
				+ "			<item workName=\"货 诀公\" workSeq=\"12\" parentWorkSeq=\"6\" startDate=\"20090506\" endDate=\"20090506\" realEndDate=\"20090506\" projectCode=\"0904085\" contentId=\"qwerq\"/>"
				+ "		</item>                                                                                                    "
				+ "	</ganttChart>                                                                                                  ";
		GanttChart ganttChart = GanttChart.fromXML(xml, XStreamTest.class.getClassLoader());
		System.out.println(ganttChart.toString());
	}

}
