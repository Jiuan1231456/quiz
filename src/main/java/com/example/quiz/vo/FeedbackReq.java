package com.example.quiz.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeedbackReq {
	//@JsonProperty("quiz_Id") �p�G��+�o�ӡA�bpostman�W�j�Mid�N�n��quiz_Id�A
	//�]���o��S�[�ҥH�bpostman�e��Ʈɭn�gquizId
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
