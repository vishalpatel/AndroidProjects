package com.vik.android.app.todo;

public class Task {
	private String description;
	private Boolean done;
	public Task(String desc) {
		this.description = desc;
		this.done = false;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getDone() {
		return done;
	}
	public void setDone(Boolean done) {
		this.done = done;
	}
}
