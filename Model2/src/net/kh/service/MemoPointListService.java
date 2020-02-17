package net.kh.service;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.kh.domain.PointVo;
import net.kh.persistence.MemberDao;

public class MemoPointListService implements IBoardService {

	private MemberDao memberDao = MemberDao.getInstance();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		int point_num = Integer.parseInt(request.getParameter("point_num"));
//		int m_point = Integer.parseInt(request.getParameter("m_point"));
		
		HttpSession session = request.getSession();
		String mem_id = (String)session.getAttribute("mem_id");
		
		List<PointVo> pointList = memberDao.getMemberPointList(mem_id);
		
		request.setAttribute("pointList", pointList);
		
		return "/WEB-INF/views/point/point_list.jsp";
	}

}
