package net.kh.domain;

import java.sql.Timestamp;

public class MemoVo {
	private int memo_num;
	private String memo_text;
	private String memo_sender;
	private String memo_receiver;
	private Timestamp memo_send_date;
	private Timestamp memo_open_date;
	
	public MemoVo() {
		super();
	}

	public MemoVo(int memo_num, String memo_text, String memo_sender, String memo_receiver, Timestamp memo_send_date,
			Timestamp memo_open_date) {
		super();
		this.memo_num = memo_num;
		this.memo_text = memo_text;
		this.memo_sender = memo_sender;
		this.memo_receiver = memo_receiver;
		this.memo_send_date = memo_send_date;
		this.memo_open_date = memo_open_date;
	}

	public int getMemo_num() {
		return memo_num;
	}

	public void setMemo_num(int memo_num) {
		this.memo_num = memo_num;
	}

	public String getMemo_text() {
		return memo_text;
	}

	public void setMemo_text(String memo_text) {
		this.memo_text = memo_text;
	}

	public String getMemo_sender() {
		return memo_sender;
	}

	public void setMemo_sender(String memo_sender) {
		this.memo_sender = memo_sender;
	}

	public String getMemo_receiver() {
		return memo_receiver;
	}

	public void setMemo_receiver(String memo_receiver) {
		this.memo_receiver = memo_receiver;
	}

	public Timestamp getMemo_send_date() {
		return memo_send_date;
	}

	public void setMemo_send_date(Timestamp memo_send_date) {
		this.memo_send_date = memo_send_date;
	}

	public Timestamp getMemo_open_date() {
		return memo_open_date;
	}

	public void setMemo_open_date(Timestamp memo_open_date) {
		this.memo_open_date = memo_open_date;
	}

	@Override
	public String toString() {
		return "MemoVo [memo_num=" + memo_num + ", memo_text=" + memo_text + ", memo_sender=" + memo_sender
				+ ", memo_receiver=" + memo_receiver + ", memo_send_date=" + memo_send_date + ", memo_open_date="
				+ memo_open_date + "]";
	}
	
	
}
