package net.kh.domain;

public class LoginDto {
	private String mem_id;
	private String mem_pass;
	
	public LoginDto() {
		super();
	}

	public LoginDto(String mem_id, String mem_pass) {
		super();
		this.mem_id = mem_id;
		this.mem_pass = mem_pass;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getMem_pass() {
		return mem_pass;
	}

	public void setMem_pass(String mem_pass) {
		this.mem_pass = mem_pass;
	}

	@Override
	public String toString() {
		return "LoginDto [mem_id=" + mem_id + ", mem_pass=" + mem_pass + "]";
	}
	
}
