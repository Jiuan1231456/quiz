package com.example.quiz.constants;

public enum OptionType {
	SINGLE_CHOICE("���"),//
	MULTI_CHOICE("�h��"),//
	TEXT("��r");//
	
	private String type;

	//�إ�get��k
	public String getType() {
		return type;
	}
	//�إ߱a�Ѽƪ��غc��k
	private OptionType(String type) {
		this.type = type;
	}

	

}
