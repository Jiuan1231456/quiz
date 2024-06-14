package com.example.quiz.service.ifs;
import com.example.quiz.vo.BasicRes;
import com.example.quiz.vo.CreateOrUpdateReq;
import com.example.quiz.vo.DeleteReq;
import com.example.quiz.vo.FillinReq;
import com.example.quiz.vo.SearchReq;
import com.example.quiz.vo.SearchRes;

public interface QuizService {
	public BasicRes createOrUpdate(CreateOrUpdateReq req);
	
	public SearchRes search(SearchReq req);
	
	//刪除資料搜尋ID就，因為有下PK，ID會使每筆資料都有其獨特性/唯一性
	public BasicRes delete(DeleteReq req);

	//public BasicRes fillin(FillinReq req);
	
	


}

