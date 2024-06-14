package com.example.quiz.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.OptionType;
import com.example.quiz.constants.ResMessage;
import com.example.quiz.entity.Quiz;
import com.example.quiz.entity.Response;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.repository.ResponseDao;
import com.example.quiz.service.ifs.FillinService;
import com.example.quiz.vo.BasicRes;
import com.example.quiz.vo.Feedback;
import com.example.quiz.vo.FeedbackDetail;
import com.example.quiz.vo.FeedbackReq;
import com.example.quiz.vo.FeedbackRes;
import com.example.quiz.vo.Fillin;
import com.example.quiz.vo.FillinReq;
import com.example.quiz.vo.Question;
import com.example.quiz.vo.StatisticsRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FillinServiceImpl implements FillinService {

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private ResponseDao responseDao;

////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public BasicRes fillin(FillinReq req) {
		// 參數檢查合不合法
		BasicRes checkResult = checkParams(req);
		if (checkResult != null) {
			return checkResult;
		}
		// 檢查同一個電話號碼是否有重複填寫同一張問卷
		if (responseDao.existsByQuizIdAndPhone(req.getQuizId(), req.getPhone())) {
			return new BasicRes(ResMessage.DUPLICATED_FILLIN.getCode(), //
					ResMessage.DUPLICATED_FILLIN.getMessage());
		}
		// 檢查 quiz_id 是否存在於DB中
		// 因為後續會比對 req 中的答案與題目的選項是否符合，所以要用 findById
		Optional<Quiz> op = quizDao.findById(req.getQuizId());
		if (op.isEmpty()) {
			return new BasicRes(ResMessage.QUIZ_NOT_FOUND.getCode(), ResMessage.QUIZ_NOT_FOUND.getMessage());
		}
		Quiz quiz = op.get();
		// 從 quiz 中取出 questions 字串
		String questionsStr = quiz.getQuestions();
		// 使用 ObjectMapper 將 questionsStr 轉成 List<Question>
		// fillinStr 要給空字串，不然預設會是 null
		// 若 fillinStr = null，後續執行 fillinStr
		// =mapper.writeValueAsString(req.getqIdAnswerMap());
		// 把執行得到的結果塞回給 fillinStr 時，會報錯

		String fillinStr = "";
		try {
			// 建立正確的List<Fillin>
			List<Fillin> newfillinList = new ArrayList<Fillin>();
			// 建立新增的quList_id list
			List<Integer> qIds = new ArrayList<>();
			List<Question> quList = mapper.readValue(questionsStr, new TypeReference<>() {
			});

			// 比對每一個 Question 與 fillin 中的答案
			for (Question item : quList) {// 題號1(必)2(必)3(選)
				List<Fillin> fillinList = req.getFillinList();
				for (Fillin fillin : fillinList) {// 回答題號1 2 3 4，4是假資料，因為原本的題目只有三題，4永遠檢查不到
					// id 不一致，跳過
					if (item.getId() != fillin.getqId()) {
						continue;
					}
					// 如果qIds已經有包含問題編號，表示已檢查過該題
					// 此段用來排除req中有重複的問題編號；如原本的題號是123，/如原本的題號是1(必)2(必)3(選)， 回答的是1233，有一個3重複了就需要排除
					if (qIds.contains(fillin.getqId())) {
						continue;
					}
//					//移除已填的必填id，後續可用來判斷是否必填的題目都有回答，如只有回答13略過2(必)的狀況
//					needQIds.remove(fillin.getqId());
					// 將已新增問題之題號加入
					qIds.add(fillin.getqId());
					// 新增相同題號的fillin
					// 不直接把fillin加到list的原因是:
					// 上面的程式碼只有對question_id和answer檢查，所以其餘的屬性內容可能是不合法的
					// 直接使用Question item的值次因為這些值都是從DB來，當初已有檢查過
					newfillinList.add(new Fillin(item.getId(), item.getTitle(), item.getOptions(), //
							fillin.getAnswer(), item.getType(), item.isNecessary()));// item是question，排序按照Fillin.java裡帶建構方法的排序
					// 檢查選項與答案
					checkResult = checkOptionAnSwer(item, fillin);
					if (checkResult != null) {
						return checkResult;
					}
				}
				// 正常情況是:問題是必填，然後又有回答，每跑完一個問題後，qIds就會包含該必填的問題id
				// 因此若問題是必填但qId又沒有包含該題的id，就表示沒有回答必填題
				if (item.isNecessary() && !qIds.contains(item.getId())) {
					return new BasicRes(ResMessage.ANSWER_IS_REQUIRED.getCode(),
							ResMessage.ANSWER_IS_REQUIRED.getMessage());
				}
			}
			fillinStr = mapper.writeValueAsString(newfillinList);
		} catch (JsonProcessingException e) {
			return new BasicRes(ResMessage.JSON_PROCESSING_EXCEPTION.getCode(),
					ResMessage.JSON_PROCESSING_EXCEPTION.getMessage());
		}
		responseDao.save(new Response(req.getQuizId(), req.getName(), req.getPhone(), req.getEmail(), //
				req.getAge(), fillinStr));
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////
	private BasicRes checkParams(FillinReq req) {
		if (req.getAge() <= 0) {
			return new BasicRes(ResMessage.PARAM_QUIZ_ID_ERROR.getCode(), //
					ResMessage.PARAM_QUIZ_ID_ERROR.getMessage());
		}
		// 檢查姓名、電話、郵件地址是否為空值或空字串
		if (!StringUtils.hasText(req.getName())) {
			return new BasicRes(ResMessage.PARAM_NAME_IS_REQUIRED.getCode(), //
					ResMessage.PARAM_NAME_IS_REQUIRED.getMessage());
		}
		if (!StringUtils.hasText(req.getPhone())) {
			return new BasicRes(ResMessage.PARAM_PHONE_IS_REQUIRED.getCode(), //
					ResMessage.PARAM_PHONE_IS_REQUIRED.getMessage());
		}
		if (!StringUtils.hasText(req.getEmail())) {
			return new BasicRes(ResMessage.PARAM_EMAIL_IS_REQUIRED.getCode(), //
					ResMessage.PARAM_EMAIL_IS_REQUIRED.getMessage());
		}
		// 設定12-99歲才可以填問卷
		if (req.getAge() < 12 || req.getAge() > 99) {
			return new BasicRes(ResMessage.PARAM_AGE_NOT_QUALIFIED.getCode(), //
					ResMessage.PARAM_AGE_NOT_QUALIFIED.getMessage());
		}

		return null;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////
	private BasicRes checkOptionAnSwer(Question item, Fillin fillin) {

		// 1. 檢查必填也要有答案
		// fillin 中的答案沒有值，返回錯誤
		if (item.isNecessary() && !StringUtils.hasText(fillin.getAnswer())) {
			return new BasicRes(ResMessage.ANSWER_IS_REQUIRED.getCode(), ResMessage.ANSWER_IS_REQUIRED.getMessage());
		}
		// 2. 排除題型是單選 但 answerArray 的長度 > 1
		String answerStr = fillin.getAnswer();
		// 把 answerStr(答案) 用分號切割成陣列
		String[] answerArray = answerStr.split(";");
		if (item.getType().equalsIgnoreCase(OptionType.SINGLE_CHOICE.getType()) //
				&& answerArray.length > 1) {
			return new BasicRes(ResMessage.ANSWER_OPTION_TYPE_IS_NOT_MATCH.getCode(),
					ResMessage.ANSWER_OPTION_TYPE_IS_NOT_MATCH.getMessage());
		}
		// 3. 排除簡答題；option type 是 text(因為接下來要檢查選項與答案是否一致)
		if (item.getType().equalsIgnoreCase(OptionType.TEXT.getType())) {
			return null;
		}
		// 把 options 切成 Array
		String[] optionArray = item.getOptions().split(";");
		// 把 optionArray 轉成 List，因為要使用 List 中的 contains 方法
		List<String> optionList = List.of(optionArray);
		// 4. 檢查答案跟選項一致
		for (String str : answerArray) {
			// 假設 item.getOptions() 的值是: "AB;BC;C;D"
			// 轉成 List 後的 optionList = ["AB", "BC", "C", "D"]
			// 假設 answerArray = [AB, B]
			// for 迴圈中就是把 AB 和 B 比對是否被包含在 optionList 中
			// List 的 contains 方法是比較元素，所以範例中，AB是有包含，B是沒有
			// 排除以下:
			// 1. 必填 && 答案選項不一致
			if (item.isNecessary() && !optionList.contains(str)) {
				return new BasicRes(ResMessage.ANSWER_OPTION_IS_NOT_MATCH.getCode(),
						ResMessage.ANSWER_OPTION_IS_NOT_MATCH.getMessage());
			}
			// 2. 非必填 && 有答案 && 答案選項不一致
			if (!item.isNecessary() && StringUtils.hasText(str) && !optionList.contains(str)) {
				return new BasicRes(ResMessage.ANSWER_OPTION_IS_NOT_MATCH.getCode(),
						ResMessage.ANSWER_OPTION_IS_NOT_MATCH.getMessage());
			}
		}

		return null;
	}

////////////////////////////////////////////////////////////////
	@Override
	public FeedbackRes feedBack(FeedbackReq req) {
		Optional<Quiz> op = quizDao.findById(req.getQuizId());
		if (op.isEmpty()) {
			return new FeedbackRes(ResMessage.QUIZ_NOT_FOUND.getCode(), //
					ResMessage.QUIZ_NOT_FOUND.getMessage());
		}
		Quiz quiz = op.get();
		List<Feedback> feedbackList = new ArrayList<>();
		try {
			List<Response> resList = responseDao.findByQuizId(req.getQuizId());

			// 遍歷resList
			for (Response resItem : resList) {
				List<Fillin> fillinList = mapper.readValue(resItem.getFillin(), new TypeReference<>() {
				});
				FeedbackDetail detail = new FeedbackDetail(quiz.getName(), quiz.getDescription(), //
						quiz.getStartDate(), quiz.getEndDate(), resItem.getName(), resItem.getPhone(), //
						resItem.getEmail(), resItem.getAge(), fillinList);// 順序在FillbackDetail帶參數的建構方法
				Feedback feedback = new Feedback(resItem.getName(), resItem.getFillinDateTime(), detail);
				feedbackList.add(feedback);
			}
		} catch (Exception e) {
			return new FeedbackRes(ResMessage.JSON_PROCESSING_EXCEPTION.getCode(),
					ResMessage.JSON_PROCESSING_EXCEPTION.getMessage());
		}
		return new FeedbackRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), feedbackList);// 呈現完整檢視清單feedbackList
	}

	@Override
	public StatisticsRes statistics(FeedbackReq req) {
		List<Response> reponseList = responseDao.findByQuizId(req.getQuizId());
		//計算所有問卷回答的 "題號 選項 次數"
		// qId題號       選項      次數
		Map<Integer, Map<String, Integer>> countMap = new HashMap<>();   //Map特性相同的key會後蓋前
		
		// 抓取Response的fillin
		// 第一個for先抓取SQL中所有的回答，這些回答可能會有N筆資料，這N筆資料是一個reponseList
		for (Response item : reponseList) {
			String fillinStr = item.getFillin();
			try {
				// 將fillin字串轉成List
				List<Fillin> fillinList = mapper.readValue(fillinStr, new TypeReference<>() {
				});
				
				// 取出fillin中的選項和答案，一個fillin就是一張問卷回答，就是SQL中的一筆資料
				// 第二個for先取出其中一張問卷回答，也就是列表裡的其中一筆資料(N筆資料取出1筆)
				for (Fillin fillin : fillinList) {
					Map<String, Integer> optionCountMap = new HashMap<>();
					//排除簡答題:不列入統計
					if(fillin.getType().equalsIgnoreCase(OptionType.TEXT.getType())) {
						continue;
					};
					// 每個選項之間是用(;)串接//如:[;綠茶;烏龍茶;]，前後都要加分號，不然[綠茶;綠茶拿鐵]用綠茶搜會出現兩筆，不能精準搜尋
					String optionStr = fillin.getOptions();
					String[] optionArray = optionStr.split(";");
					String answer = fillin.getAnswer();
					answer = ";" + answer + ";";// 理由同下
					
					/// 第三個for先取出問卷選項(某1筆(某1張問卷)資料中的回答選項)
					for (String option : optionArray) {//[綠茶;綠茶拿鐵;梅子綠茶]
						// 比對答案中每個選項出現的次數
						// 避免某個選項是另一個選項的其中一部份
						// 例如[綠茶;綠茶拿鐵;梅子綠茶]都是選項，要找出"綠茶"出現次數，綠茶拿鐵和梅子綠茶都不能算
						// 所以需要在每個選項""前後""加上分號，會用分號是因為答案的串接使用分號
						// 後續要找出次數時就會是用;綠茶;來找
						String newOption = ";" + option + ";";
						// 用空白將回答的選項取代，可以計算出減少的長度
						String newAnswerStr = answer.replace(newOption, "");
						// 計算該選項出現的次數
						// [;綠茶;綠茶拿鐵;梅子綠茶;]-[綠茶拿鐵;梅子綠茶;]/[;綠茶;] =1
						// 原本答案的長度字串-被取代後的答案字串長度，要除以答案選項的長度才是真正的次數， 但因為每個選項只能選一次，所以這邊算出來只會有0跟1
						int count = (answer.length() - newAnswerStr.length()) / newOption.length();
						// 記錄每一題的統計,取問題題號、問題、必填、選項和次數
						//這行是防止該題選項受到其他題的選項汙染
						optionCountMap=countMap.getOrDefault(fillin.getqId(), optionCountMap);
						//先取出選項(key)對應的次數
						//getOrDefault(option, 0):map中沒有key的話，就會返回0，用在第一次，因為第一次啥都沒有
						int oldCount=optionCountMap.getOrDefault(option, 0);
						//這邊是累加:累加oldCount+count
						optionCountMap.put(option,oldCount+count);
						//把有累加次數的optionCountMap覆蓋回countMap中(相同的題號)
						countMap.put(fillin.getqId(), optionCountMap);


					}

				}

			} catch (JsonProcessingException e) {
				return new StatisticsRes(ResMessage.JSON_PROCESSING_EXCEPTION.getCode(),
						ResMessage.JSON_PROCESSING_EXCEPTION.getMessage());

			}

		}
		Optional<Quiz> op=quizDao.findById(req.getQuizId());
		
		if(op.isEmpty()) {
			return  new StatisticsRes(ResMessage.QUIZ_NOT_FOUND.getCode(),
					ResMessage.QUIZ_NOT_FOUND.getMessage());
		}
		
		Quiz quiz=op.get();
		return  new StatisticsRes(ResMessage.SUCCESS.getCode(),ResMessage.SUCCESS.getMessage(),//
				quiz.getName(),quiz.getStartDate(),quiz.getEndDate(),countMap);//排序在StatisticsRes裡帶參數的的建構方法裡
		
	}

}
