package net.kh.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.kh.domain.BoardVo;
import net.kh.domain.MemoVo;
import net.kh.persistence.MemoDao;

public class MemoListService implements IBoardService {
	
	private MemoDao memoDao = MemoDao.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemoListService 실행됨");
		
		// MemoDao - 목록가져오기 (mem_id) -> getMemoList(String mem_id)
		HttpSession session = request.getSession();
		String mem_id = (String) session.getAttribute("mem_id");
		
		// List<MemoVo> list
		List<MemoVo> memoList = memoDao.getMemoList(mem_id);
		
		// request 영역에 넣고
		request.setAttribute("memoList", memoList);
		
		return "/WEB-INF/views/memo/memo_list.jsp";
	}

}
