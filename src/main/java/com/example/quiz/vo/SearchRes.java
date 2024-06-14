package com.example.quiz.vo;

import java.util.List;

import com.example.quiz.entity.Quiz;

public class SearchRes extends BasicRes {

	private List<Quiz> quizList;

//預設建構方法
	public SearchRes() {
		super();

	}

// 帶有參數的建構方法
	public SearchRes(int statusCode, String message, List<Quiz> quizList) {
		super(statusCode, message);
		this.quizList = quizList;

	}
	
	
// 建構get和set的建構方法
	public List<Quiz> getQuizList() {
		return quizList;
	}

	public void setQuizList(List<Quiz> quizList) {
		this.quizList = quizList;
	}

	

}
