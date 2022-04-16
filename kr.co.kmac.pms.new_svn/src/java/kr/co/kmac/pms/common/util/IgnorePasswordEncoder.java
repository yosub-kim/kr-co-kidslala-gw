package kr.co.kmac.pms.common.util;

import org.acegisecurity.providers.encoding.PlaintextPasswordEncoder;

public class IgnorePasswordEncoder extends PlaintextPasswordEncoder {
	/**
	 * 입력된 패스워드를 무시하고 로그인 처리를 위해서 true 를 반환한다.
	 */
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		return true;
	}
}
