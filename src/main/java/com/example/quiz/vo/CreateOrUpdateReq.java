package com.example.quiz.vo;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateOrUpdateReq {
	//下面這邊是問卷的參數
	private int id;//問卷名字
	
	private String name;//問卷名字

	private String description;//問題說明

	@JsonProperty("start_date") //開始時間
	private LocalDate startDate;

	@JsonProperty("end_date")//結束時間
	private LocalDate endDate;
	//====================================================
//	//下面這邊是問題的參數
//	@JsonProperty("question_id")//問題編號
//	private int questionId;
//
//	private String content; //問卷題目
//
//	private String type;
//
//	@JsonProperty("is_necessary")
//	private boolean necessary;
	
	@JsonProperty("question_list")
	private List<Question> questionList;
	
	@JsonProperty("is_published")
	private boolean published;

	
	//帶有id參數的所有建構方法
	public CreateOrUpdateReq(int id, String name, String description, LocalDate startDate, LocalDate endDate,
			List<Question> questionList, boolean published) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.questionList = questionList;
		this.published = published;
	}
	//不帶有id參數的所有建構方法
	public CreateOrUpdateReq(String name, String description, LocalDate startDate, LocalDate endDate,
			List<Question> questionList, boolean published) {
		super();
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.questionList = questionList;
		this.published = published;
	}

	// 建構方法選getter就好

	public CreateOrUpdateReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	
	public List<Question> getQuestionList() {
		return questionList;
	}

	public boolean isPublished() {
		return published;
	}

	public int getId() {
		return id;
	}



}
