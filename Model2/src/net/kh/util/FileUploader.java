package net.kh.util;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class FileUploader {
	
	private final static String SAVE_DIRECTORY = "upload";
	private final static int MAX_SIZE = 1024 * 1024 * 10;

	public static MultipartRequest upload(HttpServletRequest request) {
		ServletContext application = request.getServletContext();
		// JSP 내장객체:application
		String path = application.getRealPath("/"); // D:\workspace\JSP\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Model2\
		String saveDirectory = path + SAVE_DIRECTORY;
		
		// java.io.File
		File f = new File(saveDirectory);
		if (!f.exists()) {	// 해당 폴더가 존재하지 않는다면
			f.mkdir();		// make directory (폴더 생성)
		}
		System.out.println("path: " + path); // 저장된 경로
		try {
			MultipartRequest multi =
					new MultipartRequest(
							request,
							saveDirectory,
							MAX_SIZE,
							"utf-8",
							new DefaultFileRenamePolicy()
							);
			//-> 파일 업로드는 수행됨
			return multi;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public static void deleteFile(HttpServletRequest request, String filename) {
		ServletContext application = request.getServletContext();
		// JSP 내장객체:application
		
		String path = application.getRealPath("/");
		String savedFile = path + SAVE_DIRECTORY + File.separator + filename;
		// -> File.separator(Windows:\, Mac, Linux: /)
		System.out.println("savedFile: " + savedFile);
		
		File f = new File(savedFile);
		if(f.exists()) {	// 파일이 존재한다면
			f.delete();		// 삭제
		}
	}
}
