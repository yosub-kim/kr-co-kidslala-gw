package kr.co.kmac.pms.sanction.projectchange.form;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import kr.co.kmac.pms.sanction.general.form.SanctionForm;

/**
 * @struts:form name="budgetChangeSanctionAction"
 */
public class BudgetSanctionForm extends SanctionForm {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2626588091596310962L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
