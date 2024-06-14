package com.example.quiz.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;

public class Feedback {
	private String userName;
	
	private LocalDateTime fillinDateTime;
	
	private FeedbackDetail feedbckDetail;

	public Feedback() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Feedback(String userName, LocalDateTime fillinDateTime, FeedbackDetail feedbckDetail) {
		super();
		this.userName = userName;
		this.fillinDateTime = fillinDateTime;
		this.feedbckDetail = feedbckDetail;
	}

	public String getUserName() {
		return userName;
	}

	public LocalDateTime getFillinDateTime() {
		return fillinDateTime;
	}

	public FeedbackDetail getFeedbckDetail() {
		return feedbckDetail;
	}
	
	
	

}
