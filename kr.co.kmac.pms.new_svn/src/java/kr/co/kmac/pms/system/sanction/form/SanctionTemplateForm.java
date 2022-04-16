package kr.co.kmac.pms.system.sanction.form;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts.action.ActionForm;

/**
 * @struts:form name="sanctionTemplateAction"
 */
public class SanctionTemplateForm extends ActionForm {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4831267070254356548L;
	/**
	 * 전자 결재 타입
	 */
	private String approvalType;
	/**
	 * 전자결재명
	 */
	private String approvalName;
	/**
	 * 참조라인이 필요?
	 */
	private boolean hasRefMember;
	private String refMemberNames;
	private String refMemberSsns;
	/**
	 * 협의 라인이 필요?
	 */
	private boolean hasAssistMember;
	private int assistMemberCnt;
	/**
	 * 협의 라인에 지원실장이 필요?
	 */
	private boolean hasSptTeamMng;
	private String sptTeamMngName;
	private String sptTeamMngDept;
	private String sptTeamMngSsn;
	/**
	 * CEO 필요?
	 */
	private boolean hasCeo;

	private boolean hasTeamManager;
	private boolean hasCfo;
	private boolean hasCio;

	private String ceoName;
	private String ceoDept;
	private String ceoSsn;
	/**
	 * 전결이 필요?
	 */
	private boolean hasWholeApprove;
	/**
	 * 협의 1 전결이 필요?
	 */
	private boolean hasSptTeamMngWholeApprove;
	/**
	 * 첨부가 필요?
	 */
	private boolean hasAttach;
	/**
	 * 결재 문서 내용
	 */
	private String templateText;

	/**
	 * 사용여부
	 */
	private boolean useYn;

	private String sanctionType;

	private Date createdDate;
	private String createUser;
	private Date updatedDate;
	private String updateUser;

	/**
	 * 모바일 결재 사용 유무
	 */
	private boolean useMobile;

	/**
	 * @return the approvalType
	 */
	public String getApprovalType() {
		return approvalType;
	}

	/**
	 * @param approvalType the approvalType to set
	 */
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	/**
	 * @return the approvalName
	 */
	public String getApprovalName() {
		return approvalName;
	}

	/**
	 * @param approvalName the approvalName to set
	 */
	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
	}

	/**
	 * @return the hasRefMember
	 */
	public boolean isHasRefMember() {
		return hasRefMember;
	}

	/**
	 * @param hasRefMember the hasRefMember to set
	 */
	public void setHasRefMember(boolean hasRefMember) {
		this.hasRefMember = hasRefMember;
	}

	/**
	 * @return the hasAssistMember
	 */
	public boolean isHasAssistMember() {
		return hasAssistMember;
	}

	/**
	 * @param hasAssistMember the hasAssistMember to set
	 */
	public void setHasAssistMember(boolean hasAssistMember) {
		this.hasAssistMember = hasAssistMember;
	}

	/**
	 * @return the assistMemberCnt
	 */
	public int getAssistMemberCnt() {
		return assistMemberCnt;
	}

	/**
	 * @param assistMemberCnt the assistMemberCnt to set
	 */
	public void setAssistMemberCnt(int assistMemberCnt) {
		this.assistMemberCnt = assistMemberCnt;
	}

	/**
	 * @return the hasSptTeamMng
	 */
	public boolean isHasSptTeamMng() {
		return hasSptTeamMng;
	}

	/**
	 * @param hasSptTeamMng the hasSptTeamMng to set
	 */
	public void setHasSptTeamMng(boolean hasSptTeamMng) {
		this.hasSptTeamMng = hasSptTeamMng;
	}

	/**
	 * @return the sptTeamMngName
	 */
	public String getSptTeamMngName() {
		return sptTeamMngName;
	}

	/**
	 * @param sptTeamMngName the sptTeamMngName to set
	 */
	public void setSptTeamMngName(String sptTeamMngName) {
		this.sptTeamMngName = sptTeamMngName;
	}

	/**
	 * @return the sptTeamMngDept
	 */
	public String getSptTeamMngDept() {
		return sptTeamMngDept;
	}

	/**
	 * @param sptTeamMngDept the sptTeamMngDept to set
	 */
	public void setSptTeamMngDept(String sptTeamMngDept) {
		this.sptTeamMngDept = sptTeamMngDept;
	}

	/**
	 * @return the sptTeamMngSsn
	 */
	public String getSptTeamMngSsn() {
		return sptTeamMngSsn;
	}

	/**
	 * @param sptTeamMngSsn the sptTeamMngSsn to set
	 */
	public void setSptTeamMngSsn(String sptTeamMngSsn) {
		this.sptTeamMngSsn = sptTeamMngSsn;
	}

	/**
	 * @return the hasCeo
	 */
	public boolean isHasCeo() {
		return hasCeo;
	}

	/**
	 * @param hasCeo the hasCeo to set
	 */
	public void setHasCeo(boolean hasCeo) {
		this.hasCeo = hasCeo;
	}

	/**
	 * @return the ceoName
	 */
	public String getCeoName() {
		return ceoName;
	}

	/**
	 * @param ceoName the ceoName to set
	 */
	public void setCeoName(String ceoName) {
		this.ceoName = ceoName;
	}

	/**
	 * @return the ceoDept
	 */
	public String getCeoDept() {
		return ceoDept;
	}

	/**
	 * @param ceoDept the ceoDept to set
	 */
	public void setCeoDept(String ceoDept) {
		this.ceoDept = ceoDept;
	}

	/**
	 * @return the ceoSsn
	 */
	public String getCeoSsn() {
		return ceoSsn;
	}

	/**
	 * @param ceoSsn the ceoSsn to set
	 */
	public void setCeoSsn(String ceoSsn) {
		this.ceoSsn = ceoSsn;
	}

	/**
	 * @return the hasWholeApprove
	 */
	public boolean isHasWholeApprove() {
		return hasWholeApprove;
	}

	/**
	 * @param hasWholeApprove the hasWholeApprove to set
	 */
	public void setHasWholeApprove(boolean hasWholeApprove) {
		this.hasWholeApprove = hasWholeApprove;
	}

	/**
	 * @return the hasSptTeamMngWholeApprove
	 */
	public boolean isHasSptTeamMngWholeApprove() {
		return hasSptTeamMngWholeApprove;
	}

	/**
	 * @param hasSptTeamMngWholeApprove the hasSptTeamMngWholeApprove to set
	 */
	public void setHasSptTeamMngWholeApprove(boolean hasSptTeamMngWholeApprove) {
		this.hasSptTeamMngWholeApprove = hasSptTeamMngWholeApprove;
	}

	/**
	 * @return the hasAttach
	 */
	public boolean isHasAttach() {
		return hasAttach;
	}

	/**
	 * @param hasAttach the hasAttach to set
	 */
	public void setHasAttach(boolean hasAttach) {
		this.hasAttach = hasAttach;
	}

	/**
	 * @return the templateText
	 */
	public String getTemplateText() {
		return templateText;
	}

	/**
	 * @param templateText the templateText to set
	 */
	public void setTemplateText(String templateText) {
		this.templateText = templateText;
	}

	/**
	 * @return the useYn
	 */
	public boolean isUseYn() {
		return useYn;
	}

	/**
	 * @param useYn the useYn to set
	 */
	public void setUseYn(boolean useYn) {
		this.useYn = useYn;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the updateUser
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * @param updateUser the updateUser to set
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public boolean isHasTeamManager() {
		return hasTeamManager;
	}

	public void setHasTeamManager(boolean hasTeamManager) {
		this.hasTeamManager = hasTeamManager;
	}

	public boolean isHasCfo() {
		return hasCfo;
	}

	public void setHasCfo(boolean hasCfo) {
		this.hasCfo = hasCfo;
	}

	public boolean isHasCio() {
		return hasCio;
	}

	public void setHasCio(boolean hasCio) {
		this.hasCio = hasCio;
	}

	public String getRefMemberNames() {
		return refMemberNames;
	}

	public void setRefMemberNames(String refMemberNames) {
		this.refMemberNames = refMemberNames;
	}

	public String getRefMemberSsns() {
		return refMemberSsns;
	}

	public void setRefMemberSsns(String refMemberSsns) {
		this.refMemberSsns = refMemberSsns;
	}

	public String getSanctionType() {
		return sanctionType;
	}

	public void setSanctionType(String sanctionType) {
		this.sanctionType = sanctionType;
	}

	public boolean isUseMobile() {
		return useMobile;
	}

	public void setUseMobile(boolean useMobile) {
		this.useMobile = useMobile;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
