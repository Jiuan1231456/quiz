package com.example.quiz.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteReq {
	
	//���C���n�R����id��ơA��id�N�i�H�F
	@JsonProperty("id_list")
	private List<Integer> idList;

	//�w�]�غc��k
	public DeleteReq() {
		super();
		System.out.println("�w�]�غc��k");
	}

	//�a�Ѽƫغc��k
	public DeleteReq(List<Integer> idList) {
		super();
		this.idList = idList;
		System.out.println("���Ѽƪ��غc��k");
	}

	//�u�nget��k�N�n
	public List<Integer> getIdList() {
		return idList;
	}
	
	
	

}
