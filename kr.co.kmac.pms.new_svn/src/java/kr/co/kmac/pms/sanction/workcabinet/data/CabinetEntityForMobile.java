package kr.co.kmac.pms.sanction.workcabinet.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import kr.co.kmac.pms.sanction.workcabinet.data.CabinetEntity;

public class CabinetEntityForMobile extends CabinetEntity {
	private String useMobile;
	
	/**
	 * @return the useMobile
	 */
	public String getUseMobile() {
		return useMobile;
	}

	/**
	 * @param useMobile the useMobile to set
	 */
	public void setUseMobile(String useMobile) {
		this.useMobile = useMobile;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
