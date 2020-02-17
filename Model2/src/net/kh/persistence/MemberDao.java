package net.kh.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.kh.domain.LoginDto;
import net.kh.domain.MemberVo;
import net.kh.domain.PointVo;

public class MemberDao {
	
	private static MemberDao instance;
	
	private MemberDao() {/*singleton*/}
	
	public static MemberDao getInstance () {
		if (instance == null) {
			instance = new MemberDao();
		}
		return instance;
		
	}
	
	
	private Connection getConnection() {
		
		try {
			// javax.naming.Context, javax.naming.InitialContext
			Context context = new InitialContext();
			
			// javax.sql.DataSource
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/oraclexe");
			//-> 커넥션  풀 참조 얻기
			
			Connection conn = ds.getConnection();
			//-> 커넥션 풀에서 이미 생성된 커넥션 객체 얻기
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		if (conn != null) 	try { conn.close(); }	catch (Exception e) { }
		if (pstmt != null) 	try { pstmt.close(); }	catch (Exception e) { }
		if (rs != null) 	try { rs.close(); }		catch (Exception e) { }
	}
	
	
	// 회원 추가
	public boolean insertMember(MemberVo memberVo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "insert into tbl_member(mem_id, mem_pass, mem_name, mem_pic)" + 
					"		values (?, ?, ?, ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, memberVo.getMem_id());
			pstmt.setString(2, memberVo.getMem_pass());
			pstmt.setString(3, memberVo.getMem_name());
			pstmt.setString(4, memberVo.getMem_pic());
			int count = pstmt.executeUpdate();
			if (count > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, null);
		}
		return false;
	} // 회원 추가
	
	
	// 중복처리
	public boolean checkDupId(String mem_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select count(*) from tbl_member"
					+ "		where mem_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				if(count > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return false;
	} // 중복처리
	
	
	// 사용자 체크
	public boolean checkMember(LoginDto loginDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select count(*) from tbl_member" + 
					"		where mem_id = ? and mem_pass = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginDto.getMem_id());
			pstmt.setString(2, loginDto.getMem_pass());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				if (count > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return false;
	}// 사용자 체크
	
	
	// 로그인한 사용자의 포인트 리스트
	public List<PointVo> getMemberPointList(String mem_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select p.point_num, p.m_point, p.give_date, c.point_desc " 
					+ "		from tbl_point p, tbl_point_code c"
					+ "		where p.mem_id = ?"
					+ "		order by p.point_num desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			
			List<PointVo> pointList = new ArrayList<>();
			
			while(rs.next()) {
				int m_point = rs.getInt("m_point");
				Timestamp give_date = rs.getTimestamp("give_date");
				String point_desc = rs.getString("point_desc");
			
				PointVo pointVo = new PointVo();
				pointVo.setMem_id(mem_id);
				pointVo.setM_point(m_point);
				pointVo.setGive_date(give_date);
				pointVo.setPoint_desc(point_desc);
				
				
				pointList.add(pointVo);
			}
			return pointList;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		
		return null ;
	}
	// 로그인한 사용자의 포인트 얻기
	
	
	
	// 로그인한 사용자의 포인트 얻기
	public int getMemberPoint(String mem_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select mem_point from tbl_member"
					+ "		where mem_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			rs.next();
			int mem_point = rs.getInt(1);
			
			return mem_point;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		
		return 0;
	}
	// 로그인한 사용자의 포인트 얻기

}
