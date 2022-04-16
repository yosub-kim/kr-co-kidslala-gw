package kr.co.kmac.pms.sanction.projectchange.data;

public interface ScheduleChange {

	public String getProjectCode();

	public void setProjectCode(String projectCode);

	public int getScheduleChangeSeq();

	public void setScheduleChangeSeq(int scheduleChangeSeq);

	public String getPreStartDate();

	public void setPreStartDate(String preStartDate);

	public String getPreEndDate();

	public void setPreEndDate(String preEndDate);

	public String getRealStartDate();

	public void setRealStartDate(String realStartDate);

	public String getRealEndDate();

	public void setRealEndDate(String realEndDate);
}
