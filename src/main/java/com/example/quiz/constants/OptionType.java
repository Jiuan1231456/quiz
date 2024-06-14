package com.example.quiz.constants;

public enum OptionType {
	SINGLE_CHOICE("單選"),//
	MULTI_CHOICE("多選"),//
	TEXT("文字");//
	
	private String type;

	//建立get方法
	public String getType() {
		return type;
	}
	//建立帶參數的建構方法
	private OptionType(String type) {
		this.type = type;
	}

	

}
