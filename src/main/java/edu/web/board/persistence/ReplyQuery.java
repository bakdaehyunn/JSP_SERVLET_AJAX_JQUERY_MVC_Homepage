package edu.web.board.persistence;

public interface ReplyQuery {
	public static final String TABLE_NAME = "reply";
	public static final String COL_REPLY_ID = "reply_id";
	public static final String COL_BOARD_ID = "board_id";
	public static final String COL_MEMBER_ID = "member_id";
	public static final String COL_REPLY_CONTENT = "reply_content";
	public static final String COL_REPLY_DATE_CREATED = "reply_date_created";

	// insert into reply values
	// (seq_reply.nextval, ?, ?, ?, sysdate)
	public static final String SQL_INSERT = "insert into " + TABLE_NAME + " values "
			+ "(reply_seq.nextval, ?, ?, ?, sysdate)";

	// select * from reply where board_id =?
	// order by reply_id desc;
	public static final String SQL_SELECT_BY_BOARD_ID = "select * from " + TABLE_NAME + " where " + COL_BOARD_ID
			+ " = ? order by " + COL_REPLY_ID + " desc";

	// update reply set
	// reply_content = ? , reply_date_created = sysdate
	// where reply_id = ?
	public static final String SQL_UPDATE = "update " + TABLE_NAME + " set " + COL_REPLY_CONTENT + " = ?, "
			+ COL_REPLY_DATE_CREATED + " = sysdate " + "where " + COL_REPLY_ID + " = ?";

	// delete from reply where reply_id = ?
	public static final String SQL_DELETE = "delete from " + TABLE_NAME + " where " + COL_REPLY_ID + " = ? ";

}
