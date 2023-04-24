package edu.web.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.web.board.domain.BoardVO;
import edu.web.board.persistence.BoardDAO;
import edu.web.board.persistence.BoardDAOImple;
import edu.web.board.util.PageCriteria;
import edu.web.board.util.PageMaker;

@WebServlet("*.do") // *.do : ~.do로 선언된 HTTP 호출에 대해 반응
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String BOARD_URL = "WEB-INF/board/";
	private static final String MAIN = "index";
	private static final String LIST = "list";
	private static final String REGISTER = "register";
	private static final String DETAIL = "detail";
	private static final String UPDATE = "update";
	private static final String DELETE = "delete";
	private static final String EXTENSION = ".jsp";
	private static final String SERVER_EXTENSION = ".do";

	private static BoardDAO dao;

	public BoardController() {
		dao = BoardDAOImple.getInstance();
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
		System.out.println("호출 방식 : " + requestMethod);

		if (requestURI.contains(LIST + SERVER_EXTENSION)) {
			System.out.println("list 호출 확인");
			list(request, response);
		} else if (requestURI.contains(REGISTER + SERVER_EXTENSION)) {
			System.out.println("register 호출 확인");
			if (requestMethod.equals("GET")) { // GET 방식(페이지 불러오기)
				registerGET(request, response);
			} else if (requestMethod.equals("POST")) { // POST 방식(DB에 저장)
				registerPOST(request, response);
			}
		} else if (requestURI.contains(DETAIL + SERVER_EXTENSION)) {
			System.out.println("detail 호출 확인");
			if (requestMethod.equals("GET")) {
				detailGet(request, response);
			}
		} else if (requestURI.contains(UPDATE + SERVER_EXTENSION)) {
			System.out.println("update 호출 확인");
			if (requestMethod.equals("GET")) {
				updateGet(request, response);
			} else if (requestMethod.equals("POST")) {
				updatePost(request, response);
			}

		} else if (requestURI.contains(DELETE + SERVER_EXTENSION)) {
			System.out.println("delete 호출 확인");
			if (requestMethod.equals("POST")) {
				deletePost(request, response);
			}
		}

	} // end controlURI

	private void deletePost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		int result = dao.delete(boardId);
		if (result == 1) {
			PrintWriter out = response.getWriter();
			out.print("<head>" + "<meta charset='UTF-8'>" + "</head>");
			out.print("<script>alert('게시글 삭제 성공');</script>");
			out.print("<script>location.href='" + MAIN + EXTENSION + "';</script>");
		}
	}

	private void updatePost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		String boardTitle = request.getParameter("boardTitle");
		String boardContent = request.getParameter("boardContent");
		BoardVO vo = new BoardVO(boardId, boardTitle, boardContent, null, null);

		System.out.println(vo);

		int result = dao.update(vo);
		System.out.println("결과 : " + result);
		if (result == 1) {
			PrintWriter out = response.getWriter();
			out.print("<head>" + "<meta charset='UTF-8'>" + "</head>");
			out.print("<script>alert('게시글 수정 성공');</script>");
			out.print("<script>location.href='" + DETAIL + SERVER_EXTENSION + "?boardId=" + boardId + "';</script>");
		}

	}

	private void updateGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String boardId = request.getParameter("boardId");
		BoardVO vo = dao.select(Integer.parseInt(boardId));
		if (vo != null) {
			System.out.println("해당보드아이디에서 데이터 불러오기 성공");
			String path = BOARD_URL + UPDATE + EXTENSION;
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			request.setAttribute("vo", vo);
			dispatcher.forward(request, response);
		}

	}

	private void detailGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String boardId = request.getParameter("boardId");
		BoardVO vo = dao.select(Integer.parseInt(boardId));
		if (vo != null) {
			System.out.println("해당보드아이디에서 데이저 불러오기 성공");
			String path = BOARD_URL + DETAIL + EXTENSION;
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			request.setAttribute("vo", vo);
			dispatcher.forward(request, response);
		}
	}

	// 전체 게시판 내용(list)을 DB에서 가져오고, 그 데이터를 list.jsp 페이지에 보내기
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// List<BoardVO> list = dao.select(null);
		String page = request.getParameter("page");

		PageCriteria criteria = new PageCriteria();

		if (page != null) {
			criteria.setPage(Integer.parseInt(page));
		}

		List<BoardVO> list = dao.select(criteria);

		String path = BOARD_URL + LIST + EXTENSION;
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		request.setAttribute("list", list);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria); // 현재 페이지 번호 및 페이지 당 게시글 개수 정보 저장
		int totalCount = dao.getTotalCounts(); // 전체 게시글 수
		pageMaker.setTotalCount(totalCount);// 전체 게시글 수 저장
		pageMaker.setPageData(); // 저장된 데이터를 바탕으로 page 링크 데이터 생성
		System.out.println("전체 게시글 수 : " + pageMaker.getTotalCount());
		System.out.println("현재 선택된 페이지 : " + criteria.getPage());
		System.out.println("한 페이지 당 게시글 수 : " + criteria.getNumsPerPage());
		System.out.println("페이지 링크 번호 개수 : " + pageMaker.getNumsOfPageLinks());
		System.out.println("시작 페이지 링크 번호 : " + pageMaker.getStartPageNo());
		System.out.println("끝 페이지 링크 번호 : " + pageMaker.getEndPageNo());
		System.out.println("이전 버튼 존재 유무 : " + pageMaker.isHasPrev());
		System.out.println("다음 버튼 존재 유무 : " + pageMaker.isHasNext());

		request.setAttribute("pageMaker", pageMaker);
		dispatcher.forward(request, response);

	} // end list()

	private void registerGET(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 로그인 세션 체크
		HttpSession session = request.getSession();
		String memberId = (String) session.getAttribute("memberId");

		if (memberId != null) { // memberId 세션이 존재(로그인 상태)
			String path = BOARD_URL + REGISTER + EXTENSION;
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		} else { // memberId세션이 존재하지 않음(로그아웃 상태)
			// session에 target url 정보를 저장
			session.setAttribute("targetURL", REGISTER + SERVER_EXTENSION);
			response.sendRedirect("login.go");
		}

	} // end registerGET()

	private void registerPOST(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String boardTitle = request.getParameter("boardTitle");
		String memberId = request.getParameter("memberId");
		String boardContent = request.getParameter("boardContent");
		BoardVO vo = new BoardVO(0, boardTitle, boardContent, memberId, null);
		System.out.println(vo);

		int result = dao.insert(vo);
		System.out.println("결과 : " + result);
		if (result == 1) {
			PrintWriter out = response.getWriter();
			out.print("<head>" + "<meta charset='UTF-8'>" + "</head>");
			out.print("<script>alert('게시글 작성 성공');</script>");
			out.print("<script>location.href='" + MAIN + EXTENSION + "';</script>");
		}

	} // end registerPOST()

}
