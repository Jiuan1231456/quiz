package com.example.quiz.vo;

//�o��u�N��@�D���έp
public class Statistics {
	//q=question
	private int qId; //�D��
	
	private String qTiltle;//�D��
	
	private boolean neccesary;//����
	
	private String option; //�ﶵ
	
	private int count;//����

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
