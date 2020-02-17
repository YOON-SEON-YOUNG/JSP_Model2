package net.kh.service;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import net.kh.domain.MemberVo;
import net.kh.persistence.MemberDao;
import net.kh.util.FileUploader;

public class MemberJoinProService implements IBoardService {

	private	MemberDao memberDao = MemberDao.getInstance(); 
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		MultipartRequest multi = FileUploader.upload(request);
		
		String mem_id = multi.getParameter("mem_id");
		String mem_pass = multi.getParameter("mem_pass");
		String mem_name = multi.getParameter("mem_name");
		
		Enumeration<?> enumer = multi.getFileNames();	// 파일명을 얻는게 아닌
		// <input type="file">인 요소의 name 속성의 값을 얻는다.
		String filename = (String)enumer.nextElement();	// name="board_filename"의 값
//		System.out.println("filename: " + filename);
		String filesystemName = multi.getFilesystemName(filename);	// 실제 저장된 이름
		
		
//		MemberVo memberVo = new MemberVo(mem_id, mem_pass, mem_name, 0); 둘중 하나 써도 상관없음~ㅇ0ㅇ
		MemberVo memberVo = new MemberVo();
		memberVo.setMem_id(mem_id);
		memberVo.setMem_pass(mem_pass);
		memberVo.setMem_name(mem_name);
		memberVo.setMem_pic(filesystemName);
		
		System.out.println("memberVo: " + memberVo);
		
//		boolean result = true;
		boolean result = memberDao.insertMember(memberVo);
		
		String redirectPath = "redirect:";
		if(result == true) {
			redirectPath += "list.kh?msg=mem_join_success";
		} else {
			redirectPath += "member-join-form.kh?msg=mem_join_fail";
		}
	
		
		return redirectPath;
	}

}
