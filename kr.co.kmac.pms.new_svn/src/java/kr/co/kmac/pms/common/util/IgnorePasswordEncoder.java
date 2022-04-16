package kr.co.kmac.pms.common.util;

import org.acegisecurity.providers.encoding.PlaintextPasswordEncoder;

public class IgnorePasswordEncoder extends PlaintextPasswordEncoder {
	/**
	 * �Էµ� �н����带 �����ϰ� �α��� ó���� ���ؼ� true �� ��ȯ�Ѵ�.
	 */
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		return true;
	}
}
