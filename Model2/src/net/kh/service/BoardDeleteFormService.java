package net.kh.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardDeleteFormService implements IBoardService {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardDeleteFormService 실행됨");
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		request.setAttribute("board_num", board_num);
		
		return "/WEB-INF/views/board/board_delete_form.jsp";
	}

}
