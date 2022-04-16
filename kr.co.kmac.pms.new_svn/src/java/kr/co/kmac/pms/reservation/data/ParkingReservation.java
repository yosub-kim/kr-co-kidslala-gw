package kr.co.kmac.pms.reservation.data;

public class ParkingReservation {
	private String idx;
	private String pdate;
	private String startTime;
	private String endTime;
	private String Rtime;
	private String addDay;
	private String pname;
	private String ptel;
	private String pcarNum;
	private String pmemo;
	private String regdate;
	private String checkResult;
	private String company;
	private String dept;
	private String deptName;
	private String writer;
	private String writerName;
	private String pClass;

	private String confirmCnt;
	private String noConfirmCnt;
	private String dateStr;
	private String weekdayStr;
	private String dt;

	/**
	 * @return the idx
	 */
	public String getIdx() {
		return idx;
	}

	/**
	 * @param idx the idx to set
	 */
	public void setIdx(String idx) {
		this.idx = idx;
	}

	/**
	 * @return the pdate
	 */
	public String getPdate() {
		return pdate;
	}

	public String getPdateStr() {
		if (this.pdate != null && this.pdate.length() == 8)
			return pdate.substring(0, 4) + "-" + pdate.substring(4, 6) + "-" + pdate.substring(6, 8);
		return pdate;
	}

	/**
	 * @param pdate the pdate to set
	 */
	public void setPdate(String pdate) {
		this.pdate = pdate;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the rtime
	 */
	public String getRtime() {
		return Rtime;
	}

	/**
	 * @param rtime the rtime to set
	 */
	public void setRtime(String rtime) {
		Rtime = rtime;
	}

	/**
	 * @return the addDay
	 */
	public String getAddDay() {
		return addDay;
	}

	/**
	 * @param addDay the addDay to set
	 */
	public void setAddDay(String addDay) {
		this.addDay = addDay;
	}

	/**
	 * @return the pname
	 */
	public String getPname() {
		return pname;
	}

	/**
	 * @param pname the pname to set
	 */
	public void setPname(String pname) {
		this.pname = pname;
	}

	/**
	 * @return the ptel
	 */
	public String getPtel() {
		return ptel;
	}

	/**
	 * @param ptel the ptel to set
	 */
	public void setPtel(String ptel) {
		this.ptel = ptel;
	}

	/**
	 * @return the pcarNum
	 */
	public String getPcarNum() {
		return pcarNum;
	}

	/**
	 * @param pcarNum the pcarNum to set
	 */
	public void setPcarNum(String pcarNum) {
		this.pcarNum = pcarNum;
	}

	/**
	 * @return the pmemo
	 */
	public String getPmemo() {
		return pmemo;
	}

	/**
	 * @param pmemo the pmemo to set
	 */
	public void setPmemo(String pmemo) {
		this.pmemo = pmemo;
	}

	/**
	 * @return the regdate
	 */
	public String getRegdate() {
		return regdate;
	}

	/**
	 * @param regdate the regdate to set
	 */
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	/**
	 * @return the checkResult
	 */
	public String getCheckResult() {
		return checkResult;
	}

	/**
	 * @param checkResult the checkResult to set
	 */
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
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

	/**
	 * @return the writer
	 */
	public String getWriter() {
		return writer;
	}

	/**
	 * @param writer the writer to set
	 */
	public void setWriter(String writer) {
		this.writer = writer;
	}

	/**
	 * @return the pClass
	 */
	public String getpClass() {
		return pClass;
	}

	/**
	 * @param pClass the pClass to set
	 */
	public void setpClass(String pClass) {
		this.pClass = pClass;
	}

	public String getConfirmCnt() {
		return confirmCnt;
	}

	public void setConfirmCnt(String confirmCnt) {
		this.confirmCnt = confirmCnt;
	}

	public String getNoConfirmCnt() {
		return noConfirmCnt;
	}

	public void setNoConfirmCnt(String noConfirmCnt) {
		this.noConfirmCnt = noConfirmCnt;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getWeekdayStr() {
		return weekdayStr;
	}

	public void setWeekdayStr(String weekdayStr) {
		this.weekdayStr = weekdayStr;
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getWriterName() {
		return writerName;
	}

	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}

}
