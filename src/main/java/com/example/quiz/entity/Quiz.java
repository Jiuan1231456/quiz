package com.example.quiz.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "quiz")

public class Quiz {
	// 因為PK是AI(AutoIncremental)所以要加上此Annotation
	// strategy:指的是AI的生成策略
	// GenerationType.IDENTITY:代表PK由資料庫來自動增加
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;

	@Column(name = "start_date")
	private LocalDate startDate;
	
	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "questions")
	private String questions;
	
	@Column(name = "published")
	private boolean published;

	public Quiz() {
		super();
		// TODO Auto-generated constructor stub
	}

	// 下面的建構方法不選id
	public Quiz(String name, String description, LocalDate startDate, LocalDate endDate, String questions,
			boolean published) {
		super();
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.questions = questions;
		this.published = published;
	}

	// 再+一個有id的建構方法

	public Quiz(int id, String name, String description, LocalDate startDate, LocalDate endDate, String questions,boolean published) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.questions = questions;
		this.published = published;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getQuestions() {
		return questions;
	}

	public void setQuestions(String questions) {
		this.questions = questions;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	
	// 下面getter setter的方法要選id

	//

}
