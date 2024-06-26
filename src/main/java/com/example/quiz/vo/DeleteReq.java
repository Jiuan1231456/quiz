package com.example.quiz.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteReq {
	
	//找到列表中要刪除的id資料，找id就可以了
	@JsonProperty("id_list")
	private List<Integer> idList;

	//預設建構方法
	public DeleteReq() {
		super();
		System.out.println("預設建構方法");
	}

	//帶參數建構方法
	public DeleteReq(List<Integer> idList) {
		super();
		this.idList = idList;
		System.out.println("有參數的建構方法");
	}

	//只要get方法就好
	public List<Integer> getIdList() {
		return idList;
	}
	
	
	

}
