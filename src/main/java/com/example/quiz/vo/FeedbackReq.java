package com.example.quiz.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeedbackReq {
	//@JsonProperty("quiz_Id") 如果有+這個，在postman上搜尋id就要打quiz_Id，
	//因為這邊沒加所以在postman送資料時要寫quizId
	private int quizId;

	public FeedbackReq() {
		super();
		
	}

	public FeedbackReq(int quizId) {
		super();
		this.quizId = quizId;
	}

	public int getQuizId() {
		return quizId;
	}


	

}
