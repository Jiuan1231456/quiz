package com.example.quiz.vo;

//這邊只代表一題的統計
public class Statistics {
	//q=question
	private int qId; //題號
	
	private String qTiltle;//題目
	
	private boolean neccesary;//必填
	
	private String option; //選項
	
	private int count;//次數

	public Statistics() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Statistics(int qId, String qTiltle, boolean neccesary, String option, int count) {
		super();
		this.qId = qId;
		this.qTiltle = qTiltle;
		this.neccesary = neccesary;
		this.option = option;
		this.count = count;
	}

	public int getqId() {
		return qId;
	}

	public String getqTiltle() {
		return qTiltle;
	}

	public boolean isNeccesary() {
		return neccesary;
	}

	public String getOption() {
		return option;
	}

	public int getCount() {
		return count;
	}

	
	
}
