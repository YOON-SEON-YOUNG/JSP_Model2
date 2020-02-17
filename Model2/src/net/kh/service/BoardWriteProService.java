package net.kh.service;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import net.kh.domain.BoardVo;
import net.kh.persistence.BoardDao;
import net.kh.util.FileUploader;

public class BoardWriteProService implements IBoardService {

	private BoardDao boardDao = BoardDao.getInstance();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardWriteProService 실행됨");
		
		MultipartRequest multi = FileUploader.upload(request);
		
		String board_subject 	= multi.getParameter("board_subject");
		String board_content 	= multi.getParameter("board_content");
		String board_ip 		= request.getRemoteAddr();
		
		HttpSession session = request.getSession();
		String mem_id = (String)session.getAttribute("mem_id");
		
		Enumeration<?> enumer = multi.getFileNames();	// 파일명을 얻는게 아닌
														// <input type="file">인 요소의 name 속성의 값을 얻는다.
		String filename = (String)enumer.nextElement();	// name="board_filename"의 값
		System.out.println("filename: " + filename);
		String board_filename = multi.getFilesystemName(filename);	// 실제 저장된 이름
		
		BoardVo boardVo = new BoardVo();
		boardVo.setBoard_subject	(board_subject);
		boardVo.setBoard_content	(board_content);
		boardVo.setBoard_writer		(mem_id);
		boardVo.setBoard_filename	(board_filename);
		boardVo.setBoard_ip			(board_ip);
		System.out.println("BoardWriteProService, boardVo: " + boardVo);
		
		boardDao.write(boardVo);
		
		return "redirect:list.kh";
	}

}
