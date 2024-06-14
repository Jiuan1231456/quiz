package com.example.quiz.vo;

import java.time.LocalDate;
import java.util.List;

public class FeedbackDetail {
	// �o��OQuiz�̪����e�A��ݨ����e(�ݨ����D�F�����B�_���ɶ�)�A���s�R�W�@�U�A���M�����ݩʦpname�|��Response�̭���
	private String quizname;

	private String description;

	private LocalDate startDate;

	private LocalDate endDate;

	// �o��OResponse�̪����e�A��ϥΪ̭Ӹ�
	private String name;

	private String phone;

	private String email;

	private int age;
	
	private List<Fillin> fillinList;

	

	public FeedbackDetail() {
		super();
		// TODO Auto-generated constructor stub
	}




	public FeedbackDetail(String quizname, String description, LocalDate startDate, LocalDate endDate, String name,
			String phone, String email, int age, List<Fillin> fillinList) {
		super();
		this.quizname = quizname;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.fillinList = fillinList;
	}


	public List<Fillin> getFillinList() {
		return fillinList;
	}


	public String getQuizname() {
		return quizname;
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

	
}
