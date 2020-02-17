package net.kh.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import net.kh.domain.BoardCommentVo;
import net.kh.persistence.BoardCommentDao;

public class BoardCommentListService implements IBoardService {

	private BoardCommentDao commentDao = BoardCommentDao.getInstance();
	
	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		List<BoardCommentVo> list = commentDao.getCommentList(board_num);
		
		JSONArray jsonArray = new JSONArray();			// []
		for (BoardCommentVo vo : list) {
			JSONObject jsonObject = new JSONObject();	// {}
			// [{
			//		"reply_num" : 1,
			//		"board_num"	: 82
			// }]
			jsonObject.put("reply_num", vo.getReply_num());
			jsonObject.put("board_num", board_num);
			jsonObject.put("replyer", vo.getReplyer());
			jsonObject.put("reply_date", vo.getReply_date().toString());
			jsonObject.put("reply_text", vo.getReply_text());
			jsonArray.add(jsonObject);
		}
		
		request.setAttribute("data", jsonArray.toJSONString());
		
		return "/WEB-INF/views/data.jsp";
	}

}
