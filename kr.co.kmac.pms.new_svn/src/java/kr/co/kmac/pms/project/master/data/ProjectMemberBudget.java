package kr.co.kmac.pms.project.master.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ProjectMemberBudget extends ProjectMember {
	private String totalMM;
	private String totalAmount;
	
	public String getTotalMM() {
		return totalMM;
	}

	public void setTotalMM(String totalMM) {
		this.totalMM = totalMM;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
/*
	public static List<ProjectMemberBudget> valueOf(ProjectMasterInfoForm form) {
		List<ProjectMemberBudget> list = new ArrayList<ProjectMemberBudget>();
		ProjectMemberBudget projectMemberBudget = null;
		if (form.getMemberSsn() != null) {
			for (int i = 0; i < form.getMemberSsn().length; i++) {
				projectMemberBudget = new ProjectMemberBudget();
				projectMemberBudget.setProjectCode(form.getProjectCode());
				projectMemberBudget.setSsn(form.getMemberSsn()[i]);
				projectMemberBudget.setName(form.getMemberName()[i]);
				projectMemberBudget.setRole(form.getMemberRole()[i]);
				projectMemberBudget.setPosition(form.getMemberPosition()[i]);
				projectMemberBudget.setResRate(form.getMemberResRate()[i]);
				projectMemberBudget.setCost(form.getMemberCost()[i]);
				projectMemberBudget.setTrainingYn(form.getMemberTrainingYn()[i]);
				projectMemberBudget.setContributionCost(form.getMemberContributionCost()[i]);
				list.add(projectMemberBudget);
			}
		}
		return list;
	}
*/
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
