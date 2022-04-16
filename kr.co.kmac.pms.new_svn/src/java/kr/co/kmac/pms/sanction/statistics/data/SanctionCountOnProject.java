package kr.co.kmac.pms.sanction.statistics.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SanctionCountOnProject {

	private String projectCode;
	private String projectName;
	private String divCode;
	private String divName;
	private String m0000;
	private String s0000;
	private String draft11;
	private String draft14;
	private String r0000;
	private String c0000;

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDivCode() {
		return divCode;
	}

	public void setDivCode(String divCode) {
		this.divCode = divCode;
	}

	public String getDivName() {
		return divName;
	}

	public void setDivName(String divName) {
		this.divName = divName;
	}

	public String getM0000() {
		return m0000;
	}

	public void setM0000(String m0000) {
		this.m0000 = m0000;
	}

	public String getS0000() {
		return s0000;
	}

	public void setS0000(String s0000) {
		this.s0000 = s0000;
	}

	public String getDraft11() {
		return draft11;
	}

	public void setDraft11(String draft11) {
		this.draft11 = draft11;
	}

	public String getDraft14() {
		return draft14;
	}

	public void setDraft14(String draft14) {
		this.draft14 = draft14;
	}

	public String getR0000() {
		return r0000;
	}

	public void setR0000(String r0000) {
		this.r0000 = r0000;
	}

	public String getC0000() {
		return c0000;
	}

	public void setC0000(String c0000) {
		this.c0000 = c0000;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}