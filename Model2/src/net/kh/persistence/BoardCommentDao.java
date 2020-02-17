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

import net.kh.domain.BoardCommentVo;
import net.kh.domain.BoardVo;
import oracle.net.aso.p;

public class BoardCommentDao {
	
	private static BoardCommentDao instance;
	
	private BoardCommentDao() {/*singleton*/}
	
	public static BoardCommentDao getInstance () {
		if (instance == null) {
			instance = new BoardCommentDao();
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
	
	
	// 댓글보이기
	public List<BoardCommentVo> getCommentList(int board_num){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select * from tbl_reply" 	+ 
					"		where board_num = ?" 	+ 
					"		order by reply_num";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			
			List<BoardCommentVo> list = new ArrayList<BoardCommentVo>();
			while(rs.next()) {
				BoardCommentVo vo = new BoardCommentVo();
				vo.setReply_num	(rs.getInt("reply_num"));
				vo.setBoard_num	(rs.getInt("board_num"));
				vo.setReplyer	(rs.getString("replyer"));
				vo.setReply_date(rs.getTimestamp("reply_date"));
				vo.setReply_text(rs.getString("reply_text"));
				
				list.add(vo);
			}
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return null;
	}
	
	// 댓글추가
	public boolean insertComment(BoardCommentVo commentVo) {
		System.out.println("commentVo: " + commentVo);
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		
		try {
			conn = getConnection();
			
			conn.setAutoCommit(false);
			
			String sql = "insert into tbl_reply"
					+ "		(reply_num, board_num, replyer, reply_date, reply_text)"
					+ "	values (seq_reply_num.nextval, ?, ?, sysdate, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt	(1, commentVo.getBoard_num());
			pstmt.setString	(2, commentVo.getReplyer());
			pstmt.setString	(3, commentVo.getReply_text());
			pstmt.executeUpdate();
			
			String sql2 = "update tbl_board set"
					+ "		reply_count = reply_count + 1 "
					+ "	where board_num = ?";
			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setInt(1, commentVo.getBoard_num());
			pstmt2.executeUpdate();
			
			conn.commit();
			return true;
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);	//  원위치~시켜야지~
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeAll(null, pstmt2, null);
			closeAll(conn, pstmt, null);
		}
		return false;
	} // 댓글추가
	
	
	
	// 댓글 삭제
	public boolean deleteComment(Map<String, Object> paramMap) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		
		try {
			
			conn = getConnection();
			
			conn.setAutoCommit(false);
			
			String sql = "delete from tbl_reply "
					+ "		where reply_num = ? "
					+ "		and replyer = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,(int)paramMap.get("reply_num"));
			pstmt.setString(2, (String)paramMap.get("mem_id"));
			int count = pstmt.executeUpdate();
			
			
			
			String sql2 = "update tbl_board set"
					+ "		reply_count = reply_count - 1 "
					+ "	where board_num = ?";
			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setInt(1, (int)paramMap.get("board_num"));
			pstmt2.executeUpdate();
			
			conn.commit();
			if(count > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} 
		finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeAll(null, pstmt2, null);
			closeAll(conn, pstmt, null);
		}
		
		return false;
	}// 댓글 삭제
	
	
	// 댓글 수정하기
	public boolean updateComment(Map<String, Object> paramMap) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "update tbl_reply set" 
					+ "    		reply_text = ?,"
					+ "   	 	reply_date = sysdate"
					+ "		where reply_num = ?"
					+ "		and replyer = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString	(1, (String) paramMap.get("reply_text"));
			pstmt.setInt	(2, (int)paramMap.get("reply_num"));
			pstmt.setString	(3, (String)paramMap.get("mem_id"));
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
	} // 댓글 수정하기
	
	
	
	
	
}
