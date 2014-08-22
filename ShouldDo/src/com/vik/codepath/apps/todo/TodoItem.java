package com.vik.codepath.apps.todo;

public class TodoItem {
	private long id = 0;
	private int completedStatus = 0;
	private String description;
	private long timestamp = 0;
	
	public TodoItem(String desc){
		setDescription(desc);
		markCompleted(false);
		setID(0);
		setTimestamp(System.currentTimeMillis()/1000);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean isCompleted() {
		if (completedStatus != 0)
			return true;
		else
			return false;
	}

	public void markCompleted(Boolean completedStatus) {
		if (completedStatus)
			this.completedStatus = 1;
		else
			this.completedStatus = 0;
	}

	public long getID() {
		return id;
	}

	public void setID(long id) {
		this.id = id;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String toString() {
		return getDescription();
	}
}
