/*
 * $Id: ResponseFormTransactionInterceptor.java,v 1.3 2011/03/20 11:27:35 cvs Exp $
 * created by    : 최인호
 * creation-date : 2005. 12. 12.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.transaction;

import java.util.Properties;

import kr.co.kmac.pms.common.form.ResponseForm;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

/**
 * TransactionInterceptor와 같지만, return 값을 체크해서 그 값에 따라 롤백을 시킬 수 있도록 하는 구현 추가.
 * <p>
 * TransactionInterceptor에 대한 자세한 설명은 springframework의 javadoc을 참조한다. doTransaction
 * 
 * @author JiwoongLee
 * @version $Id: ResponseFormTransactionInterceptor.java,v 1.3 2011/03/20 11:27:35 cvs Exp $
 */
public class ResponseFormTransactionInterceptor extends TransactionAspectSupport implements MethodInterceptor {
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 3214307364430420215L;

	private boolean rollBackOnFail = true;

	public ResponseFormTransactionInterceptor() {
	}

	public ResponseFormTransactionInterceptor(PlatformTransactionManager ptm, Properties attributes) {
		setTransactionManager(ptm);
		setTransactionAttributes(attributes);
	}

	public ResponseFormTransactionInterceptor(PlatformTransactionManager ptm, TransactionAttributeSource tas) {
		setTransactionManager(ptm);
		setTransactionAttributeSource(tas);
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		// Work out the target class: may be <code>null</code>.
		// The TransactionAttributeSource should be passed the target class
		// as well as the method, which may be from an interface
		Class targetClass = (invocation.getThis() != null) ? invocation.getThis().getClass() : null;

		// Create transaction if necessary.
		TransactionInfo txInfo = createTransactionIfNecessary(invocation.getMethod(), targetClass);

		Object retVal = null;
		try {
			// This is an around advice.
			// Invoke the next interceptor in the chain.
			// This will normally result in a target object being invoked.
			retVal = invocation.proceed();

			// 결과값을 체크한다.
			Object[] args = invocation.getArguments();
			if (args != null && args.length > 2 && args[1] != null && args[1] instanceof ResponseForm) {
				ResponseForm resForm = (ResponseForm) args[1];
// 주석-20110319: 이지웅 - 				
//				if (!resForm.isSuccess() && this.rollBackOnFail) {
//					doCloseTransaction(txInfo, resForm.getThrowable());
//					return retVal;
//				}
			}
		} catch (Throwable ex) {
			// target invocation exception
			completeTransactionAfterThrowing(txInfo, ex);
			throw ex;
		} finally {
			cleanupTransactionInfo(txInfo);
		}
		commitTransactionAfterReturning(txInfo);
		return retVal;
	}

	protected void doCloseTransaction(TransactionInfo txInfo, Throwable ex) {
		if (ex == null) {
			logger.warn("Throwable of response form is null.");
			ex = new NullPointerException("Throwable of response form is null.");
		}
		if (txInfo.hasTransaction()) {
			if (logger.isDebugEnabled()) {
				logger.debug("Invoking rollback for transaction on " + txInfo.getJoinpointIdentification() + " due to throwable [" + ex + "]");
			}
			try {
				this.getTransactionManager().rollback(txInfo.getTransactionStatus());
			} catch (IllegalTransactionStateException ex3) {
				logger.error("Transaction is already completed - do not call commit or rollback more than once per transaction");
			} catch (TransactionSystemException ex4) {
				logger.error("Could not roll back JDBC transaction");
			} catch (RuntimeException ex2) {
				logger.error("Application exception overridden by rollback exception", ex);
				throw ex2;
			} catch (Error err) {
				logger.error("Application exception overridden by rollback error", ex);
				throw err;
			}
		}
	}

	/**
	 * @return Returns the rollBackOnFail.
	 */
	public boolean isRollBackOnFail() {
		return this.rollBackOnFail;
	}

	/**
	 * @param rollBackOnFail The rollBackOnFail to set.
	 */
	public void setRollBackOnFail(boolean rollBackOnFail) {
		this.rollBackOnFail = rollBackOnFail;
	}
}
