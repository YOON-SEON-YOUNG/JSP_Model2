package net.kh.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.kh.persistence.BoardDao;

public class BoardIsLikeService implements IBoardService {

	private BoardDao boardDao = BoardDao.getInstance();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		HttpSession session = request.getSession();
		String mem_id = (String) session.getAttribute("mem_id");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("board_num", board_num);
		paramMap.put("mem_id", mem_id);
		
		boolean isLike = boardDao.isLike(paramMap);
		
		String data = "unlike";
		if(isLike == true) {
			data = "like";
		}
		
		request.setAttribute("data", data);
		
		return "/WEB-INF/views/data.jsp";
	}

}
