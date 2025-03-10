package com.webjjang.boardreply.vo;

public class BoardReplyVO {
	private Long no;
	private Long rno;
	private String content;
	private String writer;
	private String writeDate;
	private String pw;
	
	//getter, setter
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public Long getRno() {
		return rno;
	}
	public void setRno(Long rno) {
		this.rno = rno;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	//toString
	@Override
	public String toString() {
		return "BoardReplyVO [no=" + no + ", content=" + content + ", writer=" + writer + ", writeDate=" + writeDate
				+ ", pw=" + pw + "]";
	}
}