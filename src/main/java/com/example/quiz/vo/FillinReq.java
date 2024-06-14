package com.example.quiz.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FillinReq {
	@JsonProperty("quiz_id")
	//quiz_id是用在postman的，寫在送資料那邊
	private int quizId;

	private String name;

	private String phone;

	private String email;

	private int age;
	@JsonProperty("fillin_list")
	private List<Fillin> fillinList;

	// 這邊是題目選項
	// qId在Fillin.java
	// qu_id and answer map:answer若有多個時用分號;串接


	public FillinReq() {
		super();

	}

	// 這邊就不產生Map<Integer,String> qIdAnswerMap的建構方法了，因為不用這麼方法
	public FillinReq(int quizId, String name, String phone, String email, int age, List<Fillin> fillinList) {
		super();
		this.quizId = quizId;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.fillinList = fillinList;
	}

	public int getQuizId() {
		return quizId;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public int getAge() {
		return age;
	}

	public List<Fillin> getFillinList() {
		return fillinList;
	}

//	public Map<Integer, String> getqIdAnswerMap() {
//		return qIdAnswerMap;
//	}
}

// req通常不需要用到建構方法，但因為要測試還是會生一生
// 預設建構方法通常是給springboot用的，有生帶參數的建構方法就一定要生預設建構方法
