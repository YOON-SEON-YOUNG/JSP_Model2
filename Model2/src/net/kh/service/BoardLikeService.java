package net.kh.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.kh.persistence.BoardDao;

public class BoardLikeService implements IBoardService {
	
	private BoardDao boardDao = BoardDao.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		HttpSession session = request.getSession();
		String mem_id = (String) session.getAttribute("mem_id");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("board_num", board_num);
		paramMap.put("mem_id", mem_id);
		
		boolean result = boardDao.boardLike(paramMap);
		
		String data = "fail";
		if(result == true) {
			data = "success";
		}
		request.setAttribute("data", data);
		
		return "/WEB-INF/views/data.jsp";
	}

}
