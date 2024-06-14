package com.example.quiz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.quiz.constants.OptionType;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.BasicRes;
import com.example.quiz.vo.CreateOrUpdateReq;
import com.example.quiz.vo.Question;

@SpringBootTest

public class QuizServiceTests {
	@Autowired
	private QuizService quizService;

	@Autowired
	private QuizDao quizDao;

	@Test

	public void createTest() {
		List<Question> questionList = new ArrayList<>();
		///////////////////////////// ID 題目 選項 type
		questionList.add(new Question(1, "健康餐吃啥?", "松阪豬;炸豬排;煎魚;必勝客"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		questionList.add(new Question(2, "丹丹吃啥?", "1號餐;2號餐;3號餐;4號餐"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		questionList.add(new Question(3, "炒飯吃啥?", "豬肉炒飯;海鮮炒飯;干貝馬鈴薯(推);綜合炒飯"//
				, OptionType.SINGLE_CHOICE.getType(), true));
//去參考CreateReq帶參數的建構方法的排序     name    description 開始時間(記得要比系統的時間晚至少一天，不然跑不出來)結束時間
		CreateOrUpdateReq req = new CreateOrUpdateReq("午餐吃啥?", "午餐吃啥?", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30), //
				questionList, true);//
		// 呼叫方法完後，測試邏輯
		BasicRes res = quizService.createOrUpdate(req);
		// 括號內字串代表當結果是false時回的文字
		Assert.isTrue(res.getStatusCode() == 200, "create test false!!");
	}

	// 刪除測試資料 TODO

	@Test
	// createNameTest的Name指的是CreateReq的name
	public void createNameTest() {
		List<Question> questionList = new ArrayList<>();
		///////////////////////////// ID 題目 選項 type
		questionList.add(new Question(1, "健康餐吃啥?", "松阪豬;炸豬排;煎魚;必勝客"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		questionList.add(new Question(2, "丹丹吃啥?", "1號餐;2號餐;3號餐;4號餐"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		questionList.add(new Question(3, "炒飯吃啥?", "豬肉炒飯;海鮮炒飯;干貝馬鈴薯(推);綜合炒飯"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		CreateOrUpdateReq req = new CreateOrUpdateReq("", "午餐吃啥?", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30), //
				questionList, true);//
		// 呼叫方法完後，測試邏輯
		BasicRes res = quizService.createOrUpdate(req);
		// 括號內字串代表當結果是false時回的文字
		Assert.isTrue(res.getMessage().equalsIgnoreCase("Param name error!!"), "create test false!!");
	}

	@Test
	// createStartDateTest的Name指的是CreateReq裡的StartDate
	public void createStartDateTest() {
		List<Question> questionList = new ArrayList<>();
		// 去參考CreateReq帶參數的建構方法的排序 name description 開始時間(記得要比系統的時間晚至少一天，不然跑不出來)結束時間
		///////////////////////////// ID 題目 選項 type
		questionList.add(new Question(1, "健康餐吃啥?", "松阪豬;炸豬排;煎魚;必勝客"//
				, OptionType.SINGLE_CHOICE.getType(), true));

		questionList.add(new Question(2, "丹丹吃啥?", "1號餐;2號餐;3號餐;4號餐"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		questionList.add(new Question(3, "炒飯吃啥?", "豬肉炒飯;海鮮炒飯;干貝馬鈴薯(推);綜合炒飯"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		// 今天是2024/5/30，所以開始日期不能是當天或之前
		CreateOrUpdateReq req = new CreateOrUpdateReq("午餐吃啥?", "午餐吃啥?", LocalDate.of(2024, 5, 30), LocalDate.of(2024, 6, 30), //
				questionList, true);//
		// 呼叫方法完後，測試邏輯
		BasicRes res = quizService.createOrUpdate(req);
		// 括號內字串代表當結果是false時回的文字
		Assert.isTrue(res.getMessage().equalsIgnoreCase("Start date error!!"), "create test false!!");

	}

	@Test
	public void createTest1() {
		List<Question> questionList = new ArrayList<>();
		questionList.add(new Question(1, "健康餐吃啥?", "松阪豬;炸豬排;煎魚;必勝客"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		// 去參考CreateReq帶參數的建構方法的排序 name description 開始時間(記得要比系統的時間晚至少一天，不然跑不出來)結束時間
		// 測試name error
		CreateOrUpdateReq req = new CreateOrUpdateReq("", "午餐吃啥?", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30), //
				questionList, true);//
		// 呼叫方法完後，測試邏輯
		BasicRes res = quizService.createOrUpdate(req);
		// 括號內字串代表當結果是false時回的文字
		//res.getMessage().equalsIgnoreCase("Param name error!!")這裡是布林值，為true
		Assert.isTrue(res.getMessage().equalsIgnoreCase("Param name error!!"), "create test false!!");
		
		// 測試start date error:假設今天是2024/5/30所以開始日期不能是當天或之前
		req = new CreateOrUpdateReq("午餐吃啥?", "午餐吃啥?", LocalDate.of(2024, 5, 30), LocalDate.of(2024, 6, 1), //
				questionList, true);//
		res = quizService.createOrUpdate(req);
		Assert.isTrue(res.getMessage().equalsIgnoreCase("Start date error!!"), "create test false!!");
		// 測試end date error:結束時間不能比開始時間早
		req = new CreateOrUpdateReq("午餐吃啥?", "午餐吃啥?", LocalDate.of(2024, 5,31 ), LocalDate.of(2024, 5, 5), //
				questionList, true);//
		res = quizService.createOrUpdate(req);
		Assert.isTrue(res.getMessage().equalsIgnoreCase("End date error!!"), "create test false!!");
		// 剩餘邏輯全部判斷完之後，最後才會是測試成功的情境
		req = new CreateOrUpdateReq("午餐吃啥?", "午餐吃啥?", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 1), //
				questionList, true);//
		res = quizService.createOrUpdate(req);
		Assert.isTrue(res.getStatusCode()==200, "create test false!!");
		
	}
	@Test
	public void createOrUpdateTest(){
		List<Question> questionList = new ArrayList<>();
		questionList.add(new Question(1, "健康餐吃啥?", "松阪豬;炸豬排;煎魚;必勝客"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		// 去參考CreateReq帶參數的建構方法的排序 name description 開始時間(記得要比系統的時間晚至少一天，不然跑不出來)結束時間
		// 測試name error
		CreateOrUpdateReq req = new CreateOrUpdateReq("", "午餐吃啥?", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30), //
				questionList, true);//
		
	}
	
	/////
	
	@Test
	public void gitUpdateTest(){
		List<Question> questionList = new ArrayList<>();
		questionList.add(new Question(1, "健康餐吃啥?", "松阪豬;炸豬排;煎魚;必勝客"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		// 去參考CreateReq帶參數的建構方法的排序 name description 開始時間(記得要比系統的時間晚至少一天，不然跑不出來)結束時間
		// 測試name error
		CreateOrUpdateReq req = new CreateOrUpdateReq("", "午餐吃啥?", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30), //
				questionList, true);
		System.out.println("=============");
		
	}
	
}
