//這邊是寫"回傳已填寫的答案做檢視"
package com.example.quiz.vo;

import com.fasterxml.jackson.annotation.JsonProperty;



public class Fillin {
//	qId=question_id
	@JsonProperty("question_id")
	private int qId; //題目名稱
	
	private String question;
	//多個選項是用(;)串接
	private String options;
	//多個答案是用(;)串接
	private String answer;//已填寫的答案

	private String type; //是多選、單選或文字輸入類型

	private boolean necessary;
	
	
	public Fillin() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Fillin(int qId, String question, String options, String answer, String type, boolean necessary) {
		super();
		this.qId = qId; //題號
		this.question = question;
		this.options = options; //選項
		this.answer = answer;
		this.type = type;
		this.necessary = necessary;
	}


	public int getqId() {
		return qId;
	}


	public String getQuestion() {
		return question;
	}


	public String getOptions() {
		return options;
	}


	public String getAnswer() {
		return answer;
	}


	public String getType() {
		return type;
	}


	public boolean isNecessary() {
		return necessary;
	}



	


}
