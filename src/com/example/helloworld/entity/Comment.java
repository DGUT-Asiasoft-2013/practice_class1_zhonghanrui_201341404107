package com.example.helloworld.entity;

import java.util.Date;

public class Comment {
	private int id;
	private Date createDate;
	private Date editDate;
	private String text;
	private User author;

	public String getText() {
		return text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public void setText(String text) {
		this.text = text;
	}

}
