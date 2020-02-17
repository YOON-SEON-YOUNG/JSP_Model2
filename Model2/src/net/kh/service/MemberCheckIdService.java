package net.kh.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.kh.persistence.MemberDao;

public class MemberCheckIdService implements IBoardService {

	private MemberDao memberDao = MemberDao.getInstance();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String mem_id = request.getParameter("mem_id");

		boolean result = memberDao.checkDupId(mem_id);	// Dao에서 리턴 받은 값
		
		String msg = "";
		if (result == true) {
			msg = "used_id";
		} else {
			msg = "available_id";
		}
		request.setAttribute("data", msg);
		
		return "/WEB-INF/views/data.jsp"; // forward
	}

}
