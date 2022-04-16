package kr.co.kmac.pms.reservation.data;

public class Reservation {

	private String no;
	private String resvCode;
	private String empno;
	private String stime;
	private String etime;
	private int stimeInt;
	private int etimeInt;
	private String useTeam;
	private String usePer;
	private String useLoc;
	private String usePurpose;
	private String timeVal;

	/**
	 * @return the no
	 */
	public String getNo() {
		return no;
	}

	/**
	 * @param no the no to set
	 */
	public void setNo(String no) {
		this.no = no;
	}

	/**
	 * @return the resvCode
	 */
	public String getResvCode() {
		return resvCode;
	}

	/**
	 * @param resvCode the resvCode to set
	 */
	public void setResvCode(String resvCode) {
		this.resvCode = resvCode;
	}

	/**
	 * @return the empno
	 */
	public String getEmpno() {
		return empno;
	}

	/**
	 * @param empno the empno to set
	 */
	public void setEmpno(String empno) {
		this.empno = empno;
	}

	/**
	 * @return the stime
	 */
	public String getStime() {
		return stime;
	}

	/**
	 * @param stime the stime to set
	 */
	public void setStime(String stime) {
		this.stime = stime;
	}

	/**
	 * @return the etime
	 */
	public String getEtime() {
		return etime;
	}

	/**
	 * @param etime the etime to set
	 */
	public void setEtime(String etime) {
		this.etime = etime;
	}

	/**
	 * @return the useTeam
	 */
	public String getUseTeam() {
		return useTeam;
	}

	/**
	 * @param useTeam the useTeam to set
	 */
	public void setUseTeam(String useTeam) {
		this.useTeam = useTeam;
	}

	/**
	 * @return the usePer
	 */
	public String getUsePer() {
		return usePer;
	}

	/**
	 * @param usePer the usePer to set
	 */
	public void setUsePer(String usePer) {
		this.usePer = usePer;
	}

	/**
	 * @return the useLoc
	 */
	public String getUseLoc() {
		return useLoc;
	}

	/**
	 * @param useLoc the useLoc to set
	 */
	public void setUseLoc(String useLoc) {
		this.useLoc = useLoc;
	}

	/**
	 * @return the usePurpose
	 */
	public String getUsePurpose() {
		return usePurpose;
	}

	/**
	 * @param usePurpose the usePurpose to set
	 */
	public void setUsePurpose(String usePurpose) {
		this.usePurpose = usePurpose;
	}

	public int getStimeInt() {
		return Integer.parseInt(stime.substring(8));
	}

	public void setStimeInt(int stimeInt) {
		this.stimeInt = stimeInt;
	}

	public int getEtimeInt() {
		return Integer.parseInt(etime.substring(8));
	}

	public void setEtimeInt(int etimeInt) {
		this.etimeInt = etimeInt;
	}

	public String getTimeVal() {
		return timeVal;
	}

	public void setTimeVal(String timeVal) {
		this.timeVal = timeVal;
	}

}
