package kr.co.kmac.pms.sanction.line.form;

import kr.co.kmac.pms.sanction.line.data.SanctionLine;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts.action.ActionForm;

/**
 * @struts:form name="SanctionLineAction"
 */
public class SanctionLineForm extends ActionForm implements SanctionLine {

	private static final long serialVersionUID = -1382335934350135263L;

	private String id;
	/**
	 * ±‚æ»
	 */
	private String registerSsn;
	private String registerDept;
	private String registerName;
	/**
	 * ∞À≈‰
	 */
	private String teamManagerSsn;
	private String teamManagerDept;
	private String teamManagerName;
	/**
	 * Ω¬¿Œ
	 */
	private String cfoSsn;
	private String cfoDept;
	private String cfoName;
	/**
	 * Ω¬¿Œ
	 */
	private String cioSsn;
	private String cioDept;
	private String cioName;
	/**
	 * «˘¿«1
	 */
	private String assistant1Ssn;
	private String assistant1Dept;
	private String assistant1Name;
	/**
	 * «˘¿«2
	 */
	private String assistant2Ssn;
	private String assistant2Dept;
	private String assistant2Name;
	/**
	 * ¥Î«•¿ÃªÁ
	 */
	private String ceoSsn;
	private String ceoDept;
	private String ceoName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegisterSsn() {
		return registerSsn;
	}

	public void setRegisterSsn(String registerSsn) {
		this.registerSsn = registerSsn;
	}

	public String getRegisterDept() {
		return registerDept;
	}

	public void setRegisterDept(String registerDept) {
		this.registerDept = registerDept;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getTeamManagerSsn() {
		return teamManagerSsn;
	}

	public void setTeamManagerSsn(String teamManagerSsn) {
		this.teamManagerSsn = teamManagerSsn;
	}

	public String getTeamManagerDept() {
		return teamManagerDept;
	}

	public void setTeamManagerDept(String teamManagerDept) {
		this.teamManagerDept = teamManagerDept;
	}

	public String getTeamManagerName() {
		return teamManagerName;
	}

	public void setTeamManagerName(String teamManagerName) {
		this.teamManagerName = teamManagerName;
	}

	public String getCfoSsn() {
		return cfoSsn;
	}

	public void setCfoSsn(String cfoSsn) {
		this.cfoSsn = cfoSsn;
	}

	public String getCfoDept() {
		return cfoDept;
	}

	public void setCfoDept(String cfoDept) {
		this.cfoDept = cfoDept;
	}

	public String getCfoName() {
		return cfoName;
	}

	public void setCfoName(String cfoName) {
		this.cfoName = cfoName;
	}

	public String getAssistant1Ssn() {
		return assistant1Ssn;
	}

	public void setAssistant1Ssn(String assistant1Ssn) {
		this.assistant1Ssn = assistant1Ssn;
	}

	public String getAssistant1Dept() {
		return assistant1Dept;
	}

	public void setAssistant1Dept(String assistant1Dept) {
		this.assistant1Dept = assistant1Dept;
	}

	public String getAssistant1Name() {
		return assistant1Name;
	}

	public void setAssistant1Name(String assistant1Name) {
		this.assistant1Name = assistant1Name;
	}

	public String getAssistant2Ssn() {
		return assistant2Ssn;
	}

	public void setAssistant2Ssn(String assistant2Ssn) {
		this.assistant2Ssn = assistant2Ssn;
	}

	public String getAssistant2Dept() {
		return assistant2Dept;
	}

	public void setAssistant2Dept(String assistant2Dept) {
		this.assistant2Dept = assistant2Dept;
	}

	public String getAssistant2Name() {
		return assistant2Name;
	}

	public void setAssistant2Name(String assistant2Name) {
		this.assistant2Name = assistant2Name;
	}

	public String getCeoSsn() {
		return ceoSsn;
	}

	public void setCeoSsn(String ceoSsn) {
		this.ceoSsn = ceoSsn;
	}

	public String getCeoDept() {
		return ceoDept;
	}

	public void setCeoDept(String ceoDept) {
		this.ceoDept = ceoDept;
	}

	public String getCeoName() {
		return ceoName;
	}

	public void setCeoName(String ceoName) {
		this.ceoName = ceoName;
	}

	public String getCioSsn() {
		return cioSsn;
	}

	public void setCioSsn(String cioSsn) {
		this.cioSsn = cioSsn;
	}

	public String getCioDept() {
		return cioDept;
	}

	public void setCioDept(String cioDept) {
		this.cioDept = cioDept;
	}

	public String getCioName() {
		return cioName;
	}

	public void setCioName(String cioName) {
		this.cioName = cioName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
