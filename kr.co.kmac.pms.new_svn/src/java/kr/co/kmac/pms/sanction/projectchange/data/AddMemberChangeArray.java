package kr.co.kmac.pms.sanction.projectchange.data;

public interface AddMemberChangeArray {

	public String getProjectCode();

	public void setProjectCode(String projectCode);

	public int getMemberChangeSeq();

	public void setMemberChangeSeq(int memberChangeSeq);

	public String[] getAddMemberSsn();

	public void setAddMemberSsn(String[] addMemberSsn);

	public String[] getAddMemberName();

	public void setAddMemberName(String[] addMemberName);

	public String[] getAddMemberRole();

	public void setAddMemberRole(String[] addMemberRole);

	public String[] getAddMemberCost();

	public void setAddMemberCost(String[] addMemberCost);

	public String[] getAddMemberTrainingYn();

	public void setAddMemberTrainingYn(String[] addMemberTrainingYn);

	public String[] getAddMemberContributionCost();

	public void setAddMemberContributionCost(String[] addMemberContributionCost);

	public String[] getAddMemberResRate();

	public void setAddMemberResRate(String[] addMemberResRate);
	
	public String[] getAddMemberPosition();

	public void setAddMemberPosition(String[] addMemberPosition);
	
	public String[] getAddMemberStartDate();

	public void setAddMemberStartDate(String[] addMemberStartDate);
	
	public String[] getAddMemberEndDate();

	public void setAddMemberEndDate(String[] addMemberEndDate);

}
