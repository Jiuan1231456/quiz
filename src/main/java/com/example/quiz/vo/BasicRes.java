package com.example.quiz.vo;

//回應應到外部的頁面

public class BasicRes {
	private int statusCode;

	private String message;

	public BasicRes() {
		super();
		
	}

	public BasicRes(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
