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
		// �Ѽ��ˬd�X���X�k
		BasicRes checkResult = checkParams(req);
		if (checkResult != null) {
			return checkResult;
		}
		// �ˬd�P�@�ӹq�ܸ��X�O�_�����ƶ�g�P�@�i�ݨ�
		if (responseDao.existsByQuizIdAndPhone(req.getQuizId(), req.getPhone())) {
			return new BasicRes(ResMessage.DUPLICATED_FILLIN.getCode(), //
					ResMessage.DUPLICATED_FILLIN.getMessage());
		}
		// �ˬd quiz_id �O�_�s�b��DB��
		// �]������|��� req �������׻P�D�ت��ﶵ�O�_�ŦX�A�ҥH�n�� findById
		Optional<Quiz> op = quizDao.findById(req.getQuizId());
		if (op.isEmpty()) {
			return new BasicRes(ResMessage.QUIZ_NOT_FOUND.getCode(), ResMessage.QUIZ_NOT_FOUND.getMessage());
		}
		Quiz quiz = op.get();
		// �q quiz �����X questions �r��
		String questionsStr = quiz.getQuestions();
		// �ϥ� ObjectMapper �N questionsStr �ন List<Question>
		// fillinStr �n���Ŧr��A���M�w�]�|�O null
		// �Y fillinStr = null�A������� fillinStr
		// =mapper.writeValueAsString(req.getqIdAnswerMap());
		// �����o�쪺���G��^�� fillinStr �ɡA�|����

		String fillinStr = "";
		try {
			// �إߥ��T��List<Fillin>
			List<Fillin> newfillinList = new ArrayList<Fillin>();
			// �إ߷s�W��quList_id list
			List<Integer> qIds = new ArrayList<>();
			List<Question> quList = mapper.readValue(questionsStr, new TypeReference<>() {
			});

			// ���C�@�� Question �P fillin ��������
			for (Question item : quList) {// �D��1(��)2(��)3(��)
				List<Fillin> fillinList = req.getFillinList();
				for (Fillin fillin : fillinList) {// �^���D��1 2 3 4�A4�O����ơA�]���쥻���D�إu���T�D�A4�û��ˬd����
					// id ���@�P�A���L
					if (item.getId() != fillin.getqId()) {
						continue;
					}
					// �p�GqIds�w�g���]�t���D�s���A��ܤw�ˬd�L���D
					// ���q�Ψӱư�req�������ƪ����D�s���F�p�쥻���D���O123�A/�p�쥻���D���O1(��)2(��)3(��)�A �^�����O1233�A���@��3���ƤF�N�ݭn�ư�
					if (qIds.contains(fillin.getqId())) {
						continue;
					}
//					//�����w�񪺥���id�A����i�ΨӧP�_�O�_�����D�س����^���A�p�u���^��13���L2(��)�����p
//					needQIds.remove(fillin.getqId());
					// �N�w�s�W���D���D���[�J
					qIds.add(fillin.getqId());
					// �s�W�ۦP�D����fillin
					// ��������fillin�[��list����]�O:
					// �W�����{���X�u����question_id�Manswer�ˬd�A�ҥH��l���ݩʤ��e�i��O���X�k��
					// �����ϥ�Question item���Ȧ��]���o�ǭȳ��O�qDB�ӡA���w���ˬd�L
					newfillinList.add(new Fillin(item.getId(), item.getTitle(), item.getOptions(), //
							fillin.getAnswer(), item.getType(), item.isNecessary()));// item�Oquestion�A�Ƨǫ���Fillin.java�̱a�غc��k���Ƨ�
					// �ˬd�ﶵ�P����
					checkResult = checkOptionAnSwer(item, fillin);
					if (checkResult != null) {
						return checkResult;
					}
				}
				// ���`���p�O:���D�O����A�M��S���^���A�C�]���@�Ӱ��D��AqIds�N�|�]�t�ӥ��񪺰��Did
				// �]���Y���D�O�����qId�S�S���]�t���D��id�A�N��ܨS���^�������D
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
		// �ˬd�m�W�B�q�ܡB�l��a�}�O�_���ŭȩΪŦr��
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
		// �]�w12-99���~�i�H��ݨ�
		if (req.getAge() < 12 || req.getAge() > 99) {
			return new BasicRes(ResMessage.PARAM_AGE_NOT_QUALIFIED.getCode(), //
					ResMessage.PARAM_AGE_NOT_QUALIFIED.getMessage());
		}

		return null;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////
	private BasicRes checkOptionAnSwer(Question item, Fillin fillin) {

		// 1. �ˬd����]�n������
		// fillin �������רS���ȡA��^���~
		if (item.isNecessary() && !StringUtils.hasText(fillin.getAnswer())) {
			return new BasicRes(ResMessage.ANSWER_IS_REQUIRED.getCode(), ResMessage.ANSWER_IS_REQUIRED.getMessage());
		}
		// 2. �ư��D���O��� �� answerArray ������ > 1
		String answerStr = fillin.getAnswer();
		// �� answerStr(����) �Τ������Φ��}�C
		String[] answerArray = answerStr.split(";");
		if (item.getType().equalsIgnoreCase(OptionType.SINGLE_CHOICE.getType()) //
				&& answerArray.length > 1) {
			return new BasicRes(ResMessage.ANSWER_OPTION_TYPE_IS_NOT_MATCH.getCode(),
					ResMessage.ANSWER_OPTION_TYPE_IS_NOT_MATCH.getMessage());
		}
		// 3. �ư�²���D�Foption type �O text(�]�����U�ӭn�ˬd�ﶵ�P���׬O�_�@�P)
		if (item.getType().equalsIgnoreCase(OptionType.TEXT.getType())) {
			return null;
		}
		// �� options ���� Array
		String[] optionArray = item.getOptions().split(";");
		// �� optionArray �ন List�A�]���n�ϥ� List ���� contains ��k
		List<String> optionList = List.of(optionArray);
		// 4. �ˬd���׸�ﶵ�@�P
		for (String str : answerArray) {
			// ���] item.getOptions() ���ȬO: "AB;BC;C;D"
			// �ন List �᪺ optionList = ["AB", "BC", "C", "D"]
			// ���] answerArray = [AB, B]
			// for �j�餤�N�O�� AB �M B ���O�_�Q�]�t�b optionList ��
			// List �� contains ��k�O��������A�ҥH�d�Ҥ��AAB�O���]�t�AB�O�S��
			// �ư��H�U:
			// 1. ���� && ���׿ﶵ���@�P
			if (item.isNecessary() && !optionList.contains(str)) {
				return new BasicRes(ResMessage.ANSWER_OPTION_IS_NOT_MATCH.getCode(),
						ResMessage.ANSWER_OPTION_IS_NOT_MATCH.getMessage());
			}
			// 2. �D���� && ������ && ���׿ﶵ���@�P
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

			// �M��resList
			for (Response resItem : resList) {
				List<Fillin> fillinList = mapper.readValue(resItem.getFillin(), new TypeReference<>() {
				});
				FeedbackDetail detail = new FeedbackDetail(quiz.getName(), quiz.getDescription(), //
						quiz.getStartDate(), quiz.getEndDate(), resItem.getName(), resItem.getPhone(), //
						resItem.getEmail(), resItem.getAge(), fillinList);// ���ǦbFillbackDetail�a�Ѽƪ��غc��k
				Feedback feedback = new Feedback(resItem.getName(), resItem.getFillinDateTime(), detail);
				feedbackList.add(feedback);
			}
		} catch (Exception e) {
			return new FeedbackRes(ResMessage.JSON_PROCESSING_EXCEPTION.getCode(),
					ResMessage.JSON_PROCESSING_EXCEPTION.getMessage());
		}
		return new FeedbackRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), feedbackList);// �e�{�����˵��M��feedbackList
	}

	@Override
	public StatisticsRes statistics(FeedbackReq req) {
		List<Response> reponseList = responseDao.findByQuizId(req.getQuizId());
		//�p��Ҧ��ݨ��^���� "�D�� �ﶵ ����"
		// qId�D��       �ﶵ      ����
		Map<Integer, Map<String, Integer>> countMap = new HashMap<>();   //Map�S�ʬۦP��key�|��\�e
		
		// ���Response��fillin
		// �Ĥ@��for�����SQL���Ҧ����^���A�o�Ǧ^���i��|��N����ơA�oN����ƬO�@��reponseList
		for (Response item : reponseList) {
			String fillinStr = item.getFillin();
			try {
				// �Nfillin�r���নList
				List<Fillin> fillinList = mapper.readValue(fillinStr, new TypeReference<>() {
				});
				
				// ���Xfillin�����ﶵ�M���סA�@��fillin�N�O�@�i�ݨ��^���A�N�OSQL�����@�����
				// �ĤG��for�����X�䤤�@�i�ݨ��^���A�]�N�O�C��̪��䤤�@�����(N����ƨ��X1��)
				for (Fillin fillin : fillinList) {
					Map<String, Integer> optionCountMap = new HashMap<>();
					//�ư�²���D:���C�J�έp
					if(fillin.getType().equalsIgnoreCase(OptionType.TEXT.getType())) {
						continue;
					};
					// �C�ӿﶵ�����O��(;)�걵//�p:[;���;�Q�s��;]�A�e�᳣�n�[�����A���M[���;������K]�κ���j�|�X�{�ⵧ�A�����Ƿj�M
					String optionStr = fillin.getOptions();
					String[] optionArray = optionStr.split(";");
					String answer = fillin.getAnswer();
					answer = ";" + answer + ";";// �z�ѦP�U
					
					/// �ĤT��for�����X�ݨ��ﶵ(�Y1��(�Y1�i�ݨ�)��Ƥ����^���ﶵ)
					for (String option : optionArray) {//[���;������K;���l���]
						// ��ﵪ�פ��C�ӿﶵ�X�{������
						// �קK�Y�ӿﶵ�O�t�@�ӿﶵ���䤤�@����
						// �Ҧp[���;������K;���l���]���O�ﶵ�A�n��X"���"�X�{���ơA������K�M���l����������
						// �ҥH�ݭn�b�C�ӿﶵ""�e��""�[�W�����A�|�Τ����O�]�����ת��걵�ϥΤ���
						// ����n��X���ƮɴN�|�O��;���;�ӧ�
						String newOption = ";" + option + ";";
						// �ΪťձN�^�����ﶵ���N�A�i�H�p��X��֪�����
						String newAnswerStr = answer.replace(newOption, "");
						// �p��ӿﶵ�X�{������
						// [;���;������K;���l���;]-[������K;���l���;]/[;���;] =1
						// �쥻���ת����צr��-�Q���N�᪺���צr����סA�n���H���׿ﶵ�����פ~�O�u�������ơA ���]���C�ӿﶵ�u���@���A�ҥH�o���X�ӥu�|��0��1
						int count = (answer.length() - newAnswerStr.length()) / newOption.length();
						// �O���C�@�D���έp,�����D�D���B���D�B����B�ﶵ�M����
						//�o��O������D�ﶵ�����L�D���ﶵ���V
						optionCountMap=countMap.getOrDefault(fillin.getqId(), optionCountMap);
						//�����X�ﶵ(key)����������
						//getOrDefault(option, 0):map���S��key���ܡA�N�|��^0�A�Φb�Ĥ@���A�]���Ĥ@��ԣ���S��
						int oldCount=optionCountMap.getOrDefault(option, 0);
						//�o��O�֥[:�֥[oldCount+count
						optionCountMap.put(option,oldCount+count);
						//�⦳�֥[���ƪ�optionCountMap�л\�^countMap��(�ۦP���D��)
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
				quiz.getName(),quiz.getStartDate(),quiz.getEndDate(),countMap);//�ƧǦbStatisticsRes�̱a�Ѽƪ����غc��k��
		
	}

}
