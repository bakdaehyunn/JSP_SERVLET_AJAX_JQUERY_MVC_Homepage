package edu.web.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.web.board.domain.ReplyVO;
import edu.web.board.persistence.ReplyDAO;
import edu.web.board.persistence.ReplyDAOImple;


@WebServlet("/replies/*")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ReplyDAO dao;
       
  
    public ReplyController() {
       dao = ReplyDAOImple.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		controlURI(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		controlURI(request, response);
	}
	
	private void controlURI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);
		
		if(requestURI.contains("add")) {
			System.out.println("add 호출 확인");
			replyAdd(request, response);
			
		} else if(requestURI.contains("all")) {
			System.out.println("all 호출 확인");
			replyList(request, response);
		} else if(requestURI.contains("update")) {
			System.out.println("update 호출 확인");
			replyUpdate(request, response);
		} else if (requestURI.contains("delete")){
			System.out.println("delete 호출 확인");
			replyDelete(request, response);
		}
	}// end controlURI()
	
	private void replyAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String obj = request.getParameter("obj");
		System.out.println(obj);
		
		JSONParser parser  = new JSONParser();
		
		JSONObject jsonObject;
		
		try {
			jsonObject = (JSONObject) parser.parse(obj);
			
			int boardId = Integer.parseInt((String) jsonObject.get("boardId"));
			String memberId = (String) jsonObject.get("memberId");
			String replyContent = (String) jsonObject.get("replyContent");
			
			ReplyVO vo = new ReplyVO(0, boardId,memberId, replyContent, null);
			System.out.println(vo.toString());
			
			int result = dao.insert(vo);
			if(result == 1) {
				response.getWriter().append("success");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void replyList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		List<ReplyVO> list = dao.select(boardId);
		
		JSONArray jsonArray = new JSONArray();
		for(int i = 0; i < list.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			ReplyVO vo = list.get(i);
			jsonObject.put("replyId", vo.getReplyId());
			jsonObject.put("boardId", vo.getBoard_id());
			jsonObject.put("memberId", vo.getMember_id());
			jsonObject.put("replyContent", vo.getReplyContent());
			jsonObject.put("replyDateCreated", vo.getReplyDateCreated().toString()); //time은 toString 사용해야함 (.(점)때문에 인식이 안된다고함)
			jsonArray.add(jsonObject);
		}
		System.out.println(jsonArray.toString());
		response.getWriter().append(jsonArray.toString());
	}
	
	private void replyUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int replyId = Integer.parseInt(request.getParameter("replyId"));
		String  replyContent = request.getParameter("replyContent");
		ReplyVO vo = new ReplyVO(replyId, 0, "",replyContent,null);
		int result = dao.update(vo);
		if(result == 1 ) {
			response.getWriter().append("success");
		}
	}
	private void replyDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int replyId = Integer.parseInt(request.getParameter("replyId"));
		int result = dao.delete(replyId);
		if(result == 1 ) {
			response.getWriter().append("success");
		}
	}

}
