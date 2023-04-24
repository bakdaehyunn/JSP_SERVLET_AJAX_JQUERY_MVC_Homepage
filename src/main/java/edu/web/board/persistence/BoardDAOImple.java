package edu.web.board.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.web.board.domain.BoardVO;
import edu.web.board.util.PageCriteria;
import edu.web.dbcp.connmgr.ConnMgr;

public class BoardDAOImple implements BoardDAO, BoardQuery{
	private static BoardDAOImple instance = null;
	
	private BoardDAOImple() {}
	
	public static BoardDAOImple getInstance() {
		if(instance == null) {
			instance = new BoardDAOImple();
		}
		
		return instance;
	}
	

	@Override
	public int insert(BoardVO vo) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_INSERT);
			pstmt.setString(1, vo.getBoardTitle());
			pstmt.setString(2, vo.getBoardContent());
			pstmt.setString(3, vo.getMemberId());
			
			result = pstmt.executeUpdate();
			System.out.println("insert 성공");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt);
		}
		
		return result;
	}

	@Override
	public List<BoardVO> select() {
		List<BoardVO> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_SELECT_ALL);
			
			rs = pstmt.executeQuery();
			
			int boardId;
			String boardTitle;
			String boardContent;
			String memberId;
			Date boardDateCreated;
			BoardVO vo = null;
			
			while(rs.next()) {
				boardId = rs.getInt(COL_BOARD_ID);
				boardTitle = rs.getString(COL_BOARD_TITLE);
				boardContent = rs.getString(COL_BOARD_CONTENT);
				memberId = rs.getString(COL_MEMBER_ID);
				boardDateCreated = rs.getTimestamp(COL_BOARD_DATE_CREATED);
				vo = new BoardVO(boardId, boardTitle, boardContent, memberId, boardDateCreated);
				list.add(vo);
			}
			System.out.println("select all 성공");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt, rs);
		}
		
		return list;
	}

	@Override
	public BoardVO select(int boardId) {
		BoardVO vo = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_SELECT_BY_BOARD_ID);
			pstmt.setInt(1, boardId);
			rs = pstmt.executeQuery();
			
			String boardTitle;
			String boardContent;
			String memberId;
			Date boardDateCreated;
			
			if(rs.next()) {
//				boardId = rs.getInt(COL_BOARD_ID);
				boardTitle = rs.getString(COL_BOARD_TITLE);
				boardContent = rs.getString(COL_BOARD_CONTENT);
				memberId = rs.getString(COL_MEMBER_ID);
				boardDateCreated = rs.getTimestamp(COL_BOARD_DATE_CREATED);
				vo = new BoardVO(boardId, boardTitle, boardContent, memberId, boardDateCreated);

			}
			System.out.println("select by board_id 성공");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt, rs);
		}
		
		return vo;
	}

	@Override
	public int update(BoardVO vo) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_UPDATE);
			pstmt.setString(1, vo.getBoardTitle());
			pstmt.setString(2, vo.getBoardContent());
			pstmt.setInt(3, vo.getBoardId());
			
			result = pstmt.executeUpdate();
			System.out.println("update 성공");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt);
		}
		
		return result;
	}

	@Override
	public int delete(int boardId) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_DELETE);
			pstmt.setInt(1, boardId);
			
			result = pstmt.executeUpdate();
			System.out.println("delete 성공");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt);
		}
		
		return result;
	}

	@Override
	public List<BoardVO> select(PageCriteria criteria) {
		List<BoardVO> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_SELECT_PAGESCOPE);
			pstmt.setInt(1,criteria.getStart());
			pstmt.setInt(2,criteria.getEnd());
			rs = pstmt.executeQuery();
			
			int boardId;
			String boardTitle;
			String boardContent;
			String memberId;
			Date boardDateCreated;
			BoardVO vo = null;
			
			while(rs.next()) {
				boardId = rs.getInt(COL_BOARD_ID);
				boardTitle = rs.getString(COL_BOARD_TITLE);
				boardContent = rs.getString(COL_BOARD_CONTENT);
				memberId = rs.getString(COL_MEMBER_ID);
				boardDateCreated = rs.getTimestamp(COL_BOARD_DATE_CREATED);
				vo = new BoardVO(boardId, boardTitle, boardContent, memberId, boardDateCreated);
				list.add(vo);
			}
			System.out.println("select page 성공");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt, rs);
		}
		
		return list;
	}

	@Override
	public int getTotalCounts() {
		int count =0;
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try {
			conn = ConnMgr.getConnection();
			pstmt= conn.prepareStatement(SQL_TOTAL_CNT);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt("total_cnt");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt, rs);
		}
		return count;
	}

}






