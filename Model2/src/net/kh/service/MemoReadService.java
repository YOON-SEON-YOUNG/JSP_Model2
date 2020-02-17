package net.kh.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import net.kh.common.IConstants;
import net.kh.persistence.MemoDao;

public class MemoReadService implements IBoardService {

	private MemoDao memoDao = MemoDao.getInstance();
	
	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int memo_num = Integer.parseInt(request.getParameter("memo_num"));
		HttpSession session = request.getSession();
		String mem_id = (String) session.getAttribute("mem_id");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("memo_num", memo_num);
		paramMap.put("mem_id", mem_id);
		paramMap.put("point", IConstants.MEMO_READ_POINT);
		paramMap.put("point_code", IConstants.MEMO_READ_CODE);
		
		String memo_open_date = memoDao.readMemo(paramMap);
		
		JSONObject obj = new JSONObject();
		obj.put("point", IConstants.MEMO_READ_POINT);
		obj.put("memo_open_date", memo_open_date);
		
		String data = obj.toJSONString();
		request.setAttribute("data", data);
		
		return "/WEB-INF/views/data.jsp";
	}

}
