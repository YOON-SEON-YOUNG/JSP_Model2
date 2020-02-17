package net.kh.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.kh.persistence.MemoDao;

public class MemoDeleteService implements IBoardService {

	private MemoDao memoDao = MemoDao.getInstance();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String memo_nums = request.getParameter("memo_nums");
		
		HttpSession session = request.getSession();
		String mem_id = (String) session.getAttribute("mem_id");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("memo_nums", memo_nums);
		paramMap.put("mem_id", mem_id);
		
		boolean result = memoDao.deleteMemo(paramMap);
		String data = "fail";
		if (result == true) {
			data = "success";
		}
		
		request.setAttribute("data", data);
		
		
		return "/WEB-INF/views/data.jsp";
	}

}
