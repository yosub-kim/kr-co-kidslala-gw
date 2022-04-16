package kr.co.kmac.pms.project.master.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ProjectRelatedEntNo {

	private String seq;
	private String projectCode;
	private String oldEntNo;
	private String newEntNo;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getOldEntNo() {
		return oldEntNo;
	}

	public void setOldEntNo(String oldEntNo) {
		this.oldEntNo = oldEntNo;
	}

	public String getNewEntNo() {
		return newEntNo;
	}

	public void setNewEntNo(String newEntNo) {
		this.newEntNo = newEntNo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
