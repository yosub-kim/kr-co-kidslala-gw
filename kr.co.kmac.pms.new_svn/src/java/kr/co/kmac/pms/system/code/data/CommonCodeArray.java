package kr.co.kmac.pms.system.code.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import kr.co.kmac.pms.common.util.StringUtil;

public class CommonCodeArray {
	private String codeLabel;
	private String codeId;
	private String codeValue;
	private String codeDisplay;

	public CommonCodeArray() {
		super();
	}

	public CommonCodeArray(String codeLabel, String codeId, String codeValue, String codeDisplay) {
		super();
		this.codeLabel = codeLabel;
		this.codeId = codeId;
		this.codeValue = codeValue;

		this.codeDisplay = (StringUtil.isNull(codeLabel, "").equals("")) ? codeDisplay : "inline";
	}

	public String getCodeLabel() {
		return codeLabel;
	}

	public void setCodeLabel(String codeLabel) {
		this.codeLabel = codeLabel;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getCodeDisplay() {
		return codeDisplay;
	}

	public void setCodeDisplay(String codeDisplay) {
		this.codeDisplay = codeDisplay;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}