package edu.web.board.persistence;

public interface BoardQuery {
	public static final String TABLE_NAME = "BOARD";
	public static final String COL_BOARD_ID = "BOARD_ID";
	public static final String COL_BOARD_TITLE = "BOARD_TITLE";
	public static final String COL_BOARD_CONTENT = "BOARD_CONTENT";
	public static final String COL_MEMBER_ID = "MEMBER_ID";
	public static final String COL_BOARD_DATE_CREATED = "BOARD_DATE_CREATED";

	// 새 글 작성
	// insert into BOARD
	// values (BOARD_SEQ.NEXTVAL, ?, ?, ?, sysdate);
	public static final String SQL_INSERT = "insert into " + TABLE_NAME + " values "
			+ "(BOARD_SEQ.NEXTVAL, ?, ?, ?, sysdate)";

	// 전체 게시글 선택
	// select * from BOARD order by BOARD_ID desc;
	public static final String SQL_SELECT_ALL = "select * from " + TABLE_NAME + " order by " + COL_BOARD_ID + " desc";

	// 특정 게시글 선택
	// select * from BOARD where BOARD_ID = ?;
	public static final String SQL_SELECT_BY_BOARD_ID = "select * from " + TABLE_NAME + " where " + COL_BOARD_ID
			+ " = ?";

	// 특정 게시글 수정
	// update BOARD set
	// BOARD_TITLE = ?, BOARD_CONTENT = ?, BOARD_DATE_CREATED = SYSDATE
	// where BOARD_ID = ?;
	public static final String SQL_UPDATE = "update " + TABLE_NAME + " set " + COL_BOARD_TITLE + " = ?, "
			+ COL_BOARD_CONTENT + " = ?, " + COL_BOARD_DATE_CREATED + " = sysdate " + "where " + COL_BOARD_ID + " = ?";

	// 특정 게시글 삭제
	// delete from BOARD where BOARD_ID = ?;
	public static final String SQL_DELETE = "delete from " + TABLE_NAME + " where " + COL_BOARD_ID + " = ?";

	// 게시글 페이징 처리 검색
	// select board_id, board_title, board_content, member_id, board_date_created
	// from(
	// select rownum rn, board.* from board order by board_id desc
	// ) where rn between 1 and 3;

	public static final String SQL_SELECT_PAGESCOPE = "select " + COL_BOARD_ID + ", " + COL_BOARD_TITLE + ", "
			+ COL_BOARD_CONTENT + ", " + COL_MEMBER_ID + ", " + COL_BOARD_DATE_CREATED + " from(" + "select rownum rn, "
			+ TABLE_NAME + ".* from " + TABLE_NAME + " order by " + COL_BOARD_ID + " desc "
			+ ") where rn between ? and ?";
	
	// select count(board_id) total_cnt from board;
	public static final String SQL_TOTAL_CNT = 
			"select count("  + COL_BOARD_ID + ") total_cnt from "
			+ TABLE_NAME;
}
