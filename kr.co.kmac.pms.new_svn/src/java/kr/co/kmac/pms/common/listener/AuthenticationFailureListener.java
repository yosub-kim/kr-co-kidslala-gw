package kr.co.kmac.pms.common.listener;

import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;

import org.acegisecurity.event.authentication.AuthenticationFailureBadCredentialsEvent;
import org.acegisecurity.event.authentication.AuthenticationFailureLockedEvent;
import org.acegisecurity.event.authentication.LoggerListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class AuthenticationFailureListener implements ApplicationListener{
	
	private static final Log logger = LogFactory.getLog(LoggerListener.class);
	private ExpertPoolManager expertPoolManager;
	
	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

	public void onApplicationEvent(ApplicationEvent event) {
		
		if (event instanceof AuthenticationFailureBadCredentialsEvent) {
			AuthenticationFailureBadCredentialsEvent res = (AuthenticationFailureBadCredentialsEvent)event;
			try {
				ExpertPool expertPool = getExpertPoolManager().retrieveUserId(res.getAuthentication().getPrincipal().toString());
				if(expertPool != null){				
					//System.out.println(expertPool.getName());
					// 로그인 실패 횟수를 1 증가한다.
					int failCnt = getExpertPoolManager().increaseLoginAttepmtCnt(expertPool.getSsn());
					if(failCnt >= 10){
						getExpertPoolManager().accountLocked(expertPool.getSsn());
					}
					// logger.info("getMessage1 : " + res.getException().getMessage());
					// logger.info("getMessage2 : " + res.getAuthentication().getPrincipal().toString());
					// logger.info("getMessage3 : " + res.getAuthentication().getName().toString());
					// logger.info("getMessage4 : " + res.getAuthentication().getCredentials().toString());
					// logger.info("getMessage5 : " + res.getAuthentication().getDetails().toString());
					// logger.info("getMessage6 : loginAttemptCnt("+ failCnt + ")");
					//  System.out.println("AAAAAAA");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		} else if (event instanceof AuthenticationFailureLockedEvent) {
			AuthenticationFailureLockedEvent res = (AuthenticationFailureLockedEvent)event;
			//System.out.println("BBBBBBB");
		}
		
	}

	
}
