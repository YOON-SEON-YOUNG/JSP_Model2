package net.kh.domain;

public class MemberVo {
	private String mem_id;
	private String mem_pass;
	private String mem_name;
	private int mem_point;
	private String mem_pic;
	
	public MemberVo() {
		super();
	}

	

	public MemberVo(String mem_id, String mem_pass, String mem_name, int mem_point, String mem_pic) {
		super();
		this.mem_id = mem_id;
		this.mem_pass = mem_pass;
		this.mem_name = mem_name;
		this.mem_point = mem_point;
		this.mem_pic = mem_pic;
	}



	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		char ch = mem_id.charAt(0);
		if ('a' <= ch && ch <= 'z' &&
				mem_id.length() <= 20) {
			this.mem_id = mem_id;
		}
	}

	public String getMem_pass() {
		return mem_pass;
	}

	public void setMem_pass(String mem_pass) {
		this.mem_pass = mem_pass;
	}

	public String getMem_name() {
		return mem_name;
	}

	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}

	public int getMem_point() {
		return mem_point;
	}

	public void setMem_point(int mem_point) {
		this.mem_point = mem_point;
	}

	

	public String getMem_pic() {
		return mem_pic;
	}



	public void setMem_pic(String mem_pic) {
		this.mem_pic = mem_pic;
	}



	@Override
	public String toString() {
		return "MemberVo [mem_id=" + mem_id + ", mem_pass=" + mem_pass + ", mem_name=" + mem_name + ", mem_point="
				+ mem_point + ", mem_pic=" + mem_pic + "]";
	}
	
}
