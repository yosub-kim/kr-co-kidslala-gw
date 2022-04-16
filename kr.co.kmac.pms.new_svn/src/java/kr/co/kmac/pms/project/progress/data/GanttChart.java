package kr.co.kmac.pms.project.progress.data;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GanttChart {
	private String projectCode;
	private List<ProjectProgress> projectProgressList;

	/**
	 * @return the projectCode
	 */
	public String getProjectCode() {
		return projectCode;
	}

	/**
	 * @param projectCode the projectCode to set
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * @return the projectProgressList
	 */
	public List<ProjectProgress> getProjectProgressList() {
		return projectProgressList;
	}

	/**
	 * @param projectProgressList the projectProgressList to set
	 */
	public void setProjectProgressList(List<ProjectProgress> projectProgressList) {
		this.projectProgressList = projectProgressList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public static GanttChart fromXML(String xml, ClassLoader loader) {
		if (loader != null) {
			xstream.setClassLoader(loader);
		}
		return (GanttChart) xstream.fromXML(xml);
	}

	public static String toXML(GanttChart ganttChart, ClassLoader loader) {
		if (loader != null) {
			xstream.setClassLoader(loader);
		}
		return xstream.toXML(ganttChart);
	}

	private static XStream xstream = new XStream(new DomDriver("UTF-8"));
	static {
		xstream.alias("ganttChart", GanttChart.class);
		xstream.aliasAttribute(GanttChart.class, "projectCode", "projectCode");
		xstream.aliasAttribute(ProjectProgress.class, "projectCode", "projectCode");
		xstream.aliasAttribute(ProjectProgress.class, "workSeq", "workSeq");
		xstream.aliasAttribute(ProjectProgress.class, "workName", "workName");
		xstream.aliasAttribute(ProjectProgress.class, "parantWorkSeq", "parantWorkSeq");
		xstream.aliasAttribute(ProjectProgress.class, "startDate", "startDate");
		xstream.aliasAttribute(ProjectProgress.class, "endDate", "endDate");
		xstream.aliasAttribute(ProjectProgress.class, "realEndDate", "realEndDate");
		xstream.aliasAttribute(ProjectProgress.class, "contentId", "contentId");
		xstream.aliasAttribute(ProjectProgress.class, "level", "level");
		xstream.addImplicitCollection(ProjectProgress.class, "projectProgressList", "item", ProjectProgress.class);
		xstream.addImplicitCollection(GanttChart.class, "projectProgressList", "item", ProjectProgress.class);
	}
}
