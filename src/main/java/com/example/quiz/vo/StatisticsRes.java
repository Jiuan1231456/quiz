package com.example.quiz.vo;

import java.time.LocalDate;
import java.util.Map;

public class StatisticsRes extends BasicRes {
	private String quizName;//問卷名稱
	private LocalDate startDate;//開始日期
	private LocalDate endtDate;//結束日期
	private Map<Integer, Map<String, Integer>> countMap ;

	
	
	public StatisticsRes() {
		super();
		
	}
	public StatisticsRes(int statusCode, String message) {
		super(statusCode, message);
		
	}
	
	
	public StatisticsRes(int statusCode, String message,String quizName, LocalDate startDate, LocalDate endtDate,
			Map<Integer, Map<String, Integer>> countMap) {
		super(statusCode, message);
		this.quizName = quizName;
		this.startDate = startDate;
		this.endtDate = endtDate;
		this.countMap = countMap;
	}
	
	public String getQuizName() {
		return quizName;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public LocalDate getEndtDate() {
		return endtDate;
	}
	public Map<Integer, Map<String, Integer>> getCountMap() {
		return countMap;
	}

	
	
	
	
	
	
}
