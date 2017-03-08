package me.ricardocunha.serverless.spring.model;

import java.util.Date;

public class Message {
    private String id;
    private String subject;
    private String topic;
	private String message;
	private String app;
    private Date dateOfExecution;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public Date getDateOfExecution() {
		return dateOfExecution;
	}
	public void setDateOfExecution(Date dateOfExecution) {
		this.dateOfExecution = dateOfExecution;
	}

}
