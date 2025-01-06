package model.entities;

import java.util.Date;

public class Post {
	private int id;
	private String content;
	private String date;
	
	public Post() {
		this(0);
	}
	
	public Post(int id){
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String dataFormatada) {
		this.date = dataFormatada;
	}

	public int getId() {
		return id;
	}
}