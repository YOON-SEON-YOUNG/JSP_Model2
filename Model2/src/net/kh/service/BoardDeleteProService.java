package net.kh.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.kh.persistence.BoardDao;
import net.kh.util.FileUploader;

public class BoardDeleteProService implements IBoardService {

	private BoardDao boardDao = BoardDao.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardDeleteProService 실행됨");
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String board_writer = request.getParameter("board_writer");
		
		// 글 작성자와 로그인한 사용자가 같은지를 판별
		HttpSession seisson = request.getSession();
		String mem_id = (String) seisson.getAttribute("mem_id");
		
		if(!board_writer.equals(mem_id)) {
			return "redirect:content.kh?board_num=" + board_num + "&msg=invalid_id";
		}
		
		// tbl_board 에서 데이터 삭제
		boolean result = boardDao.delete(board_num);
		
		String msg = "fail";
		if (result == true) {
			msg = "success";
			
			// upload 폴더에 있는 파일 삭제
			String board_filname = boardDao.getFilename(board_num);
			FileUploader.deleteFile(request, board_filname);
		}
		
		return "redirect:list.kh?msg=" + msg;
	}

}
