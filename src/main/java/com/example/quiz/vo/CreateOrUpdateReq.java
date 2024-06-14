package com.example.quiz.vo;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateOrUpdateReq {
	//�U���o��O�ݨ����Ѽ�
	private int id;//�ݨ��W�r
	
	private String name;//�ݨ��W�r

	private String description;//���D����

	@JsonProperty("start_date") //�}�l�ɶ�
	private LocalDate startDate;

	@JsonProperty("end_date")//�����ɶ�
	private LocalDate endDate;
	//====================================================
//	//�U���o��O���D���Ѽ�
//	@JsonProperty("question_id")//���D�s��
//	private int questionId;
//
//	private String content; //�ݨ��D��
//
//	private String type;
//
//	@JsonProperty("is_necessary")
//	private boolean necessary;
	
	@JsonProperty("question_list")
	private List<Question> questionList;
	
	@JsonProperty("is_published")
	private boolean published;

	
	//�a��id�Ѽƪ��Ҧ��غc��k
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
	//���a��id�Ѽƪ��Ҧ��غc��k
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

	// �غc��k��getter�N�n

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
