package com.example.quiz.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteReq {
	
	//тい璶埃id戈тid碞
	@JsonProperty("id_list")
	private List<Integer> idList;

	//箇砞篶よ猭
	public DeleteReq() {
		super();
		System.out.println("箇砞篶よ猭");
	}

	//盿把计篶よ猭
	public DeleteReq(List<Integer> idList) {
		super();
		this.idList = idList;
		System.out.println("Τ把计篶よ猭");
	}

	//璶getよ猭碞
	public List<Integer> getIdList() {
		return idList;
	}
	
	
	

}
