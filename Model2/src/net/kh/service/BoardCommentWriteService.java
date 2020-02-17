package net.kh.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import net.kh.domain.BoardCommentVo;
import net.kh.persistence.BoardCommentDao;

public class BoardCommentWriteService implements IBoardService {

	private BoardCommentDao commentDao = BoardCommentDao.getInstance(); 
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String reply_text = request.getParameter("reply_text");
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		HttpSession session = request.getSession();
		String mem_id = (String)session.getAttribute("mem_id");
		System.out.println("mem_id: "+ mem_id);
		
		BoardCommentVo commentVo = new BoardCommentVo();
		commentVo.setBoard_num(board_num);
		commentVo.setReply_text(reply_text);
		commentVo.setReplyer(mem_id);
		boolean result = commentDao.insertComment(commentVo);
		
		String data = "fail";
		
		if (result == true) {
			data = "success";
		}
			
//		System.out.println("reply_text: " + reply_text);
//		System.out.println("board_num: " + board_num);
		
		request.setAttribute("data", data);
		return "/WEB-INF/views/data.jsp";
	}

}
