package net.kh.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.kh.service.IBoardService;

@WebServlet("*.mem")
public class MemberFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Map<String, IBoardService> commandMap = new HashMap<>();
       
    public MemberFrontController() {
        super();
    }
    

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("init 실행됨");
		loadProperties();
	}
	
	private void loadProperties() {
		ResourceBundle bundle = ResourceBundle.getBundle("net.kh.properties.MemberCommand");
		Enumeration<String> keys = bundle.getKeys(); // list.kh, write-form.kh, ....
		
		while (keys.hasMoreElements()) {
			String commandName = keys.nextElement();
			String className = bundle.getString(commandName);
			try {
				Class<?> commandClass = Class.forName(className);
				Object obj = commandClass.newInstance(); // new Xxx()
				commandMap.put(commandName, (IBoardService)obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // while
		
		System.out.println("commandMap:" + commandMap);
	}

	//로그인 된 상태인지 체크
	private boolean checkLogin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String mem_id = (String)session.getAttribute("mem_id");
		if (mem_id != null && !mem_id.equals("")) {
			return true;
		}
		return false;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//로그인 된 상태인지 체크
		boolean isLogin = checkLogin(request);	
		if (isLogin == false) {
			response.sendRedirect("list.kh?msg=not_login");
			return;
		}
		
		String command = getCommand(request);
		System.out.println("command:" + command);
		IBoardService service = commandMap.get(command);
		try {
			String path = service.execute(request, response);
			System.out.println("path:" + path);
			if (path.startsWith("redirect:")) {
				String location = path.substring("redirect:".length());
				response.sendRedirect(location);
			} else {
				RequestDispatcher dispatcher = 
						request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private String getCommand(HttpServletRequest request) {
		String uri = request.getRequestURI();
//		System.out.println("uri:" + uri); // /Model2/list.kh
		String contextPath = request.getContextPath();
//		System.out.println("contextPath:" + contextPath); // /Model2
		int beginIndex = contextPath.length() + 1;
		String command = uri.substring(beginIndex); // list.kh
//		System.out.println("command:" + command);
		return command;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
