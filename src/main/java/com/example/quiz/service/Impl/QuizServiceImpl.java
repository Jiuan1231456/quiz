package com.example.quiz.service.Impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.OptionType;
import com.example.quiz.constants.ResMessage;
import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.BasicRes;
import com.example.quiz.vo.CreateOrUpdateReq;
import com.example.quiz.vo.DeleteReq;
import com.example.quiz.vo.FillinReq;
import com.example.quiz.vo.Question;
import com.example.quiz.vo.SearchReq;
import com.example.quiz.vo.SearchRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service

public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizDao quizDao;

	// 新增或更新問卷
	@Override
	public BasicRes createOrUpdate(CreateOrUpdateReq req) {
		// 檢查參數
		BasicRes checkResult = checkParams(req);
		// checkResult==null時表示參數檢查都正確
		if (checkResult != null) {
			return checkResult;
		}

		// ===============================
//		if (checkResult.getStatusCode() != 200) {
//
//		}
		// 因為Quiz中questions的資料格式是String,所以要將req的List<Question>轉成String
		// ObjectMapper可以把物件(類別)轉成JSON格式的字串
		// 紅蚯蚓選try catch
		ObjectMapper mapper = new ObjectMapper();
		try {
			// 原本是List<Question>questionList(在CreateOrUpdateReq的檔案裡)，現在要把這張LIST轉成JSON格式的字串
			String questionStr = mapper.writeValueAsString(req.getQuestionList());

			// 若req中的id>0則表示更新已存在的資料;，若id=0則表示新增
			// 這邊表示先檢查外部進來的ID資料，在資料庫中有沒有存在
			if (req.getId() > 0) {
				// 以下兩種方式擇一
				// 使用方法一,透過findById，若有資料就會回傳一整筆的資料(可能回傳的資料量較大)
				// 使用方法二，是因為透過exsitsById來判斷資料是否存在，所以回傳的資料永遠都只會是一個bit(0或1)
				// 方法一:透過findById若有資料回傳整筆資料
				// 將req中心的值設定到舊的quiz中(上面第64行的)，不設定id，因為id一樣(id是唯一值，不能變)。=>用findById
//				Optional<Quiz> op = quizDao.findById(req.getId());
//				//判斷資料是否有誤
//				if(op.isEmpty()){//op.isEmpty():表示沒資料
//					return new BasicRes(ResMessage.UPDATE_ID_NOT_FOUND.getCode(), //
//							ResMessage.UPDATE_ID_NOT_FOUND.getMessage());
//				}
//				Quiz quiz=op.get();
//				//設定新值(值從req中來)
//				
//				quiz.setName(req.getName());
//				quiz.setDescription(req.getDescription());
//				quiz.setStartDate(req.getStartDate());
//				quiz.setEndDate(req.getEndDate());
//				quiz.setQuestions(questionStr);
//				quiz.setPublished(req.isPublished());
				// 方法二:透過exsitsById回傳一個bit的值，檢查資料庫中是否存在指定的 Quiz 物件。若不存在則回傳UPDATE_ID_NOT_FOUND
				// 確認ID存在於資料庫
				// 這邊要判斷從req帶進來的id是否真的有存在於DB之中
				// 因為若id不存在，又不檢查，後續程式碼在呼叫JPA的save方法時，會變成新增
				// boolean boo = quizDao.existsById(req.getId());
				// 若ID不存在=0，則回傳UPDATE_ID_NOT_FOUND並跳出去if判斷式，執行quiz.setId(req.getId())，新增這筆新資料
				// =====改用匿名類別=====
				if (!quizDao.existsById(req.getId())) { // !boo 表示資料不存在，可能是被刪掉的 id
					return new BasicRes(ResMessage.UPDATE_ID_NOT_FOUND.getCode(), //
							ResMessage.UPDATE_ID_NOT_FOUND.getMessage());
				}
				// 要放id，把他+進去新增這筆資料
				// quiz.setId(req.getId());
				// 因為是new一個新的Quiz所以要放id，不然新的Quiz裡，新增/更新就沒有id=>用exsitsById,回傳資料量少，耗能小
//				Quiz quiz = new Quiz(req.getId(), req.getName(), req.getDescription(), req.getStartDate(), //
//						req.getEndDate(), questionStr, req.isPublished());
			}
			// ===============================
			// 上述一整段 if 程式碼可以縮減成以下這段
			// 表示若有外部的ID資料進來，且該ID不存在DB中，則繼續執行下面返回ID不存在的訊息，然後在執行下面的save做新增；若本來就存在於DB中，則直接到下面的save作更新
//			if (req.getId() > 0 && !quizDao.existsById(req.getId())) {
//				return new BasicRes(ResMessage.UPDATE_ID_NOT_FOUND.getCode(),
//						ResMessage.UPDATE_ID_NOT_FOUND.getMessage());
//			}
			// ===============================

//			Quiz quiz = new Quiz(req.getName(),req.getDescription(),req.getStartDate(),//
//					req.getEndDate(),questionStr,req.isPublished());
//			quizDao.save(quiz);//是第50行的quiz
			// 因為變數quiz只用一次，因此可以使用匿名類別方式撰寫(不需要變數接)
			// new Quiz()中帶入req.getId()是PK，在呼叫save時，會先去檢查PK是否存在於DB之中，
			// 若存在則更新，不存在則新增
			// req中沒有該欄位時，預設是0，因為id的資料型態是int
			quizDao.save(new Quiz(req.getId(), req.getName(), req.getDescription(), req.getStartDate(),
					req.getEndDate(), questionStr, req.isPublished()));

		} catch (JsonProcessingException e) { // e表異常，這邊要排除異常

//			e.printStackTrace();
			return new BasicRes(ResMessage.JSON_PROCESSING_EXCEPTION.getCode(), //
					ResMessage.JSON_PROCESSING_EXCEPTION.getMessage());
		}

		// return null;
		return new BasicRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage());
	}

	private BasicRes checkParams(CreateOrUpdateReq req) {
		// 檢查問卷參數
		// StringUtils.hasText():檢查字串，是否有全空白字串、空值null、空字串，若是符合3種其中一項則會回傳false
		// 前面加!表示反向意思，若字串的檢查結果是false的話，就會進到if的實作區塊
		// !StringUtils.hasText(req.getName())等同於StringUtils.hasText(req.getName())==false
		// 有驚嘆號 沒驚嘆號
		if (!StringUtils.hasText(req.getName())) {
			return new BasicRes(ResMessage.PARAM_QUIZ_NAME_ERROR.getCode(), //
					ResMessage.PARAM_QUIZ_NAME_ERROR.getMessage());
		}
		if (!StringUtils.hasText(req.getDescription())) {
			return new BasicRes(ResMessage.PARAM_DESCRIPTION_ERROR.getCode(), //
					ResMessage.PARAM_DESCRIPTION_ERROR.getMessage());
		}

		// .isBefore()是真的只有今天之前，不包含今天，反之isAfter也是
		// LocalDate.now(): 取得系統當前時間
		// req.getStartDate().isBefore(LocalDate.now()): 若 req 中的開始時間"早"於當前時間，會得到 true
		// req.getStartDate().isEqual(LocalDate.now()): 若 req 中的開始時間"等"於當前時間，會得到 true
		// 因為開始時間不能在今天(含)之前，所以上兩個比較若是任一個結果為 true，則表示開始時間要比當前(含)時間早
		// req.getStartDate().isAfter(LocalDate.now()): 若 req 中的開始時間比當前時間晚，會得到 true
		// !req.getStartDate().isAfter(LocalDate.now()): 前面有加驚嘆號，表示會得到相反的結果，就是開始時間
		// 會等於小於當前時間
		// 1.開始時間不能晚於等於當前時間
		// 2.開始時間一定在當前時間之後
		if (req.getStartDate() == null || !req.getStartDate().isAfter(LocalDate.now())) {
			return new BasicRes(ResMessage.PARAM_START_DATE_ERROR.getCode(), //
					ResMessage.PARAM_START_DATE_ERROR.getMessage());
		}
		// 開始時間不能小於等於當前時間
		// LocalDate.now(): 取得系統當前時間
		// req.getStartDate().isAfter(LocalDate.now()): 若 req 中的開始時間比當前時間晚，會得到 true
		// !req.getStartDate().isAfter(LocalDate.now()): 前面有加驚嘆號，表示會得到相反的結果，就是開始時間
		// 會等於小於當前時間
		// LocalDate.now().isAfter(req.getStartDate()) =
		// !req.getStartDate().isAfter(LocalDate.now())
		// 程式碼執行到這行時，表示開始時間一定晚於等於當前時間
		// 所以後續檢查結束時間時，只要確定結束時間是晚於等於開始時間即可，因為只要結束時間是晚於等於開始時間，就一定會晚於等於當前時間
		// 時間邏輯順序:當前時間<=開始時間<=結束時間，所以結束時間不需要判斷!req.getEndDate().isAfter(LocalDate.now())
		// 1.結束時間不能小於(早)等於當前時間
		// 2.結束時間不能小於(早)開始時間
		if (req.getEndDate() == null || req.getEndDate().isBefore(req.getStartDate())) {//
			return new BasicRes(ResMessage.PARAM_END_DATE_ERROR.getCode(), //
					ResMessage.PARAM_END_DATE_ERROR.getMessage());
		}
		// 檢查問題參數
		if (CollectionUtils.isEmpty(req.getQuestionList())) {
			return new BasicRes(ResMessage.PARAM_QUESTION_List_NOT_FOUND.getCode(), //
					ResMessage.PARAM_QUESTION_List_NOT_FOUND.getMessage());

		}
		// 因為題目可能是多題所以要用迴圈檢查

		for (Question item : req.getQuestionList()) {
			if (item.getId() <= 0) {
				return new BasicRes(ResMessage.PARAM_QUESTION_ID_ERROR.getCode(), //
						ResMessage.PARAM_QUESTION_ID_ERROR.getMessage());
			}
			if (!StringUtils.hasText(item.getTitle())) {
				return new BasicRes(ResMessage.PARAM_TITLE_ERROR.getCode(), //
						ResMessage.PARAM_TITLE_ERROR.getMessage());
			}

			if (!StringUtils.hasText(item.getType())) {
				return new BasicRes(ResMessage.PARAM_TYPE_ERROR.getCode(), //
						ResMessage.PARAM_TYPE_ERROR.getMessage());
			}
			// .equalsIgnoreCase():字串比較，不分大小寫
			// 當option_type 是單選或多選時，options就不能是空字串
			// 但option_type是文字時，options允許是空字串
			// 以下條件檢查:當option_type是單選或多選時，且options是空字串，返回錯誤
			if (item.getType().equalsIgnoreCase(OptionType.SINGLE_CHOICE.getType())
					|| item.getType().equalsIgnoreCase(OptionType.MULTI_CHOICE.getType())) {
				if (!StringUtils.hasText(item.getOptions())) {
					return new BasicRes(ResMessage.PARAM_OPTIONS_ERROR.getCode(), //
							ResMessage.PARAM_OPTIONS_ERROR.getMessage());
				}
			}
			// 以下是上述兩個if合併的寫法:((條件1||條件2)&&條件3)
			// 第一個if條件式&&第二個if條件式
//			if((item.getType().equalsIgnoreCase(OptionType.SINGLE_CHOICE.getType())
//					|| item.getType().equalsIgnoreCase(OptionType.MULTI_CHOICE.getType()))
//					&&!StringUtils.hasText(item.getOptions())) {
//				return new BasicRes(ResMessage.PARAM_OPTIONS_ERROR.getCode(), //
//						ResMessage.PARAM_OPTIONS_ERROR.getMessage());
//			}
		}

		return null;// 這邊也可以呼叫200成功

	}

	@Override
	public SearchRes search(SearchReq req) {
		String name = req.getName();
		LocalDate start = req.getStartDate();
		LocalDate end = req.getEndDate();
		// 假設name是null或是全空白字串，需要把name變成空字串，可以視為沒有輸入條件值，就表示要取得全部
		// JPA的containing方法,條件值是空字串時，會搜尋全部
		// 所以要把name的值是null或全空白字串時，轉換成空字串
		// 若req裡的name有值就不會跑進if，但如果是null或空字串就會跑下面的if
		if (!StringUtils.hasText(name)) {
			name = "";
		}
		if (start == null) {
			start = LocalDate.of(1970, 1, 1);
		}
		if (end == null) {
			end = LocalDate.of(2999, 1, 1);
		}
		List<Quiz> quizList = quizDao.findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(name,
				start, end);
//		List<Quiz> res=quizDao.findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(name,
//			    start, end);
//		return new SearchRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(),res);

		return new SearchRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), quizList);
	}

	@Override
	public BasicRes delete(DeleteReq req) {
		// 檢查參數是否正確
		// 檢查List因為是集合，要用collection
		// 檢查 List 是否為空：因為 req.getIdList() 是一個集合（List），所以使用 CollectionUtils.isEmpty()
		// 方法來檢查它是否為空。
		// 刪除問卷：如果集合不為空，則呼叫 quizDao.deleteAllById(req.getIdList()) 方法，刪除集合中的所有 ID 對應的問卷。
		// 返回結果：刪除操作完成後，返回一個表示成功的響應。
		// 先檢查List裡要刪除的資料是否存在，若不為空(即存在)，則執行刪除問卷
		if (!CollectionUtils.isEmpty(req.getIdList())) {
			// 刪除問卷
			try {
				quizDao.deleteAllById(req.getIdList());
			} catch (Exception e) {
				// 當 deleteAllById 方法中，id 的值不存在時，JPA 會報錯
				// 因為在刪除之前，JPA 會先搜尋帶入的 id 值，若沒結果就會報錯
				// 由於實際上沒刪除任何資料，因此就不需要對這個 Exception 作處理
			}
		}
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

//	@Override
//	public BasicRes fillin(FillinReq req) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
