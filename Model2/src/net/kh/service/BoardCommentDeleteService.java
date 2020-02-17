package net.kh.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.kh.persistence.BoardCommentDao;

public class BoardCommentDeleteService implements IBoardService {

	private BoardCommentDao commentDao = BoardCommentDao.getInstance();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int reply_num = Integer.parseInt(request.getParameter("reply_num"));
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		HttpSession session = request.getSession();
		String mem_id = (String)session.getAttribute("mem_id");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("reply_num", reply_num);
		paramMap.put("mem_id", mem_id);
		paramMap.put("board_num", board_num);
		
		boolean result = commentDao.deleteComment(paramMap);
		String data = "fail";
		if (result == true) {
			data = "success";
		}
		request.setAttribute("data", data);
		
		return "/WEB-INF/views/data.jsp";
	}

}
