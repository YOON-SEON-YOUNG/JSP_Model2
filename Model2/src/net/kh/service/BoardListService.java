package net.kh.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.kh.domain.BoardVo;
import net.kh.persistence.BoardDao;
import net.kh.persistence.MemberDao;
import net.kh.persistence.MemoDao;

public class BoardListService implements IBoardService {
	
	private BoardDao boardDao = BoardDao.getInstance();
	private	MemoDao memoDao = MemoDao.getInstance();
	private MemberDao memberDao = MemberDao.getInstance();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardListService 실행됨");
		
		List<BoardVo> list = boardDao.getList();
		
		HttpSession session = request.getSession();
		String mem_id = (String) session.getAttribute("mem_id");
		int memoCount = memoDao.countNotRead(mem_id);
		int mem_point = memberDao.getMemberPoint(mem_id);
		
		
		request.setAttribute("list", list);
		request.setAttribute("memoCount", memoCount);
		request.setAttribute("mem_point", mem_point);
		
		return "/WEB-INF/views/board/board_list.jsp";	// forward
	}

}
