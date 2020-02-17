package net.kh.domain;

import java.sql.Timestamp;

public class BoardCommentVo {
	private int reply_num;
	private int board_num;
	private String replyer;
	private Timestamp reply_date;
	private String reply_text;
	
	public BoardCommentVo() {
		super();
	}

	public BoardCommentVo(int reply_num, int board_num, String replyer, Timestamp reply_date, String reply_text) {
		super();
		this.reply_num = reply_num;
		this.board_num = board_num;
		this.replyer = replyer;
		this.reply_date = reply_date;
		this.reply_text = reply_text;
	}

	public int getReply_num() {
		return reply_num;
	}

	public void setReply_num(int reply_num) {
		this.reply_num = reply_num;
	}

	public int getBoard_num() {
		return board_num;
	}

	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}

	public String getReplyer() {
		return replyer;
	}

	public void setReplyer(String replyer) {
		this.replyer = replyer;
	}

	public Timestamp getReply_date() {
		return reply_date;
	}

	public void setReply_date(Timestamp reply_date) {
		this.reply_date = reply_date;
	}

	public String getReply_text() {
		return reply_text;
	}

	public void setReply_text(String reply_text) {
		this.reply_text = reply_text;
	}

	@Override
	public String toString() {
		return "BoardCommentVo [reply_num=" + reply_num + ", board_num=" + board_num + ", replyer=" + replyer
				+ ", reply_date=" + reply_date + ", reply_text=" + reply_text + "]";
	}
	
}
