package com.example.quiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.mapping.Array;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.quiz.entity.Response;

@SpringBootTest
class QuizApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void test() {
		Response response=new Response();
		System.out.println("==============***************");
	}
	@Test
	public void test2() {
		List<Integer>need=new ArrayList<>(List.of(1,2));
		List<Integer>qIds=new ArrayList<>(List.of(1,3));
		System.out.println(need.contains(qIds));
		List<Integer>need1=new ArrayList<>(List.of(1,2));
		System.out.println(need.contains(need1));
	
	}
	
	@Test
	public void test3() {
		List<String> list=List.of("A","B","C","D","E");
		String str="AABBCCDDAABEDDBBABAEDEDED";
		//�p��ABCDE�U�X�{�F�X��
		
		//�C�Ӧr���ŧi�ss
	    for (String s : list) {
	        // �ϥ� replaceAll �p��r�� s �X�{������
	        int originalLength = str.length();
	        //�ϥ� str.replace(s, "") �N�r�Ŧꤤ�Ҧ����r�� s �������Ŧr��A�íp������᪺�s���� (newLength)�C
	        int newLength = str.replace(s, "").length();
	        //��l���״�h�s���ת��t���Y���Ӧr�����X�{����
	        int count = originalLength - newLength;
	        
	        // ���L���G
	        System.out.println(s + ": " + count+"��");
	    }
	    
	    
	    //----��k�G:�p��ABCDE�U�X�{�F�X��-------
	    System.out.println("=======================");
	    //   �r��   �X�{����
	    Map<String, Integer>map= new HashMap<String, Integer>();
	    //item�Olist���������A�]�N�O�r��
	    for (String item : list) {
	    	String newStr=str.replace(item, "");
	    	int count=str.length()-newStr.length();
	    	map.put(item, count);
	    }
	    
	    System.out.println(map+"��");
	    
	    
	}
		
		
	
}
