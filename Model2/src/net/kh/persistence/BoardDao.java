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

public class BoardDao {
	
	private static BoardDao instance;
	
	private BoardDao() {/*singleton*/}
	
	public static BoardDao getInstance () {
		if (instance == null) {
			instance = new BoardDao();
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
	
	
	// 글쓰기
	public void write(BoardVo boardVo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "insert into tbl_board(board_num, board_subject, board_content, board_writer," + 
					"                    board_ip, board_ref, board_filename)" + 
					"		values (seq_board_num.nextval, ?, ?, ?," + 
					"         ?, seq_board_num.nextval, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardVo.getBoard_subject());
			pstmt.setString(2, boardVo.getBoard_content());
			pstmt.setString(3, boardVo.getBoard_writer());
			pstmt.setString(4, boardVo.getBoard_ip());
			pstmt.setString(5, boardVo.getBoard_filename());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, null);
		}
	}	//글쓰기
	
	
	// 목록 가져오기
	public List<BoardVo> getList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "select * from tbl_board"
					+ "		order by board_num desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			List<BoardVo> list = new ArrayList<>();
			while(rs.next()) {
				int 	board_num = rs.getInt("board_num");
				String 	board_subject 	 = rs.getString("board_subject");
				String 	board_content 	 = rs.getString("board_content");
				String 	board_writer 	 = rs.getString("board_writer");
				String	board_ip 		 = rs.getString("board_ip");
				int 	board_read_count = rs.getInt("board_read_count");
				int 	board_ref 		 = rs.getInt("board_ref");
				int 	board_re_step 	 = rs.getInt("board_re_step");
				int 	board_re_level 	 = rs.getInt("board_re_level");
				Timestamp board_reg_date = rs.getTimestamp("board_reg_date");
				String 	board_filename 	 = rs.getString("board_filename");
				int 	reply_count 	 = rs.getInt("reply_count");
				
				BoardVo boardVo = new BoardVo();
				boardVo.setBoard_num		(board_num);
				boardVo.setBoard_subject	(board_subject);
				boardVo.setBoard_content	(board_content);
				boardVo.setBoard_writer		(board_writer);
				boardVo.setBoard_ip			(board_ip);
				boardVo.setBoard_read_count	(board_read_count);
				boardVo.setBoard_ref		(board_ref);
				boardVo.setBoard_re_step	(board_re_step);
				boardVo.setBoard_re_level	(board_re_level);
				boardVo.setBoard_reg_date	(board_reg_date);
				boardVo.setBoard_filename	(board_filename);
				boardVo.setReply_count		(reply_count);
				
				list.add(boardVo);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return null;
	}	// 목록 가져오기
	
	
	
	
	// 상세보기
	public BoardVo getContentByNum(int board_num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select * from tbl_board"
					+ "		where board_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String 	board_subject 	 = rs.getString("board_subject");
				String 	board_content 	 = rs.getString("board_content");
				String	board_writer 	 = rs.getString("board_writer");
				String 	board_ip 		 = rs.getString("board_ip");
				int 	board_read_count = rs.getInt("board_read_count");
				int 	board_ref 		 = rs.getInt("board_ref");
				int 	board_re_step 	 = rs.getInt("board_re_step");
				int 	board_re_level 	 = rs.getInt("board_re_level");
				Timestamp board_reg_date = rs.getTimestamp("board_reg_date");
				String	board_filename	 = rs.getString("board_filename");
				int 	like_count		 = rs.getInt("like_count");
				
				BoardVo boardVo = new BoardVo();
				boardVo.setBoard_num		(board_num);
				boardVo.setBoard_subject	(board_subject);
				boardVo.setBoard_content	(board_content);
				boardVo.setBoard_writer		(board_writer);
				boardVo.setBoard_ip			(board_ip);
				boardVo.setBoard_read_count	(board_read_count);
				boardVo.setBoard_ref		(board_ref);
				boardVo.setBoard_re_step	(board_re_step);
				boardVo.setBoard_re_level	(board_re_level);
				boardVo.setBoard_reg_date	(board_reg_date);
				boardVo.setBoard_filename	(board_filename);
				boardVo.setLike_count		(like_count);
				
				return boardVo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		
		return null;
	} // 상세보기
	
	
	// 수정하기
	public void update(BoardVo boardVo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "update tbl_board set" 	+ 
					"   	board_writer = ?," 		+ 
					"    	board_subject = ?," 	+ 
					"    	board_content = ?";
			if (boardVo.getBoard_filename() != null &&
					!boardVo.getBoard_filename().equals("")) {
				sql +=	"    	, board_filename = ?";
			}
			sql +=	"	where board_num = ?";
				
			pstmt = conn.prepareStatement(sql);
			int i = 0;
			pstmt.setString(++i, boardVo.getBoard_writer());
			pstmt.setString(++i, boardVo.getBoard_subject());
			pstmt.setString(++i, boardVo.getBoard_content());
			if (boardVo.getBoard_filename() != null &&
					!boardVo.getBoard_filename().equals("")) {
				pstmt.setString	(++i, boardVo.getBoard_filename());
			}
			pstmt.setInt(++i, boardVo.getBoard_num());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, null);
		}
	} // 수정하기
	
	
	// 이미지 삭제
	public String getFilename(int board_num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select board_filename from tbl_board"
					+ "   where board_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String board_filename = rs.getString("board_filename");
				return board_filename;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return null;
	} // 이미지 삭제
	
	
	// 데이터 삭제
	public boolean delete(int board_num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "delete from tbl_board"  
					+ "		where board_num = ?";
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt	(1, board_num);
			
			int count = pstmt.executeUpdate();
			if(count > 0) {
				return true;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, null);
		}
		return false;
	} // 데이터 삭제
	
	
	// 답글달기
	public void reply(BoardVo boardVo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "insert into tbl_board(board_num, board_subject, board_content, board_writer, board_ip, board_ref, board_filename) " + 
					"	values (seq_board_num.nextval, ?, ?, ?, ?, seq_board_num.nextval, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString	(1, boardVo.getBoard_subject());
			pstmt.setString	(2, boardVo.getBoard_content());
			pstmt.setString	(3, boardVo.getBoard_writer());
			pstmt.setString	(4, boardVo.getBoard_ip());
			pstmt.setString	(5, boardVo.getBoard_filename());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, null);
		}
	} // 답글달기
	
	
	// 현재 사용자가 해당 글에 대해서 좋아요 했는지 확인
	public boolean isLike(Map<String, Object> paramMap) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select count(*) from tbl_like"
					+ "		where board_num = ?"
					+ "		and mem_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt	(1, (int) paramMap.get("board_num"));
			pstmt.setString	(2, (String) paramMap.get("mem_id"));
			rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			if (count > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return false;
	}
	
	
	// 좋아요
		public boolean boardLike(Map<String, Object> paramMap) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			ResultSet rs = null;
			
			try {
				// 현재 사용자가 해당 글에 대해서 좋아요 했는지 확인
				boolean isLike = isLike(paramMap);
				
				conn = getConnection();
				conn.setAutoCommit(false);	// transaction
				
				String sql = "";
				int i = 0;	// 1, -1
				
				if (isLike == true) {
					// 좋아요 처리를 한 상태라면 원복시키기
					// delete, count -1
					sql = "delete from tbl_like"
							+ "	where board_num = ?"
							+ "	and mem_id = ?";
					i = -1;
				} else {
					// 좋아요 처리를 안한 상태라면 추가
					// insert, count +1
					sql = "insert into tbl_like(like_num, board_num, mem_id)"
							+ "    values(seq_like_num.nextval, ?, ?)";
					i = +1;
				}
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt	(1, (int)paramMap.get("board_num"));
				pstmt.setString	(2, (String)paramMap.get("mem_id"));
				pstmt.executeUpdate();
				
				String sql2 = "update tbl_board set" 
						+ "			like_count = like_count + ? "	// i: 1, -1 
						+ "		where board_num = ?"; 	
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, i);
				pstmt2.setInt(2, (int)paramMap.get("board_num"));
				pstmt2.executeUpdate();
				
				conn.commit();				// transaction
				return true;
			} catch (Exception e) {
				try {
					conn.rollback(); 		// transaction
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} finally {
				try {
					conn.setAutoCommit(true); // transaction
				} catch (SQLException e) {
					e.printStackTrace();
				} 	
				closeAll(null, pstmt2, null);
				closeAll(conn, pstmt, rs);
			}
			
			return false;
		} // 좋아요.
}
