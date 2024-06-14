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
		//計算ABCDE各出現了幾次
		
		//每個字母宣告叫s
	    for (String s : list) {
	        // 使用 replaceAll 計算字母 s 出現的次數
	        int originalLength = str.length();
	        //使用 str.replace(s, "") 將字符串中所有的字母 s 替換為空字串，並計算替換後的新長度 (newLength)。
	        int newLength = str.replace(s, "").length();
	        //原始長度減去新長度的差異即為該字母的出現次數
	        int count = originalLength - newLength;
	        
	        // 打印結果
	        System.out.println(s + ": " + count+"次");
	    }
	    
	    
	    //----方法二:計算ABCDE各出現了幾次-------
	    System.out.println("=======================");
	    //   字母   出現次數
	    Map<String, Integer>map= new HashMap<String, Integer>();
	    //item是list中的元素，也就是字母
	    for (String item : list) {
	    	String newStr=str.replace(item, "");
	    	int count=str.length()-newStr.length();
	    	map.put(item, count);
	    }
	    
	    System.out.println(map+"次");
	    
	    
	}
		
		
	
}
