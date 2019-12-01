package Vo;

import java.io.Serializable;

public class Post implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int postNo;
	private String title;
	private String content;
	private String postingDate;
	private int sid;
	private int available;	//available변수를 통해서 DB의 post를 삭제하지 않으면서 보여줄 post를 출력
	
	public int getPostNo() {
		return postNo;
	}
	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPostingDate() {
		return postingDate;
	}
	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}

}
