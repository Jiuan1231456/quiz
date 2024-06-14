package com.example.quiz.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FillinReq {
	@JsonProperty("quiz_id")
	//quiz_id�O�Φbpostman���A�g�b�e��ƨ���
	private int quizId;

	private String name;

	private String phone;

	private String email;

	private int age;
	@JsonProperty("fillin_list")
	private List<Fillin> fillinList;

	// �o��O�D�ؿﶵ
	// qId�bFillin.java
	// qu_id and answer map:answer�Y���h�ӮɥΤ���;�걵


	public FillinReq() {
		super();

	}

	// �o��N������Map<Integer,String> qIdAnswerMap���غc��k�F�A�]�����γo���k
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

// req�q�`���ݭn�Ψ�غc��k�A���]���n�����٬O�|�ͤ@��
// �w�]�غc��k�q�`�O��springboot�Ϊ��A���ͱa�Ѽƪ��غc��k�N�@�w�n�͹w�]�غc��k
