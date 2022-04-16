/*
 * $Id: ExpertPool.java,v 1.17 2019/03/03 17:27:51 cvs Exp $ created by :
 * jiwoongLee creation-date : 2006. 10. 13.
 * ========================================================= Copyright (c) 2005
 * Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.form.ExpertPoolForm;

/**
 * 전문가 풀관련 모델 객체
 * 
 * @author jiwoongLee
 * @version $Id: ExpertPool.java,v 1.17 2019/03/03 17:27:51 cvs Exp $
 */
public class ExpertPool { 

	private String ssn;
	private String name;
	private String chnName;
	private String engName;
	private String password;
	private String enable;
	private String absence;
	private String gender;
	private String nationality;
	private String telNo;
	private String mobileNo;
	private String zipCode;
	private String address1;
	private String address2;
	private String companyId;
	private String company;
	private String dept;
	private String deptName;
	private String companyPosition;
	private String companyPositionName;
	private String jobClass;
	private String companyZipcode;
	private String companyAddress1;
	private String companyAddress2;
	private String companyTelNo;
	private String companyFaxNo;
	private String email;
	private String photo;
	private String resume;
	private String resumeName;
	private String finalMajor;
	private String langSkill1;
	private String langSkill1Level;
	private String langSkill2;
	private String langSkill2Level;
	private String langSkill3;
	private String langSkill3Level;
	private String essayTitle;
	private String uid;
	private String salaryMailingYN;

	// 컨실팅 관련 세부 정보
	private String indField;
	private String bizField;
	private String funcField;
	private String consultingMajor;
	private String consultingMinor;
	private String consultingDetail;
	private String researchEassy;
	private String publishedBook;
	private Date createdDate;
	private String createrId;
	private Date modifiedDate;
	private Date lastModifiedDate;
	private String modifierId;
	private String remark;
	private String emplNo;

	private List<ExpertPoolCareerHst> expertPoolCareerHst;
	private List<ExpertPoolSchoolHst> expertPoolSchoolHst;
	private List<ExpertPoolProjectHst> expertPoolProjectHst;
	private String expertPoolSpecialFunctionNameDistinct;
	private List<ExpertPoolSpecialField> expertPoolSpecialFields = new ArrayList<ExpertPoolSpecialField>();

	// 전문가 Skill 추가
	private String gubun;
	private String total;
	private String employ;
	private String contractEmploy;
	private String consultant;
	private String industryExpert;
	private String professor;
	private String businessExpert;
	private String etcEmploy;

	private String projectName;
	private String projectMemberCount;

	private String endRate;

	private String role;
	private String extRole;

	private String userId;

	private String rid;

	private String rate;
	private String rank;
	private String basicSalary;

	private String acctBeginDate;
	private String acctExpireDate;
	private String blockDownload;
	private String accountNonLocked;

	// 성과급 기준 추가
	private String minAmt;
	private String maxAmt;
	private String minEdu;
	private String maxEdu;
	private String minMMAmt;
	private String maxMMAmt;
	
	// 엑스퍼트 관리 추가
	private String realStartDate;
	private String realEndDate;
	private String projectTypeCode;
	private String projectManager;
	private String des;
	private String ssnn;
	private String expertCnt;
	private String position;
	
	// ra연차 확인
	private String deptCnt;
	private String restday_1;
	private String useday;
	private String restday;
	
	// 스케줄 화면
	private String groupcount;
	private String usercount;
	private String posName;
	private String year;
	private String month;
	private String day;
	private String startHour;
	private String startMin;
	private String endHour;
	private String endMin;
	private String groupName;
	private String content;
	private String place;
	private String filterdate;
	private String dateCnt;
	private String workType;
	private String relationUser;
	private String labelName;
	private String projectCode;
	private String emplno;
	
	//스케줄 현황
	private String M01;
	private String M02;
	private String M03;
	private String M04;
	private String M05;
	private String M06;
	private String M07;
	private String M08;
	private String M09;
	private String M10;
	private String M11;
	private String M12;
	private String M13;
	private String M14;
	private String M15;
	private String M16;
	private String M17;
	private String M18;
	private String M19;
	private String M20;
	private String M21;
	private String M22;
	private String M23;
	private String M24;
	private String M25;
	private String M26;
	private String M27;
	private String M28;
	private String M29;
	private String M30;
	private String M31;
	
	private String userName;
	private String groupSeq;
	private String gruopcount;
	private String googleSyncId;
	private String customerCount;
	
	private String ssnCount;
	private String cusCount;
	private String result;
	private String ssnResult;
	private String cusResult;
	private String customerName;
	
	private String type;
	private String mm;
	private String addOccur2;
	private String jointUse;
	
	private String workOn;
	private String workOff;
	
	private String restrictContents;
	
	private String registerDate;
	private String sum;
	private String detailSum;
	private String detailBen;
	private String runningDeptCode;
	private String description;
	private String checkYN;
	private String aliasName;
	private String checkValue;
	private String companyNum;
	private String writeDate;
	private String readDate;
	private String resultPosition;
	private String businessName;
	private String sumMoney;
	
	private String resultYN;
	private String attachFileId;
	private String attachCreatorId;
	private String deptYN;
	private String downloadCnt;
	
	private String resultDate;
	private String entno;
	private String ssnCnt;
	
	private String progressCnt;
	private String stateChk;
	private String gubunChk;
	private String monthlyAmount;
	private String runningDivCode;
	private String prj_Amount;
	
	private String birthDay;
	private String pm;
	
	private String eval;
	private String grade;
	private String contents;
	private String evalContent;
	
	private String pmName;
	private String pjtName;
	private String amount;
	private String ref;
	private String monthCnt;
	private String typeCnt;
	
	public String getMonthCnt() {
		return monthCnt;
	}

	public void setMonthCnt(String monthCnt) {
		this.monthCnt = monthCnt;
	}

	public String getTypeCnt() {
		return typeCnt;
	}

	public void setTypeCnt(String typeCnt) {
		this.typeCnt = typeCnt;
	}

	public String getPmName() {
		return pmName;
	}

	public void setPmName(String pmName) {
		this.pmName = pmName;
	}

	public String getPjtName() {
		return pjtName;
	}

	public void setPjtName(String pjtName) {
		this.pjtName = pjtName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getEvalContent() {
		return evalContent;
	}

	public void setEvalContent(String evalContent) {
		this.evalContent = evalContent;
	}

	public String getEval() {
		return eval;
	}

	public void setEval(String eval) {
		this.eval = eval;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getPrj_Amount() {
		return prj_Amount;
	}

	public void setPrj_Amount(String prj_Amount) {
		this.prj_Amount = prj_Amount;
	}

	public String getRunningDivCode() {
		return runningDivCode;
	}

	public void setRunningDivCode(String runningDivCode) {
		this.runningDivCode = runningDivCode;
	}

	public String getMonthlyAmount() {
		return monthlyAmount;
	}

	public void setMonthlyAmount(String monthlyAmount) {
		this.monthlyAmount = monthlyAmount;
	}

	public String getGubunChk() {
		return gubunChk;
	}

	public void setGubunChk(String gubunChk) {
		this.gubunChk = gubunChk;
	}

	public String getProgressCnt() {
		return progressCnt;
	}

	public void setProgressCnt(String progressCnt) {
		this.progressCnt = progressCnt;
	}

	public String getStateChk() {
		return stateChk;
	}

	public void setStateChk(String stateChk) {
		this.stateChk = stateChk;
	}

	public String getResultDate() {
		return resultDate;
	}

	public void setResultDate(String resultDate) {
		this.resultDate = resultDate;
	}

	public String getEntno() {
		return entno;
	}

	public void setEntno(String entno) {
		this.entno = entno;
	}

	public String getSsnCnt() {
		return ssnCnt;
	}

	public void setSsnCnt(String ssnCnt) {
		this.ssnCnt = ssnCnt;
	}
	
	public String getDownloadCnt() {
		return downloadCnt;
	}

	public void setDownloadCnt(String downloadCnt) {
		this.downloadCnt = downloadCnt;
	}

	public String getDeptYN() {
		return deptYN;
	}

	public void setDeptYN(String deptYN) {
		this.deptYN = deptYN;
	}

	public String getResultYN() {
		return resultYN;
	}

	public void setResultYN(String resultYN) {
		this.resultYN = resultYN;
	}

	public String getAttachFileId() {
		return attachFileId;
	}

	public void setAttachFileId(String attachFileId) {
		this.attachFileId = attachFileId;
	}

	public String getAttachCreatorId() {
		return attachCreatorId;
	}

	public void setAttachCreatorId(String attachCreatorId) {
		this.attachCreatorId = attachCreatorId;
	}

	public String getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(String sumMoney) {
		this.sumMoney = sumMoney;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getResultPosition() {
		return resultPosition;
	}

	public void setResultPosition(String resultPosition) {
		this.resultPosition = resultPosition;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public String getReadDate() {
		return readDate;
	}

	public void setReadDate(String readDate) {
		this.readDate = readDate;
	}

	public String getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}

	public String getCompanyNum() {
		return companyNum;
	}

	public void setCompanyNum(String companyNum) {
		this.companyNum = companyNum;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getCheckYN() {
		return checkYN;
	}

	public void setCheckYN(String checkYN) {
		this.checkYN = checkYN;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getDetailSum() {
		return detailSum;
	}

	public void setDetailSum(String detailSum) {
		this.detailSum = detailSum;
	}

	public String getDetailBen() {
		return detailBen;
	}

	public void setDetailBen(String detailBen) {
		this.detailBen = detailBen;
	}

	public String getRunningDeptCode() {
		return runningDeptCode;
	}

	public void setRunningDeptCode(String runningDeptCode) {
		this.runningDeptCode = runningDeptCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRestrictContents() {
		return restrictContents;
	}

	public void setRestrictContents(String restrictContents) {
		this.restrictContents = restrictContents;
	}

	public String getWorkOn() {
		return workOn;
	}

	public void setWorkOn(String workOn) {
		this.workOn = workOn;
	}

	public String getWorkOff() {
		return workOff;
	}

	public void setWorkOff(String workOff) {
		this.workOff = workOff;
	}

	public String getJointUse() {
		return jointUse;
	}

	public void setJointUse(String jointUse) {
		this.jointUse = jointUse;
	}

	public String getMm() {
		return mm;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

	public String getAddOccur2() {
		return addOccur2;
	}

	public void setAddOccur2(String addOccur2) {
		this.addOccur2 = addOccur2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSsnResult() {
		return ssnResult;
	}

	public void setSsnResult(String ssnResult) {
		this.ssnResult = ssnResult;
	}

	public String getCusResult() {
		return cusResult;
	}

	public void setCusResult(String cusResult) {
		this.cusResult = cusResult;
	}

	public String getSsnCount() {
		return ssnCount;
	}

	public void setSsnCount(String ssnCount) {
		this.ssnCount = ssnCount;
	}

	public String getCusCount() {
		return cusCount;
	}

	public void setCusCount(String cusCount) {
		this.cusCount = cusCount;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(String customerCount) {
		this.customerCount = customerCount;
	}

	public String getGoogleSyncId() {
		return googleSyncId;
	}

	public void setGoogleSyncId(String googleSyncId) {
		this.googleSyncId = googleSyncId;
	}

	public String getGruopcount() {
		return gruopcount;
	}

	public void setGruopcount(String gruopcount) {
		this.gruopcount = gruopcount;
	}

	public String getGroupSeq() {
		return groupSeq;
	}

	public void setGroupSeq(String groupSeq) {
		this.groupSeq = groupSeq;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmplno() {
		return emplno;
	}

	public String getM01() {
		return M01;
	}

	public void setM01(String m01) {
		M01 = m01;
	}

	public String getM02() {
		return M02;
	}

	public void setM02(String m02) {
		M02 = m02;
	}

	public String getM03() {
		return M03;
	}

	public void setM03(String m03) {
		M03 = m03;
	}

	public String getM04() {
		return M04;
	}

	public void setM04(String m04) {
		M04 = m04;
	}

	public String getM05() {
		return M05;
	}

	public void setM05(String m05) {
		M05 = m05;
	}

	public String getM06() {
		return M06;
	}

	public void setM06(String m06) {
		M06 = m06;
	}

	public String getM07() {
		return M07;
	}

	public void setM07(String m07) {
		M07 = m07;
	}

	public String getM08() {
		return M08;
	}

	public void setM08(String m08) {
		M08 = m08;
	}

	public String getM09() {
		return M09;
	}

	public void setM09(String m09) {
		M09 = m09;
	}

	public String getM10() {
		return M10;
	}

	public void setM10(String m10) {
		M10 = m10;
	}

	public String getM11() {
		return M11;
	}

	public void setM11(String m11) {
		M11 = m11;
	}

	public String getM12() {
		return M12;
	}

	public void setM12(String m12) {
		M12 = m12;
	}

	public String getM13() {
		return M13;
	}

	public void setM13(String m13) {
		M13 = m13;
	}

	public String getM14() {
		return M14;
	}

	public void setM14(String m14) {
		M14 = m14;
	}

	public String getM15() {
		return M15;
	}

	public void setM15(String m15) {
		M15 = m15;
	}

	public String getM16() {
		return M16;
	}

	public void setM16(String m16) {
		M16 = m16;
	}

	public String getM17() {
		return M17;
	}

	public void setM17(String m17) {
		M17 = m17;
	}

	public String getM18() {
		return M18;
	}

	public void setM18(String m18) {
		M18 = m18;
	}

	public String getM19() {
		return M19;
	}

	public void setM19(String m19) {
		M19 = m19;
	}

	public String getM20() {
		return M20;
	}

	public void setM20(String m20) {
		M20 = m20;
	}

	public String getM21() {
		return M21;
	}

	public void setM21(String m21) {
		M21 = m21;
	}

	public String getM22() {
		return M22;
	}

	public void setM22(String m22) {
		M22 = m22;
	}

	public String getM23() {
		return M23;
	}

	public void setM23(String m23) {
		M23 = m23;
	}

	public String getM24() {
		return M24;
	}

	public void setM24(String m24) {
		M24 = m24;
	}

	public String getM25() {
		return M25;
	}

	public void setM25(String m25) {
		M25 = m25;
	}

	public String getM26() {
		return M26;
	}

	public void setM26(String m26) {
		M26 = m26;
	}

	public String getM27() {
		return M27;
	}

	public void setM27(String m27) {
		M27 = m27;
	}

	public String getM28() {
		return M28;
	}

	public void setM28(String m28) {
		M28 = m28;
	}

	public String getM29() {
		return M29;
	}

	public void setM29(String m29) {
		M29 = m29;
	}

	public String getM30() {
		return M30;
	}

	public void setM30(String m30) {
		M30 = m30;
	}

	public String getM31() {
		return M31;
	}

	public void setM31(String m31) {
		M31 = m31;
	}

	public void setEmplno(String emplno) {
		this.emplno = emplno;
	}

	public String getProjectCode(){
		return projectCode;
	}
	
	public void setProjectCode(String projectCode){
		this.projectCode = projectCode;
	}
	
	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	// 스케줄 화면
	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getRelationUser() {
		return relationUser;
	}

	public void setRelationUser(String relationUser) {
		this.relationUser = relationUser;
	}
	
	public String getPosName() {
		return posName;
	}

	public String getGroupcount() {
		return groupcount;
	}

	public String getFilterdate() {
		return filterdate;
	}

	public void setFilterdate(String filterdate) {
		this.filterdate = filterdate;
	}

	public void setGroupcount(String groupcount) {
		this.groupcount = groupcount;
	}

	public String getDateCnt() {
		return dateCnt;
	}

	public void setDateCnt(String dateCnt) {
		this.dateCnt = dateCnt;
	}

	public String getUsercount() {
		return usercount;
	}

	public void setUsercount(String usercount) {
		this.usercount = usercount;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getYear() {
		return year;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getStartMin() {
		return startMin;
	}

	public void setStartMin(String startMin) {
		this.startMin = startMin;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public String getEndMin() {
		return endMin;
	}

	public void setEndMin(String endMin) {
		this.endMin = endMin;
	}

	public String getPosition(){
		return position;
	}
	public void setPosition(String position){
		this.position = position; 
	}
	
	public String getRestday_1(){
		return restday_1;
	}
	public void setRestday_1(String restday_1){
		this.restday_1 = restday_1;
	}
	
	public String getUseday(){
		return useday;
	}
	public void setUseday(String useday){
		this.useday = useday;
	}
	
	public String getRestday(){
		return restday;
	}
	public void setRestday(String restday){
		this.restday = restday;
	}
	
	public String getDeptCnt(){
		return deptCnt;
	}
	public void setDeptCnt(String deptCnt){
		this.deptCnt = deptCnt;
	}
	
	public String getExpertCnt(){
		return expertCnt;
	}
	
	public void setExpertCnt(String expertCnt){
		this.expertCnt = expertCnt;
	}
	
	public String getSsnn(){
		return ssnn;
	}
	
	public void setSsnn(String ssnn){
		this.ssnn = ssnn;
	}
	
	public String getDes(){
		return des;
	}
	
	public void setDes(String des){
		this.des = des;
	}
	
	public String getProjectManager(){
		return projectManager;
	}
	
	public void setProjectManager(String projectManager){
		this.projectManager = projectManager;
	}
	
	public String getProjectTypeCode(){
		return projectTypeCode;
	}
	
	public void setProjectTypeCode(String projectTypeCode){
		this.projectTypeCode = projectTypeCode;
	}
	
	public String getRealStartDate (){
		return realStartDate; 
	}
	
	public void setRealStartDate(String realStartDate){
		this.realStartDate = realStartDate;
	}
	
	public String getRealEndDate (){
		return realEndDate; 
	}
	
	public void setRealEndDate(String realEndDate){
		this.realEndDate = realEndDate;
	}

	public String getMinAmt() {
		return minAmt;
	}

	public void setMinAmt(String minAmt) {
		this.minAmt = minAmt;
	}

	public String getMaxAmt() {
		return maxAmt;
	}

	public void setMaxAmt(String maxAmt) {
		this.maxAmt = maxAmt;
	}

	public String getMinEdu() {
		return minEdu;
	}

	public void setMinEdu(String minEdu) {
		this.minEdu = minEdu;
	}

	public String getMaxEdu() {
		return maxEdu;
	}

	public void setMaxEdu(String maxEdu) {
		this.maxEdu = maxEdu;
	}

	/**
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the chnName
	 */
	public String getChnName() {
		return chnName;
	}

	/**
	 * @param chnName the chnName to set
	 */
	public void setChnName(String chnName) {
		this.chnName = chnName;
	}

	/**
	 * @return the engName
	 */
	public String getEngName() {
		return engName;
	}

	/**
	 * @param engName the engName to set
	 */
	public void setEngName(String engName) {
		this.engName = engName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the enable
	 */
	public String getEnable() {
		return enable;
	}

	/**
	 * @param enable the enable to set
	 */
	public void setEnable(String enable) {
		this.enable = enable;
	}

	/**
	 * @return the absence
	 */
	public String getAbsence() {
		return absence;
	}

	/**
	 * @param absence the absence to set
	 */
	public void setAbsence(String absence) {
		this.absence = absence;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * @param nationality the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * @return the telNo
	 */
	public String getTelNo() {
		return telNo;
	}

	/**
	 * @param telNo the telNo to set
	 */
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * 
	 * @return the dash를 포함한 zipCode를 return
	 */
	public String getZipCodeInDash() {
		String tmp = "";
		if ((zipCode == null) || (zipCode.equals("")))
			tmp = "";
		else
			tmp = zipCode.substring(0, 3) + "-" + zipCode.substring(3, zipCode.length());
		return tmp;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the dept
	 */
	public String getDept() {
		return dept;
	}

	/**
	 * @param dept the dept to set
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCompanyPositionName() {
		return companyPositionName;
	}

	public void setCompanyPositionName(String companyPositionName) {
		this.companyPositionName = companyPositionName;
	}

	/**
	 * @return the companyPosition
	 */
	public String getCompanyPosition() {
		return companyPosition;
	}

	/**
	 * @param companyPosition the companyPosition to set
	 */
	public void setCompanyPosition(String companyPosition) {
		this.companyPosition = companyPosition;
	}

	/**
	 * @return the jobClass
	 */
	public String getJobClass() {
		return jobClass;
	}

	/**
	 * @param jobClass the jobClass to set
	 */
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	/**
	 * @return the companyZipcode
	 */
	public String getCompanyZipcode() {
		return companyZipcode;
	}

	/**
	 * 
	 * @return
	 */
	public String getCompanyZipcodeInDash() {
		String tmp = "";
		if ((companyZipcode == null) || (companyZipcode.equals("")))
			tmp = "";
		else
			tmp = companyZipcode.substring(0, 3) + "-" + companyZipcode.substring(3, companyZipcode.length());
		return tmp;
	}

	/**
	 * @param companyZipcode the companyZipcode to set
	 */
	public void setCompanyZipcode(String companyZipcode) {
		this.companyZipcode = companyZipcode;
	}

	/**
	 * @return the companyAddress1
	 */
	public String getCompanyAddress1() {
		return companyAddress1;
	}

	/**
	 * @param companyAddress1 the companyAddress1 to set
	 */
	public void setCompanyAddress1(String companyAddress1) {
		this.companyAddress1 = companyAddress1;
	}

	/**
	 * @return the companyAddress2
	 */
	public String getCompanyAddress2() {
		return companyAddress2;
	}

	/**
	 * @param companyAddress2 the companyAddress2 to set
	 */
	public void setCompanyAddress2(String companyAddress2) {
		this.companyAddress2 = companyAddress2;
	}

	/**
	 * @return the companyTelNo
	 */
	public String getCompanyTelNo() {
		return companyTelNo;
	}

	/**
	 * @param companyTelNo the companyTelNo to set
	 */
	public void setCompanyTelNo(String companyTelNo) {
		this.companyTelNo = companyTelNo;
	}

	/**
	 * @return the companyFaxNo
	 */
	public String getCompanyFaxNo() {
		return companyFaxNo;
	}

	/**
	 * @param companyFaxNo the companyFaxNo to set
	 */
	public void setCompanyFaxNo(String companyFaxNo) {
		this.companyFaxNo = companyFaxNo;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}

	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	/**
	 * @return the resume
	 */
	public String getResume() {
		return resume;
	}

	/**
	 * @param resume the resume to set
	 */
	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getResumeName() {
		return resumeName;
	}

	public void setResumeName(String resumeName) {
		this.resumeName = resumeName;
	}

	/**
	 * @return the finalMajor
	 */
	public String getFinalMajor() {
		return finalMajor;
	}

	/**
	 * @param finalMajor the finalMajor to set
	 */
	public void setFinalMajor(String finalMajor) {
		this.finalMajor = finalMajor;
	}

	/**
	 * @return the langSkill1
	 */
	public String getLangSkill1() {
		return langSkill1;
	}

	/**
	 * @param langSkill1 the langSkill1 to set
	 */
	public void setLangSkill1(String langSkill1) {
		this.langSkill1 = langSkill1;
	}

	/**
	 * @return the langSkill1Level
	 */
	public String getLangSkill1Level() {
		return langSkill1Level;
	}

	/**
	 * @param langSkill1Level the langSkill1Level to set
	 */
	public void setLangSkill1Level(String langSkill1Level) {
		this.langSkill1Level = langSkill1Level;
	}

	/**
	 * @return the langSkill2
	 */
	public String getLangSkill2() {
		return langSkill2;
	}

	/**
	 * @param langSkill2 the langSkill2 to set
	 */
	public void setLangSkill2(String langSkill2) {
		this.langSkill2 = langSkill2;
	}

	/**
	 * @return the langSkill2Level
	 */
	public String getLangSkill2Level() {
		return langSkill2Level;
	}

	/**
	 * @param langSkill2Level the langSkill2Level to set
	 */
	public void setLangSkill2Level(String langSkill2Level) {
		this.langSkill2Level = langSkill2Level;
	}

	/**
	 * @return the langSkill3
	 */
	public String getLangSkill3() {
		return langSkill3;
	}

	/**
	 * @param langSkill3 the langSkill3 to set
	 */
	public void setLangSkill3(String langSkill3) {
		this.langSkill3 = langSkill3;
	}

	/**
	 * @return the langSkill3Level
	 */
	public String getLangSkill3Level() {
		return langSkill3Level;
	}

	/**
	 * @param langSkill3Level the langSkill3Level to set
	 */
	public void setLangSkill3Level(String langSkill3Level) {
		this.langSkill3Level = langSkill3Level;
	}

	/**
	 * @return the essayTitle
	 */
	public String getEssayTitle() {
		return essayTitle;
	}

	/**
	 * @param essayTitle the essayTitle to set
	 */
	public void setEssayTitle(String essayTitle) {
		this.essayTitle = essayTitle;
	}

	/**
	 * @return the indField
	 */
	public String getIndField() {
		return indField;
	}

	/**
	 * @param indField the indField to set
	 */
	public void setIndField(String indField) {
		this.indField = indField;
	}

	/**
	 * @return the bizField
	 */
	public String getBizField() {
		return bizField;
	}

	/**
	 * @param bizField the bizField to set
	 */
	public void setBizField(String bizField) {
		this.bizField = bizField;
	}

	/**
	 * @return the funcField
	 */
	public String getFuncField() {
		return funcField;
	}

	/**
	 * @param funcField the funcField to set
	 */
	public void setFuncField(String funcField) {
		this.funcField = funcField;
	}

	/**
	 * @return the consultingMajor
	 */
	public String getConsultingMajor() {
		return consultingMajor;
	}

	/**
	 * @param consultingMajor the consultingMajor to set
	 */
	public void setConsultingMajor(String consultingMajor) {
		this.consultingMajor = consultingMajor;
	}

	/**
	 * @return the consultingMinor
	 */
	public String getConsultingMinor() {
		return consultingMinor;
	}

	/**
	 * @param consultingMinor the consultingMinor to set
	 */
	public void setConsultingMinor(String consultingMinor) {
		this.consultingMinor = consultingMinor;
	}

	/**
	 * @return the consultingDetail
	 */
	public String getConsultingDetail() {
		return consultingDetail;
	}

	/**
	 * @param consultingDetail the consultingDetail to set
	 */
	public void setConsultingDetail(String consultingDetail) {
		this.consultingDetail = consultingDetail;
	}

	/**
	 * @return the researchEassy
	 */
	public String getResearchEassy() {
		return researchEassy;
	}

	/**
	 * @param researchEassy the researchEassy to set
	 */
	public void setResearchEassy(String researchEassy) {
		this.researchEassy = researchEassy;
	}

	/**
	 * @return the publishedBook
	 */
	public String getPublishedBook() {
		return publishedBook;
	}

	/**
	 * @param publishedBook the publishedBook to set
	 */
	public void setPublishedBook(String publishedBook) {
		this.publishedBook = publishedBook;
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
	 * @return the createrId
	 */
	public String getCreaterId() {
		return createrId;
	}

	/**
	 * @param createrId the createrId to set
	 */
	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the modifierId
	 */
	public String getModifierId() {
		return modifierId;
	}

	/**
	 * @param modifierId the modifierId to set
	 */
	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the emplNo
	 */
	public String getEmplNo() {
		return emplNo;
	}

	/**
	 * @param remark the emplNo to set
	 */
	public void setEmplNo(String emplNo) {
		this.emplNo = emplNo;
	}

	/**
	 * @return the expertPoolCareerHst
	 */
	public List<ExpertPoolCareerHst> getExpertPoolCareerHst() {
		return expertPoolCareerHst;
	}

	/**
	 * @param expertPoolCareerHst the expertPoolCareerHst to set
	 */
	public void setExpertPoolCareerHst(List<ExpertPoolCareerHst> expertPoolCareerHst) {
		this.expertPoolCareerHst = expertPoolCareerHst;
	}

	/**
	 * @return the expertPoolSchoolHst
	 */
	public List<ExpertPoolSchoolHst> getExpertPoolSchoolHst() {
		return expertPoolSchoolHst;
	}

	/**
	 * @param expertPoolSchoolHst the expertPoolSchoolHst to set
	 */
	public void setExpertPoolSchoolHst(List<ExpertPoolSchoolHst> expertPoolSchoolHst) {
		this.expertPoolSchoolHst = expertPoolSchoolHst;
	}

	/**
	 * @return the expertPoolProjectHst
	 */
	public List<ExpertPoolProjectHst> getExpertPoolProjectHst() {
		return expertPoolProjectHst;
	}

	/**
	 * @param expertPoolProjectHst the expertPoolProjectHst to set
	 */
	public void setExpertPoolProjectHst(List<ExpertPoolProjectHst> expertPoolProjectHst) {
		this.expertPoolProjectHst = expertPoolProjectHst;
	}

	/**
	 * @return the gubun
	 */
	public String getGubun() {
		return gubun;
	}

	/**
	 * @param gubun the gubun to set
	 */
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @return the employ
	 */
	public String getEmploy() {
		return employ;
	}

	/**
	 * @param employ the employ to set
	 */
	public void setEmploy(String employ) {
		this.employ = employ;
	}

	/**
	 * @return the contractEmploy
	 */
	public String getContractEmploy() {
		return contractEmploy;
	}

	/**
	 * @param contractEmploy the contractEmploy to set
	 */
	public void setContractEmploy(String contractEmploy) {
		this.contractEmploy = contractEmploy;
	}

	/**
	 * @return the consultant
	 */
	public String getConsultant() {
		return consultant;
	}

	/**
	 * @param consultant the consultant to set
	 */
	public void setConsultant(String consultant) {
		this.consultant = consultant;
	}

	/**
	 * @return the industryExpert
	 */
	public String getIndustryExpert() {
		return industryExpert;
	}

	/**
	 * @param industryExpert the industryExpert to set
	 */
	public void setIndustryExpert(String industryExpert) {
		this.industryExpert = industryExpert;
	}

	/**
	 * @return the professor
	 */
	public String getProfessor() {
		return professor;
	}

	/**
	 * @param professor the professor to set
	 */
	public void setProfessor(String professor) {
		this.professor = professor;
	}

	/**
	 * @return the businessExpert
	 */
	public String getBusinessExpert() {
		return businessExpert;
	}

	/**
	 * @param businessExpert the businessExpert to set
	 */
	public void setBusinessExpert(String businessExpert) {
		this.businessExpert = businessExpert;
	}

	/**
	 * @return the etcEmploy
	 */
	public String getEtcEmploy() {
		return etcEmploy;
	}

	/**
	 * @param etcEmploy the etcEmploy to set
	 */
	public void setEtcEmploy(String etcEmploy) {
		this.etcEmploy = etcEmploy;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the projectMemberCount
	 */
	public String getProjectMemberCount() {
		return projectMemberCount;
	}

	/**
	 * @param projectMemberCount the projectMemberCount to set
	 */
	public void setProjectMemberCount(String projectMemberCount) {
		this.projectMemberCount = projectMemberCount;
	}

	/**
	 * @return the endRate
	 */
	public String getEndRate() {
		return endRate;
	}

	/**
	 * @param endRate the endRate to set
	 */
	public void setEndRate(String endRate) {
		this.endRate = endRate;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the extRole
	 */
	public String getExtRole() {
		return extRole;
	}

	/**
	 * @param extRole the extRole to set
	 */
	public void setExtRole(String extRole) {
		this.extRole = extRole;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the rid
	 */
	public String getRid() {
		return rid;
	}

	/**
	 * @param rid the rid to set
	 */
	public void setRid(String rid) {
		this.rid = rid;
	}

	/**
	 * @return the rate
	 */
	public String getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}

	/**
	 * @return the rank
	 */
	public String getRank() {
		return rank;
	}
	
	/**
	 * @param rate the rate to set
	 */
	public void setRank(String rank) {
		this.rank = rank;
	}
	
	/**
	 * @return the basicSalary
	 */
	public String getBasicSalary() {
		return basicSalary;
	}

	/**
	 * @param basicSalary the basicSalary to set
	 */
	public void setBasicSalary(String basicSalary) {
		this.basicSalary = basicSalary;
	}

	public String getAcctBeginDate() {
		return acctBeginDate;
	}

	public void setAcctBeginDate(String acctBeginDate) {
		this.acctBeginDate = acctBeginDate;
	}

	public String getAcctExpireDate() {
		return acctExpireDate;
	}

	public void setAcctExpireDate(String acctExpireDate) {
		this.acctExpireDate = acctExpireDate;
	}

	public String getBlockDownload() {
		return blockDownload;
	}

	public void setBlockDownload(String blockDownload) {
		this.blockDownload = blockDownload;
	}
	
	public String getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(String accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public List<ExpertPoolSpecialField> getExpertPoolSpecialFields() {
		return expertPoolSpecialFields;
	}

	public void setExpertPoolSpecialFields(List<ExpertPoolSpecialField> expertPoolSpecialFields) {
		this.expertPoolSpecialFields = expertPoolSpecialFields;
	}

	public String getSalaryMailingYN() {
		return salaryMailingYN;
	}

	public void setSalaryMailingYN(String salaryMailingYN) {
		this.salaryMailingYN = salaryMailingYN;
	}
	
	public String getMinMMAmt() {
		return minMMAmt;
	}

	public void setMinMMAmt(String minMMAmt) {
		this.minMMAmt = minMMAmt;
	}

	public String getMaxMMAmt() {
		return maxMMAmt;
	}

	public void setMaxMMAmt(String maxMMAmt) {
		this.maxMMAmt = maxMMAmt;
	}

	public String getExpertPoolSpecialFunctionNameDistinct() {
		String temp = "";
		String result = "";
		for (ExpertPoolSpecialField expertPoolSpecialField : this.expertPoolSpecialFields) {
			if (!temp.equals(expertPoolSpecialField.getSpecialFunctionName())) {
				result += expertPoolSpecialField.getSpecialFunctionName() + ", ";
				temp = expertPoolSpecialField.getSpecialFunctionName();
			}
		}
		return result;
	}

	public void setExpertPoolSpecialFunctionNameDistinct(String expertPoolSpecialFunctionNameDistinct) {
		this.expertPoolSpecialFunctionNameDistinct = expertPoolSpecialFunctionNameDistinct;
	}

	public static ExpertPool valueOf(ExpertPoolForm form) {
		ExpertPool expertPool = new ExpertPool();
		expertPool.setSsn(form.getSsn());
		expertPool.setUid(form.getUid());
		expertPool.setName(form.getName());
		expertPool.setChnName(form.getChnName());
		expertPool.setEngName(form.getEngName());		
		expertPool.setUserId(StringUtil.isNull(form.getIdentify(), "").equals("") ? null : form.getIdentify());
		expertPool.setPassword(StringUtil.isNull(form.getPassword(), "").equals("") ? null : form.getPassword());
		expertPool.setEnable(form.getEnable());
		expertPool.setGender(form.getGender());
		expertPool.setNationality(form.getNationality());
		expertPool.setTelNo(form.getTelNo());
		expertPool.setMobileNo(form.getMobileNo());
		expertPool.setZipCode(form.getZipCode());
		expertPool.setAddress1(form.getAddress1());
		expertPool.setAddress2(form.getAddress2());
		expertPool.setCompany(form.getCompany());
		expertPool.setCompanyId(form.getCompanyId());
		expertPool.setDept(form.getDept());
		expertPool.setJobClass(form.getJobClass());
		expertPool.setCompanyZipcode(form.getCompanyZipcode());
		expertPool.setCompanyAddress1(form.getCompanyAddress1());
		expertPool.setCompanyAddress2(form.getCompanyAddress2());
		expertPool.setCompanyTelNo(form.getCompanyTelNo());
		expertPool.setCompanyFaxNo(form.getCompanyFaxNo());
		expertPool.setEmail(form.getEmail());
		expertPool.setLangSkill1(form.getLangSkill1());
		expertPool.setLangSkill2(form.getLangSkill2());
		expertPool.setLangSkill3(form.getLangSkill3());
		expertPool.setLangSkill1Level(form.getLangSkill1Level());
		expertPool.setLangSkill2Level(form.getLangSkill2Level());
		expertPool.setLangSkill3Level(form.getLangSkill3Level());
		expertPool.setFinalMajor(form.getFinalMajor());
		expertPool.setEssayTitle(form.getEssayTitle());
		expertPool.setIndField(form.getIndField());
		expertPool.setBizField(form.getBizField());
		expertPool.setFuncField(form.getFuncField());
		expertPool.setConsultingMajor(form.getConsultingMajor());
		expertPool.setConsultingMinor(form.getConsultingMinor());
		expertPool.setConsultingDetail(form.getConsultingDetail());
		expertPool.setResearchEassy(form.getResearchEassy());
		expertPool.setPublishedBook(form.getPublishedBook());
		// expertPool.setModifiedDate(form.getModifiedDate());
		expertPool.setModifierId(form.getModifierId());
		expertPool.setResume(form.getResume());
		expertPool.setRemark(form.getRemark());
		expertPool.setRate(form.getRate());
		expertPool.setSalaryMailingYN(form.getSalaryMailingYN());

		List<ExpertPoolCareerHst> careerHstList = new ArrayList<ExpertPoolCareerHst>();
		if (form.getCareerSeq() != null) {
			for (int i = 0; i < form.getCareerSeq().length; i++) {
				ExpertPoolCareerHst careerHst = new ExpertPoolCareerHst();
				careerHst.setSsn(form.getSsn());
				careerHst.setSeqNo((i + 1) + "");
				careerHst.setTerm(form.getCareerTerm()[i]);
				careerHst.setCompanyName(form.getCareercompanyName()[i]);
				careerHst.setWorkingDept(form.getCareerWorkingDept()[i]);
				careerHst.setCompanyPosition(form.getCareerPosition()[i]);
				careerHst.setWorkingDetail(form.getCareerWorkingDetail()[i]);
				careerHst.setRemark(form.getCareerRemark()[i]);
				careerHstList.add(i, careerHst);
			}
		}
		expertPool.setExpertPoolCareerHst(careerHstList);

		List<ExpertPoolSchoolHst> schoolHistList = new ArrayList<ExpertPoolSchoolHst>();
		if (form.getSchoolSeq() != null) {
			for (int i = 0; i < form.getSchoolSeq().length; i++) {
				ExpertPoolSchoolHst expertPoolSchoolHst = new ExpertPoolSchoolHst();
				expertPoolSchoolHst.setSsn(form.getSsn());
				expertPoolSchoolHst.setSeq((i + 1) + "");
				expertPoolSchoolHst.setTerm(form.getSchoolTerm()[i]);
				expertPoolSchoolHst.setSchoolName(form.getSchoolName()[i]);
				expertPoolSchoolHst.setMajor(form.getSchoolMajor()[i]);
				expertPoolSchoolHst.setGraduationState(form.getSchoolGraduationState()[i]);
				expertPoolSchoolHst.setGpa(form.getSchoolGpa()[i]);
				expertPoolSchoolHst.setLocation(form.getSchoolLocation()[i]);
				expertPoolSchoolHst.setRemark(form.getSchoolRemark()[i]);
				schoolHistList.add(i, expertPoolSchoolHst);
			}
		}
		expertPool.setExpertPoolSchoolHst(schoolHistList);

		return expertPool;
	}

}