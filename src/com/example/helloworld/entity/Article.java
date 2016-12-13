package com.example.helloworld.entity;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8898081862773638025L;
	private int id;
	private User author;
	private Date createDate;
	private Date editDate;

	private String title;
	private String text;
	private String authorName;
	private String authorAvatar;

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public void setAuthorAvatar(String authorAvatar) {
		this.authorAvatar = authorAvatar;
	}

	public String getAuthorAvatar() {
		return authorAvatar;
	}

	public String getAuthorName() {
		return authorName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
