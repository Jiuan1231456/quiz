//�o��O�g"�^�Ǥw��g�����װ��˵�"
package com.example.quiz.vo;

import com.fasterxml.jackson.annotation.JsonProperty;



public class Fillin {
//	qId=question_id
	@JsonProperty("question_id")
	private int qId; //�D�ئW��
	
	private String question;
	//�h�ӿﶵ�O��(;)�걵
	private String options;
	//�h�ӵ��׬O��(;)�걵
	private String answer;//�w��g������

	private String type; //�O�h��B���Τ�r��J����

	private boolean necessary;
	
	
	public Fillin() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Fillin(int qId, String question, String options, String answer, String type, boolean necessary) {
		super();
		this.qId = qId; //�D��
		this.question = question;
		this.options = options; //�ﶵ
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
