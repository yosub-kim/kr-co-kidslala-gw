/*
 * $Id: CostOverService.java,v 1.1 2009/09/19 11:15:37 cvs3 Exp $
 * created by    : 안성호
 * creation-date : 2006. 7. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.batch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import kr.co.kmac.pms.common.ajax.BizplayJSONWriter;
import kr.co.kmac.pms.service.exception.ServiceException;
import kr.co.kmac.pms.service.scheduler.data.bizplay.dvsn.BizplayDeptInfoSet;
import kr.co.kmac.pms.service.scheduler.data.bizplay.empl.BizplayExpertPoolInfoSet;
import kr.co.kmac.pms.service.scheduler.data.bizplay.pjt.BizplayProjectInfoSet;
import kr.co.kmac.pms.service.scheduler.manager.BizplayInfoSendManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BizplayInfoSendService {
	private static final Log logger = LogFactory.getLog(BizplayInfoSendService.class);
	private BizplayInfoSendManager bizplayInfoSendManager;

	public BizplayInfoSendManager getBizplayInfoSendManager() {
		return bizplayInfoSendManager;
	}

	public void setBizplayInfoSendManager(BizplayInfoSendManager bizplayInfoSendManager) {
		this.bizplayInfoSendManager = bizplayInfoSendManager;
	}

	private String sendToBizplay(String targetUrl, String enPoint, String sendTxt) throws Exception {
		String resultStr = null;
		try {
			System.out.println("sendTxt" + sendTxt);
			System.out.println("URL인코딩 text : " + URLEncoder.encode(sendTxt, "UTF-8"));

			URL url = new URL(targetUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);

			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write("JSONData=" + URLEncoder.encode(sendTxt, "UTF-8"));
			writer.flush();

			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			writer.close();
			reader.close();
			resultStr = builder.toString();

			System.out.println("result: " + resultStr);

			this.getBizplayInfoSendManager().insertHistory(enPoint, sendTxt, resultStr, "");

			logger.info(resultStr);
			logger.info("##  BizplayInfoSendService was executed... ");
		} catch (Exception e) {
			this.getBizplayInfoSendManager().insertHistory(enPoint, sendTxt, resultStr, e.getMessage());
			logger.info("##  BizplayInfoSendService was failed... ");
			logger.error(e.getMessage(), e);
			throw new Exception(e);
		}
		return resultStr;
	}

	public void sendProjectInfoToBizplay() throws Exception {
		BizplayJSONWriter jsonWriter = new BizplayJSONWriter();
		try {
			List<BizplayProjectInfoSet> bizplayProjectInfoSetList = this.getBizplayInfoSendManager().projectInfoJsonObject("C");
			if (bizplayProjectInfoSetList != null && bizplayProjectInfoSetList.size() > 0) {
				for (int i = 0; i < bizplayProjectInfoSetList.size(); i++) {
					System.out.println("sendProjectInfoToBizplay C" + i + "index :~~~~~~~~~");
					BizplayProjectInfoSet bizplayProjectInfoSet = bizplayProjectInfoSetList.get(i);
					bizplayProjectInfoSet.setAPI_KEY("ba0a66b8-e554-14ed-0a00-ce447681d53a");
					String sndTxt = jsonWriter.write(bizplayProjectInfoSet);
					sendToBizplay("https://webankapi.appplay.co.kr/gateway.do", bizplayProjectInfoSet.getAPI_ID(), sndTxt);// 운영
					Thread.sleep(5000l);
				}
			}
		} catch (ServiceException e) {
			throw new Exception(e);
		}
		try {
			List<BizplayProjectInfoSet> bizplayProjectInfoSetList = this.getBizplayInfoSendManager().projectInfoJsonObject("G");
			if (bizplayProjectInfoSetList != null && bizplayProjectInfoSetList.size() > 0) {
				for (int i = 0; i < bizplayProjectInfoSetList.size(); i++) {
					System.out.println("sendProjectInfoToBizplay G" + i + "index :~~~~~~~~~");
					BizplayProjectInfoSet bizplayProjectInfoSet = bizplayProjectInfoSetList.get(i);
					bizplayProjectInfoSet.setAPI_KEY("32f650df-2853-5b31-fbc7-2a1f8088be5e");
					String sndTxt = jsonWriter.write(bizplayProjectInfoSet);
					sendToBizplay("https://webankapi.appplay.co.kr/gateway.do", bizplayProjectInfoSet.getAPI_ID(), sndTxt);// 운영
					Thread.sleep(5000l);
				}
			}
		} catch (ServiceException e) {
			throw new Exception(e);
		}
		try {
			List<BizplayProjectInfoSet> bizplayProjectInfoSetList = this.getBizplayInfoSendManager().projectInfoJsonObject("M");
			if (bizplayProjectInfoSetList != null && bizplayProjectInfoSetList.size() > 0) {
				for (int i = 0; i < bizplayProjectInfoSetList.size(); i++) {
					System.out.println("sendProjectInfoToBizplay M" + i + "index :~~~~~~~~~");
					BizplayProjectInfoSet bizplayProjectInfoSet = bizplayProjectInfoSetList.get(i);
					bizplayProjectInfoSet.setAPI_KEY("754c6cf8-9313-da59-cd73-510acb3a4a12");
					String sndTxt = jsonWriter.write(bizplayProjectInfoSet);
					sendToBizplay("https://webankapi.appplay.co.kr/gateway.do", bizplayProjectInfoSet.getAPI_ID(), sndTxt);// 운영
					Thread.sleep(5000l);
				}
			}
		} catch (ServiceException e) {
			throw new Exception(e);
		}
	}

	public void sendAllProjectInfoToBizplay() throws Exception {
		BizplayJSONWriter jsonWriter = new BizplayJSONWriter();
		try {
			List<BizplayProjectInfoSet> bizplayProjectInfoSetList = this.getBizplayInfoSendManager().projectInfoJsonObjectAll("C");
			if (bizplayProjectInfoSetList != null && bizplayProjectInfoSetList.size() > 0) {
				for (int i = 0; i < bizplayProjectInfoSetList.size(); i++) {
					System.out.println("sendProjectInfoToBizplay C" + i + "index :~~~~~~~~~");
					BizplayProjectInfoSet bizplayProjectInfoSet = bizplayProjectInfoSetList.get(i);
					bizplayProjectInfoSet.setAPI_KEY("ba0a66b8-e554-14ed-0a00-ce447681d53a");
					String sndTxt = jsonWriter.write(bizplayProjectInfoSet);
					sendToBizplay("https://webankapi.appplay.co.kr/gateway.do", bizplayProjectInfoSet.getAPI_ID(), sndTxt);// 운영
					Thread.sleep(3000l);
				}
			}
		} catch (ServiceException e) {
			throw new Exception(e);
		}
		try {
			List<BizplayProjectInfoSet> bizplayProjectInfoSetList = this.getBizplayInfoSendManager().projectInfoJsonObjectAll("G");
			if (bizplayProjectInfoSetList != null && bizplayProjectInfoSetList.size() > 0) {
				for (int i = 0; i < bizplayProjectInfoSetList.size(); i++) {
					System.out.println("sendProjectInfoToBizplay G" + i + "index :~~~~~~~~~");
					BizplayProjectInfoSet bizplayProjectInfoSet = bizplayProjectInfoSetList.get(i);
					bizplayProjectInfoSet.setAPI_KEY("32f650df-2853-5b31-fbc7-2a1f8088be5e");
					String sndTxt = jsonWriter.write(bizplayProjectInfoSet);
					sendToBizplay("https://webankapi.appplay.co.kr/gateway.do", bizplayProjectInfoSet.getAPI_ID(), sndTxt);// 운영
					Thread.sleep(5000l);
				}
			}
		} catch (ServiceException e) {
			throw new Exception(e);
		}
		try {
			List<BizplayProjectInfoSet> bizplayProjectInfoSetList = this.getBizplayInfoSendManager().projectInfoJsonObjectAll("M");
			if (bizplayProjectInfoSetList != null && bizplayProjectInfoSetList.size() > 0) {
				for (int i = 0; i < bizplayProjectInfoSetList.size(); i++) {
					System.out.println("sendProjectInfoToBizplay M" + i + "index :~~~~~~~~~");
					BizplayProjectInfoSet bizplayProjectInfoSet = bizplayProjectInfoSetList.get(i);
					bizplayProjectInfoSet.setAPI_KEY("754c6cf8-9313-da59-cd73-510acb3a4a12");
					String sndTxt = jsonWriter.write(bizplayProjectInfoSet);
					sendToBizplay("https://webankapi.appplay.co.kr/gateway.do", bizplayProjectInfoSet.getAPI_ID(), sndTxt);// 운영
					Thread.sleep(5000l);
				}
			}
		} catch (ServiceException e) {
			throw new Exception(e);
		}
	}

	public void sendExpertPoolInfoToBizplay() throws Exception {
		try {
			BizplayJSONWriter jsonWriter = new BizplayJSONWriter();
			List<BizplayExpertPoolInfoSet> bizplayExpertPoolInfoSetList = this.getBizplayInfoSendManager().expertPoolJsonObject("");
			if (bizplayExpertPoolInfoSetList != null && bizplayExpertPoolInfoSetList.size() > 0) {
				for (int i = 0; i < bizplayExpertPoolInfoSetList.size(); i++) {
					System.out.println("sendExpertPoolInfoToBizplay  " + i + "index :~~~~~~~~~");
					BizplayExpertPoolInfoSet expertPoolInfoSet = bizplayExpertPoolInfoSetList.get(i);
					String sndTxt = jsonWriter.write(expertPoolInfoSet);
					sendToBizplay("https://emplinfo.appplay.co.kr/gw/ApiGate", expertPoolInfoSet.getSVC_CD(), sndTxt);// 운영
					Thread.sleep(5000l);
				}
			}

			logger.info("##  BizplayInfoSendService was executed... ");
		} catch (ServiceException e) {
			logger.info("##  BizplayInfoSendService was failed... ");
			logger.error(e.getMessage(), e);
		}
	}

	public void sendDeptInfoToBizplay() throws Exception {
		try {
			BizplayJSONWriter jsonWriter = new BizplayJSONWriter();
			List<BizplayDeptInfoSet> bizplayDeptInfoSetsList = this.getBizplayInfoSendManager().deptInfoJsonObject("");
			if (bizplayDeptInfoSetsList != null && bizplayDeptInfoSetsList.size() > 0) {
				for (int i = 0; i < bizplayDeptInfoSetsList.size(); i++) {
					System.out.println("sendDeptInfoToBizplay " + i + "index :~~~~~~~~~");
					BizplayDeptInfoSet bizplayDeptInfoSet = bizplayDeptInfoSetsList.get(i);
					String sndTxt = jsonWriter.write(bizplayDeptInfoSet);
					sendToBizplay("https://emplinfo.appplay.co.kr/gw/ApiGate", bizplayDeptInfoSet.getSVC_CD(), sndTxt);// 운영
					Thread.sleep(5000l);
				}
			}

			logger.info("##  BizplayInfoSendService was executed... ");
		} catch (ServiceException e) {
			logger.info("##  BizplayInfoSendService was failed... ");
			logger.error(e.getMessage(), e);
		}
	}

	public void bizplayInfoSend() {
		try {
			sendProjectInfoToBizplay();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			sendExpertPoolInfoToBizplay();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			sendDeptInfoToBizplay();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
