package kr.co.kmac.pms.board.data;

public class BoardChoiceData extends BoardData {

	private String idx;
	private String seq;
	private String writer_id;
	private String writer_name;
	private String choice_type;
	private String write_date;
	private String remote_ip;

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getWriter_id() {
		return writer_id;
	}

	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}

	public String getWriter_name() {
		return writer_name;
	}

	public void setWriter_name(String writer_name) {
		this.writer_name = writer_name;
	}

	public String getChoice_type() {
		return choice_type;
	}

	public void setChoice_type(String choice_type) {
		this.choice_type = choice_type;
	}

	public String getWrite_date() {
		return write_date;
	}

	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}

	public String getRemote_ip() {
		return remote_ip;
	}

	public void setRemote_ip(String remote_ip) {
		this.remote_ip = remote_ip;
	}

}
