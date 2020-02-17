package net.kh.domain;

import java.sql.Timestamp;

public class PointVo {
	private int point_num;
	private int m_point;
	private String mem_id;
	private Timestamp give_date;
	private String point_code;
	private String point_desc;	// 화면에서 보일때
	
	public PointVo() {
		super();
	}

	public PointVo(int point_num, int m_point, String mem_id, Timestamp give_date, String point_code, String point_desc) {
		super();
		this.point_num = point_num;
		this.m_point = m_point;
		this.mem_id = mem_id;
		this.give_date = give_date;
		this.point_code = point_code;
		this.point_desc = point_desc;
	}

	public int getPoint_num() {
		return point_num;
	}

	public void setPoint_num(int point_num) {
		this.point_num = point_num;
	}

	public int getM_point() {
		return m_point;
	}

	public void setM_point(int m_point) {
		this.m_point = m_point;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public Timestamp getGive_date() {
		return give_date;
	}

	public void setGive_date(Timestamp give_date) {
		this.give_date = give_date;
	}

	public String getPoint_code() {
		return point_code;
	}

	public void setPoint_code(String point_code) {
		this.point_code = point_code;
	}

	public String getPoint_desc() {
		return point_desc;
	}

	public void setPoint_desc(String point_desc) {
		this.point_desc = point_desc;
	}

	@Override
	public String toString() {
		return "PointVo [point_num=" + point_num + ", m_point=" + m_point + ", mem_id=" + mem_id + ", give_date="
				+ give_date + ", point_code=" + point_code + ", point_desc=" + point_desc + "]";
	}
	
}
