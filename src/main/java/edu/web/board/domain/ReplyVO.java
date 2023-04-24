package edu.web.board.domain;

import java.util.Date;

public class ReplyVO {
	private int replyId;
	private int board_id;
	private String member_id;
	private String replyContent;
	private Date replyDateCreated;
	public ReplyVO() {
		
	}
	public ReplyVO(int replyId, int board_id, String member_id, String replyContent, Date replyDateCreated) {
		
		this.replyId = replyId;
		this.board_id = board_id;
		this.member_id = member_id;
		this.replyContent = replyContent;
		this.replyDateCreated = replyDateCreated;
	}
	
	public int getReplyId() {
		return replyId;
	}
	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}
	public int getBoard_id() {
		return board_id;
	}
	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public Date getReplyDateCreated() {
		return replyDateCreated;
	}
	public void setReplyDateCreated(Date replyDateCreated) {
		this.replyDateCreated = replyDateCreated;
	}
}
