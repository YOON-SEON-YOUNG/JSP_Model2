package net.kh.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.kh.domain.BoardVo;
import net.kh.persistence.BoardDao;

public class BoardUpdateFormService implements IBoardService {
	
	private BoardDao boardDao = BoardDao.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardUpdateFormService 실행됨");
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		BoardVo boardVo = boardDao.getContentByNum(board_num);
		request.setAttribute("boardVo", boardVo);
		
		return "/WEB-INF/views/board/board_update_form.jsp";
	}

}
