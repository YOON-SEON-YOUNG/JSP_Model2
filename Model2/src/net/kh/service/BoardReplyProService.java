package net.kh.service;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.oreilly.servlet.MultipartRequest;

import net.kh.domain.BoardVo;
import net.kh.persistence.BoardDao;
import net.kh.util.FileUploader;

public class BoardReplyProService implements IBoardService {

	private BoardDao boardDao = BoardDao.getInstance();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardReplyProService 실행됨");
		
		MultipartRequest multi = FileUploader.upload(request);
		
		String board_subject = multi.getParameter("board_subject");
		String board_content = multi.getParameter("board_content");
		
		HttpSession session = request.getSession();
		String mem_id = (String)session.getAttribute("mem_id");
		
		Enumeration<?> enumer = multi.getFileNames();
		String filename = (String) enumer.nextElement();
		String filesystemName = multi.getFilesystemName(filename);
		
		BoardVo boardVo = new BoardVo();
		boardVo.setBoard_subject(board_subject);
		boardVo.setBoard_writer(mem_id);
		boardVo.setBoard_content(board_content);
		boardVo.setBoard_filename(filesystemName);
		
		System.out.println("board_subject : " + board_subject);
		boardDao.reply(boardVo);
		
		return "redirect:list.kh";
	}

}
