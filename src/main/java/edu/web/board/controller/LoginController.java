package edu.web.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("*.go")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginController() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		controlURI(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		controlURI(request, response);

	}

	private void controlURI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String requestMethod = request.getMethod();
		System.out.println("호출 경로 : " + requestURI);
		System.out.println("호출 메소드 : " + requestMethod);

		if (requestURI.contains("login")) {
			System.out.println("login 호출");
			if (requestMethod.equals("GET")) {
				loginGET(request, response);
			} else if (requestMethod.equals("POST")) {
				loginPOST(request, response);
			}
		} else if (requestURI.contains("logout")) {
			System.out.println("logout 호출");
			logout(request, response);
		}

	}

	private void loginGET(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("loginGET() 호출");
		request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
		
	}

	private void loginPOST(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("loginPOST() 호출");
		String memberId = request.getParameter("memberId");
		String password = request.getParameter("password");
		
		System.out.println("memberId : "+ memberId);
		System.out.println("password : "+ password);
		
		if(memberId.equals("test") && password.equals("1234")) {
			HttpSession session =request.getSession();
			session.setAttribute("memberId", memberId);
			session.setMaxInactiveInterval(600);
			
			// 설정된 target url 가져오기
			String targetURL = (String) session.getAttribute("targetURL");
			System.out.println("targetURL : "+ targetURL);
			
			if(targetURL !=null) {
				
				// targetURL이 존재하는 경우(글 작성 버튼 클릭 -> 로그인하는 경우>
				session.removeAttribute("targetURL");
				response.sendRedirect(targetURL);
			} else {// targetURL이 존재하지 않는 경우(일반적인 로그인하는 경우)
				
				response.sendRedirect("index.jsp");
			}
		} else { 
			response.sendRedirect("login.go");
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("memberId")!= null) {
			session.removeAttribute("memberId");
			System.out.println("--------------세션만료시킴----------");
			response.sendRedirect("index.jsp");
		}
	}

}
