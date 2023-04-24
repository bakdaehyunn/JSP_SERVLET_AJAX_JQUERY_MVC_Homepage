package edu.web.board.domain;

import java.util.Date;

public class BoardVO {
	private int boardId;
	private String boardTitle;
	private String boardContent;
	private String memberId;
	private Date boardDateCreated;
	
	public BoardVO() {}

	public BoardVO(int boardId, String boardTitle, String boardContent, String memberId, Date boardDateCreated) {
		super();
		this.boardId = boardId;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.memberId = memberId;
		this.boardDateCreated = boardDateCreated;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getBoardDateCreated() {
		return boardDateCreated;
	}

	public void setBoardDateCreated(Date boardDateCreated) {
		this.boardDateCreated = boardDateCreated;
	}

	@Override
	public String toString() {
		return "BoardVO [boardId=" + boardId + ", boardTitle=" + boardTitle + ", boardContent=" + boardContent
				+ ", memberId=" + memberId + ", boardDateCreated=" + boardDateCreated + "]";
	}
	

}
