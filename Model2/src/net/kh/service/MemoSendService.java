package net.kh.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.kh.common.IConstants;
import net.kh.domain.MemoVo;
import net.kh.domain.PointVo;
import net.kh.persistence.MemoDao;

public class MemoSendService implements IBoardService {

	private MemoDao memoDao = MemoDao.getInstance();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String memo_receiver = request.getParameter("memo_receiver");
		String memo_text = request.getParameter("memo_text");
		
		
		HttpSession session = request.getSession();
		String mem_id = (String) session.getAttribute("mem_id");
		
		PointVo pointVo = new PointVo();
		pointVo.setM_point(IConstants.MEMO_SEND_POINT);
		pointVo.setMem_id(mem_id);
		pointVo.setPoint_code(IConstants.MEMO_SEND_CODE);

		MemoVo memoVo = new MemoVo();
		memoVo.setMemo_receiver(memo_receiver);
		memoVo.setMemo_sender(mem_id);	// 보내는 사람, 로그인한 사용자
		memoVo.setMemo_text(memo_text);
		
		memoDao.sendMemo(pointVo, memoVo);
		
		return "redirect:memo-list.mem";
	}
	
}
