package kr.co.kmac.pms.sanction.projectchange.form;

import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.sanction.general.form.SanctionForm;
import kr.co.kmac.pms.sanction.projectchange.data.ScheduleChange;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @struts:form name="scheduleChangeSanctionAction"
 */
public class ScheduleChangeSanctionForm extends SanctionForm implements ScheduleChange {

	private int scheduleChangeSeq;
	private String preStartDate;
	private String preEndDate;
	private String realStartDate;
	private String realEndDate;

	public int getScheduleChangeSeq() {
		return scheduleChangeSeq;
	}

	public void setScheduleChangeSeq(int scheduleChangeSeq) {
		this.scheduleChangeSeq = scheduleChangeSeq;
	}

	public String getPreStartDate() {
		return StringUtil.replace(this.preStartDate, "-", "");
	}

	public void setPreStartDate(String preStartDate) {
		this.preStartDate = preStartDate;
	}

	public String getPreEndDate() {
		return StringUtil.replace(this.preEndDate, "-", "");
	}

	public void setPreEndDate(String preEndDate) {
		this.preEndDate = preEndDate;
	}

	public String getRealStartDate() {
		return StringUtil.replace(this.realStartDate, "-", "");
	}

	public void setRealStartDate(String realStartDate) {
		this.realStartDate = realStartDate;
	}

	public String getRealEndDate() {
		return StringUtil.replace(this.realEndDate, "-", "");
	}

	public void setRealEndDate(String realEndDate) {
		this.realEndDate = realEndDate;
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6501557856508603530L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
