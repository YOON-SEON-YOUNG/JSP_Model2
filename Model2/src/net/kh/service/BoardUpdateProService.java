package net.kh.service;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import net.kh.domain.BoardVo;
import net.kh.persistence.BoardDao;
import net.kh.util.FileUploader;

public class BoardUpdateProService implements IBoardService {
	
	private BoardDao boardDao = BoardDao.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardUpdateProService 실행됨");
		
		BoardVo boardVo = new BoardVo();
		
		String isBinary = request.getParameter("isBinary");
		
		// 글 작성자와 로그인한 사용자가 같은지를 판별
		HttpSession seisson = request.getSession();
		String mem_id = (String) seisson.getAttribute("mem_id");
		String board_writer = "";
		String board_filename = "";
		String fileSystemName = "";
		
		int board_num = 0;
		
		if(isBinary != null && isBinary.equals("N")) {
			board_num = Integer.parseInt(request.getParameter("board_num"));
			String board_subject = request.getParameter("board_subject");
			String board_content = request.getParameter("board_content");
			board_writer  = request.getParameter("board_writer");	// 위에서 얻음
			
			boardVo.setBoard_num	(board_num);
			boardVo.setBoard_subject(board_subject);
			boardVo.setBoard_content(board_content);
			boardVo.setBoard_writer	(mem_id);
			
		} else {
			MultipartRequest multi = FileUploader.upload(request);
			board_filename = multi.getParameter("board_filename");
			
			board_num = Integer.parseInt(multi.getParameter("board_num"));
			String board_subject = multi.getParameter("board_subject");
			String board_content = multi.getParameter("board_content");
			board_writer  = multi.getParameter("board_writer");		// 위에서 얻음
			
			Enumeration<?> enumer = multi.getFileNames();
			String filename = (String)enumer.nextElement();
			fileSystemName = multi.getFilesystemName(filename);
			
			boardVo.setBoard_num(board_num);
			boardVo.setBoard_subject(board_subject);
			boardVo.setBoard_content(board_content);
			boardVo.setBoard_writer(mem_id);
			
			boardVo.setBoard_filename(fileSystemName);
		}
		
		// 글 작성자와 로그인한 사용자가 같은지를 판별
		if(!board_writer.equals(mem_id)) {
			FileUploader.deleteFile(request, fileSystemName);
			return "redirect:content.kh?board_num=" + board_num + "&msg=invalid_id";
		}
		
		FileUploader.deleteFile(request, board_filename);	// 삭제요청
		
		boardDao.update(boardVo);
		
		return "redirect:content.kh?board_num=" + board_num;
	}

}
