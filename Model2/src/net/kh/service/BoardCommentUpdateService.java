package net.kh.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.kh.persistence.BoardCommentDao;

public class BoardCommentUpdateService implements IBoardService {
	
	private BoardCommentDao commentDao = BoardCommentDao.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int reply_num = Integer.parseInt(request.getParameter("reply_num"));
		String reply_text = request.getParameter("reply_text");
		
		HttpSession session = request.getSession();
		String mem_id = (String)session.getAttribute("mem_id");
		
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("reply_num", reply_num);
		paramMap.put("reply_text", reply_text);
		paramMap.put("mem_id", mem_id);
		
		boolean result = commentDao.updateComment(paramMap);
		String data = "fail";
		if(result == true) {
			data = "success";
		}
		
		request.setAttribute("data", data);
		
		return "/WEB-INF/views/data.jsp";
	}

}
