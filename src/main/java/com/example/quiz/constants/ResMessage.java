package com.example.quiz.constants;

import com.fasterxml.jackson.core.JsonProcessingException;

public enum ResMessage {
  //SUCCESS(int code, String message) 對應第18行
	//用逗號作串接
	//給QuizServiceImpl用的
	SUCCESS(200,"Success!!!!"),//
	PARAM_QUIZ_NAME_ERROR(400,"Param quiz name error!!"),//
	PARAM_DESCRIPTION_ERROR(400,"Description name error!!"),//
	PARAM_START_DATE_ERROR(400,"Start date error!!"),//
	PARAM_END_DATE_ERROR(400,"End date error!!"),
	PARAM_QUESTION_List_NOT_FOUND(404,"Param question list not found!!"),//
	PARAM_QUESTION_ID_ERROR(400,"Param question id  error!!"),//
	PARAM_QUIZ_ID_ERROR(400,"Param quiz id  error!!"),//
	PARAM_TITLE_ERROR(400,"Param title error!!"),//
	PARAM_OPTIONS_ERROR(400,"Param options error!!"),
	PARAM_TYPE_ERROR(400,"Param type error!!"),
	JSON_PROCESSING_EXCEPTION(400,"JSON processing error!!"),//
	UPDATE_ID_NOT_FOUND(404,"Update id not found!!"),
	
	//給ResponseServiceImpl用的
	PARAM_NAME_IS_REQUIRED(400,"Param name is required!!"),//
	PARAM_PHONE_IS_REQUIRED(400,"Param phone is required!!"),//
	PARAM_EMAIL_IS_REQUIRED(400,"Param email is required!!"),//
	PARAM_AGE_NOT_QUALIFIED(400,"Param age is qualified!!"),//
	QUIZ_NOT_FOUND(400,"Quiz not found!!"),//
	ANSWER_IS_REQUIRED(400,"Answer is required!!"),//
	ANSWER_OPTION_IS_NOT_MATCH(400,"Answer option is not match!!"),
	ANSWER_OPTION_TYPE_IS_NOT_MATCH(400,"Answer option_type is not match!!"),
	DUPLICATED_FILLIN(400,"Duplicated fillin!!");
	
	

	
	

		
	private int code;
	private String message;
	
	
//enum只能get無法set
	
	//enum的建構方法只會用到get不會用到set
	public int getCode() {
		return code;
	}
	//帶出所有屬性的建構方法
	private ResMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}
	public String getMessage() {
		return message;
	}

}
