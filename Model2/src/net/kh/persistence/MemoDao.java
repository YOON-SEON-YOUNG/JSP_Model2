package net.kh.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.kh.domain.MemoVo;
import net.kh.domain.PointVo;

public class MemoDao {
private static MemoDao instance;
	
	private MemoDao() {/*singleton*/}
	
	public static MemoDao getInstance () {
		if (instance == null) {
			instance = new MemoDao();
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
	
	// 읽지 않은 쪽지 갯수 구하기
	public int countNotRead(String mem_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select count(*) from tbl_memo"
					+ "	where memo_receiver = ?"
					+ "	and memo_open_date is null";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			return count;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return 0;
	}
	
	
	// 쪽지 목록 들고오기
	public List<MemoVo> getMemoList(String mem_id){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select * from tbl_memo"
					+ "		where memo_receiver = ?"
					+ "		order by memo_send_date desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			
			List<MemoVo> memoList = new ArrayList<>();
			while(rs.next()) {
				int			memo_num 		= rs.getInt("memo_num");
				String 		memo_text 		= rs.getString("memo_text");
				String 		memo_sender 	= rs.getString("memo_sender");
				String 		memo_receiver 	= rs.getString("memo_receiver");
				Timestamp 	memo_send_date 	= rs.getTimestamp("memo_send_date");
				Timestamp 	memo_open_date 	= rs.getTimestamp("memo_open_date");

				MemoVo memoVo = new MemoVo();
				memoVo.setMemo_num		(memo_num);
				memoVo.setMemo_text		(memo_text);
				memoVo.setMemo_sender	(memo_sender);
				memoVo.setMemo_receiver	(memo_receiver);
				memoVo.setMemo_send_date(memo_send_date);
				memoVo.setMemo_open_date(memo_open_date);
				
				memoList.add(memoVo);
			}
			return memoList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return null;
	} // 쪽지 목록 들고오기
	
	
	
	// 쪽지 보내기
	public void sendMemo(PointVo pointVo, MemoVo memoVo) {
		
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		
		try {
			conn = getConnection();
			
			conn.setAutoCommit(false);
			
			String sql1 = "insert into tbl_point"
					+ "				(point_num, m_point, mem_id, point_code)"
					+ "		 values (seq_point_num.nextval, ?, ?, ?)";
			
			String sql2 = "insert into tbl_memo"
					+ "				(memo_num, memo_text, memo_sender, memo_receiver)"
					+ "		 values (seq_memo_num.nextval, ?, ?, ?)";
			
			String sql3 = "update tbl_member set"
					+ "		 	mem_point = mem_point + ?"
					+ "		 where mem_id = ?";
			
			pstmt1 = conn.prepareStatement(sql1);
			pstmt2 = conn.prepareStatement(sql2);
			pstmt3 = conn.prepareStatement(sql3);
			
			pstmt1.setInt	(1, pointVo.getM_point());
			pstmt1.setString(2, pointVo.getMem_id());
			pstmt1.setString(3, pointVo.getPoint_code());
			
			pstmt2.setString(1, memoVo.getMemo_text());
			pstmt2.setString(2, memoVo.getMemo_sender());
			pstmt2.setString(3, memoVo.getMemo_receiver());
			
			pstmt3.setInt	(1, pointVo.getM_point());
			pstmt3.setString(2, pointVo.getMem_id());
			
			pstmt1.executeUpdate();
			pstmt2.executeUpdate();
			pstmt3.executeUpdate();
			
			conn.commit();
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeAll(null, pstmt3, null);
			closeAll(null, pstmt2, null);
			closeAll(conn, pstmt1, null);
		}
		/*
		 insert into tbl_point(point_num, m_point, mem_id, point_code)
		 values (seq_point_num.nextval, ?, ?, ?);
		 
		 insert into tbl_memo(memo_num, memo_text, memo_sender, memo_receiver)
		 values (seq_memo_num.nextval, ?, ?, ?);
		 
		 update tbl_member set
		 	mem_point = mem_point + ?
		 where mem_id = ?;
		 */
	} // 쪽지 보내기
	
	
	// 쪽지 읽기
	public String readMemo(Map<String, Object> paramMap) {
		// 메모 테이블에서 읽은 시각을 변경(null -> sysdate)
		// 회원 테이블에서 읽은 사용자 포인트 변경(mem_point + 5)
		
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			conn.setAutoCommit(false);
			
			// 메모 테이블 날짜 변경
			String sql1 = "update tbl_memo set" 
					+ "   	 	memo_open_date = sysdate"
					+ "		where memo_num = ?"
					+ "		and memo_receiver = ?";
			
			// 회원 테이블 포인트 변경
			String sql2 = "update tbl_member set" 
					+ "    		mem_point = mem_point + ?"
					+ "		where mem_id = ?";
			
			// 포인트 테이블 포인트 내역 추가
			String sql3 = "insert into tbl_point"
					+ "				(point_num, m_point, mem_id, point_code)"
					+ "		values (seq_point_num.nextval, ?, ?, ?)";
		
			// 해당 쪽지의 읽은 날짜 가져오기
			String sql4 = "select memo_open_date" 
					+ "		from tbl_memo"
					+ "		where memo_num = ?";
			
			pstmt1 = conn.prepareStatement(sql1);
			pstmt2 = conn.prepareStatement(sql2);
			pstmt3 = conn.prepareStatement(sql3);
			pstmt4 = conn.prepareStatement(sql4);
			
			pstmt1.setInt		(1, (int) paramMap.get("memo_num"));
			pstmt1.setString	(2, (String) paramMap.get("mem_id"));
			
			pstmt2.setInt		(1, (int) paramMap.get("point"));
			pstmt2.setString	(2, (String) paramMap.get("mem_id"));
			
			pstmt3.setInt		(1, (int) paramMap.get("point"));
			pstmt3.setString	(2, (String) paramMap.get("mem_id"));
			pstmt3.setString	(3, (String) paramMap.get("point_code"));
			
			pstmt4.setInt(1, (int) paramMap.get("memo_num"));
			
			pstmt1.executeUpdate();
			pstmt2.executeUpdate();
			pstmt3.executeUpdate();
			conn.commit();
			
			rs = pstmt4.executeQuery();
			rs.next();
			String memo_open_date = rs.getTimestamp(1).toString();
			
			return memo_open_date;
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeAll(null, pstmt4, null);
			closeAll(null, pstmt3, null);
			closeAll(null, pstmt2, null);
			closeAll(conn, pstmt1, rs);
		}
		return null;
	} // 쪽지 읽기
	
	
	
	// 쪽지 삭제
	public boolean deleteMemo(Map<String, Object> paramMap) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "delete from tbl_memo" 
					+ "		where memo_num in ("
					+ 				(String)paramMap.get("memo_nums") + ")" 
					+ "		and memo_receiver = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String) paramMap.get("mem_id"));
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
	}
	// 쪽지 삭제
	
}
