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

	// �s�W�Χ�s�ݨ�
	@Override
	public BasicRes createOrUpdate(CreateOrUpdateReq req) {
		// �ˬd�Ѽ�
		BasicRes checkResult = checkParams(req);
		// checkResult==null�ɪ�ܰѼ��ˬd�����T
		if (checkResult != null) {
			return checkResult;
		}

		// ===============================
//		if (checkResult.getStatusCode() != 200) {
//
//		}
		// �]��Quiz��questions����Ʈ榡�OString,�ҥH�n�Nreq��List<Question>�নString
		// ObjectMapper�i�H�⪫��(���O)�নJSON�榡���r��
		// ���L�C��try catch
		ObjectMapper mapper = new ObjectMapper();
		try {
			// �쥻�OList<Question>questionList(�bCreateOrUpdateReq���ɮ׸�)�A�{�b�n��o�iLIST�নJSON�榡���r��
			String questionStr = mapper.writeValueAsString(req.getQuestionList());

			// �Yreq����id>0�h��ܧ�s�w�s�b�����;�A�Yid=0�h��ܷs�W
			// �o���ܥ��ˬd�~���i�Ӫ�ID��ơA�b��Ʈw�����S���s�b
			if (req.getId() > 0) {
				// �H�U��ؤ覡�ܤ@
				// �ϥΤ�k�@,�z�LfindById�A�Y����ƴN�|�^�Ǥ@�㵧�����(�i��^�Ǫ���ƶq���j)
				// �ϥΤ�k�G�A�O�]���z�LexsitsById�ӧP�_��ƬO�_�s�b�A�ҥH�^�Ǫ���ƥû����u�|�O�@��bit(0��1)
				// ��k�@:�z�LfindById�Y����Ʀ^�Ǿ㵧���
				// �Nreq���ߪ��ȳ]�w���ª�quiz��(�W����64�檺)�A���]�wid�A�]��id�@��(id�O�ߤ@�ȡA������)�C=>��findById
//				Optional<Quiz> op = quizDao.findById(req.getId());
//				//�P�_��ƬO�_���~
//				if(op.isEmpty()){//op.isEmpty():��ܨS���
//					return new BasicRes(ResMessage.UPDATE_ID_NOT_FOUND.getCode(), //
//							ResMessage.UPDATE_ID_NOT_FOUND.getMessage());
//				}
//				Quiz quiz=op.get();
//				//�]�w�s��(�ȱqreq����)
//				
//				quiz.setName(req.getName());
//				quiz.setDescription(req.getDescription());
//				quiz.setStartDate(req.getStartDate());
//				quiz.setEndDate(req.getEndDate());
//				quiz.setQuestions(questionStr);
//				quiz.setPublished(req.isPublished());
				// ��k�G:�z�LexsitsById�^�Ǥ@��bit���ȡA�ˬd��Ʈw���O�_�s�b���w�� Quiz ����C�Y���s�b�h�^��UPDATE_ID_NOT_FOUND
				// �T�{ID�s�b���Ʈw
				// �o��n�P�_�qreq�a�i�Ӫ�id�O�_�u�����s�b��DB����
				// �]���Yid���s�b�A�S���ˬd�A����{���X�b�I�sJPA��save��k�ɡA�|�ܦ��s�W
				// boolean boo = quizDao.existsById(req.getId());
				// �YID���s�b=0�A�h�^��UPDATE_ID_NOT_FOUND�ø��X�hif�P�_���A����quiz.setId(req.getId())�A�s�W�o���s���
				// =====��ΰΦW���O=====
				if (!quizDao.existsById(req.getId())) { // !boo ��ܸ�Ƥ��s�b�A�i��O�Q�R���� id
					return new BasicRes(ResMessage.UPDATE_ID_NOT_FOUND.getCode(), //
							ResMessage.UPDATE_ID_NOT_FOUND.getMessage());
				}
				// �n��id�A��L+�i�h�s�W�o�����
				// quiz.setId(req.getId());
				// �]���Onew�@�ӷs��Quiz�ҥH�n��id�A���M�s��Quiz�̡A�s�W/��s�N�S��id=>��exsitsById,�^�Ǹ�ƶq�֡A�ӯ�p
//				Quiz quiz = new Quiz(req.getId(), req.getName(), req.getDescription(), req.getStartDate(), //
//						req.getEndDate(), questionStr, req.isPublished());
			}
			// ===============================
			// �W�z�@��q if �{���X�i�H�Y��H�U�o�q
			// ��ܭY���~����ID��ƶi�ӡA�B��ID���s�bDB���A�h�~�����U����^ID���s�b���T���A�M��b����U����save���s�W�F�Y���ӴN�s�b��DB���A�h������U����save�@��s
//			if (req.getId() > 0 && !quizDao.existsById(req.getId())) {
//				return new BasicRes(ResMessage.UPDATE_ID_NOT_FOUND.getCode(),
//						ResMessage.UPDATE_ID_NOT_FOUND.getMessage());
//			}
			// ===============================

//			Quiz quiz = new Quiz(req.getName(),req.getDescription(),req.getStartDate(),//
//					req.getEndDate(),questionStr,req.isPublished());
//			quizDao.save(quiz);//�O��50�檺quiz
			// �]���ܼ�quiz�u�Τ@���A�]���i�H�ϥΰΦW���O�覡���g(���ݭn�ܼƱ�)
			// new Quiz()���a�Jreq.getId()�OPK�A�b�I�ssave�ɡA�|���h�ˬdPK�O�_�s�b��DB�����A
			// �Y�s�b�h��s�A���s�b�h�s�W
			// req���S�������ɡA�w�]�O0�A�]��id����ƫ��A�Oint
			quizDao.save(new Quiz(req.getId(), req.getName(), req.getDescription(), req.getStartDate(),
					req.getEndDate(), questionStr, req.isPublished()));

		} catch (JsonProcessingException e) { // e���`�A�o��n�ư����`

//			e.printStackTrace();
			return new BasicRes(ResMessage.JSON_PROCESSING_EXCEPTION.getCode(), //
					ResMessage.JSON_PROCESSING_EXCEPTION.getMessage());
		}

		// return null;
		return new BasicRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage());
	}

	private BasicRes checkParams(CreateOrUpdateReq req) {
		// �ˬd�ݨ��Ѽ�
		// StringUtils.hasText():�ˬd�r��A�O�_�����ťզr��B�ŭ�null�B�Ŧr��A�Y�O�ŦX3�ب䤤�@���h�|�^��false
		// �e���[!��ܤϦV�N��A�Y�r�ꪺ�ˬd���G�Ofalse���ܡA�N�|�i��if����@�϶�
		// !StringUtils.hasText(req.getName())���P��StringUtils.hasText(req.getName())==false
		// ����ĸ� �S��ĸ�
		if (!StringUtils.hasText(req.getName())) {
			return new BasicRes(ResMessage.PARAM_QUIZ_NAME_ERROR.getCode(), //
					ResMessage.PARAM_QUIZ_NAME_ERROR.getMessage());
		}
		if (!StringUtils.hasText(req.getDescription())) {
			return new BasicRes(ResMessage.PARAM_DESCRIPTION_ERROR.getCode(), //
					ResMessage.PARAM_DESCRIPTION_ERROR.getMessage());
		}

		// .isBefore()�O�u���u�����Ѥ��e�A���]�t���ѡA�Ϥ�isAfter�]�O
		// LocalDate.now(): ���o�t�η�e�ɶ�
		// req.getStartDate().isBefore(LocalDate.now()): �Y req �����}�l�ɶ�"��"���e�ɶ��A�|�o�� true
		// req.getStartDate().isEqual(LocalDate.now()): �Y req �����}�l�ɶ�"��"���e�ɶ��A�|�o�� true
		// �]���}�l�ɶ�����b����(�t)���e�A�ҥH�W��Ӥ���Y�O���@�ӵ��G�� true�A�h��ܶ}�l�ɶ��n���e(�t)�ɶ���
		// req.getStartDate().isAfter(LocalDate.now()): �Y req �����}�l�ɶ����e�ɶ��ߡA�|�o�� true
		// !req.getStartDate().isAfter(LocalDate.now()): �e�����[��ĸ��A��ܷ|�o��ۤϪ����G�A�N�O�}�l�ɶ�
		// �|����p���e�ɶ�
		// 1.�}�l�ɶ�����ߩ󵥩��e�ɶ�
		// 2.�}�l�ɶ��@�w�b��e�ɶ�����
		if (req.getStartDate() == null || !req.getStartDate().isAfter(LocalDate.now())) {
			return new BasicRes(ResMessage.PARAM_START_DATE_ERROR.getCode(), //
					ResMessage.PARAM_START_DATE_ERROR.getMessage());
		}
		// �}�l�ɶ�����p�󵥩��e�ɶ�
		// LocalDate.now(): ���o�t�η�e�ɶ�
		// req.getStartDate().isAfter(LocalDate.now()): �Y req �����}�l�ɶ����e�ɶ��ߡA�|�o�� true
		// !req.getStartDate().isAfter(LocalDate.now()): �e�����[��ĸ��A��ܷ|�o��ۤϪ����G�A�N�O�}�l�ɶ�
		// �|����p���e�ɶ�
		// LocalDate.now().isAfter(req.getStartDate()) =
		// !req.getStartDate().isAfter(LocalDate.now())
		// �{���X�����o��ɡA��ܶ}�l�ɶ��@�w�ߩ󵥩��e�ɶ�
		// �ҥH�����ˬd�����ɶ��ɡA�u�n�T�w�����ɶ��O�ߩ󵥩�}�l�ɶ��Y�i�A�]���u�n�����ɶ��O�ߩ󵥩�}�l�ɶ��A�N�@�w�|�ߩ󵥩��e�ɶ�
		// �ɶ��޿趶��:��e�ɶ�<=�}�l�ɶ�<=�����ɶ��A�ҥH�����ɶ����ݭn�P�_!req.getEndDate().isAfter(LocalDate.now())
		// 1.�����ɶ�����p��(��)�����e�ɶ�
		// 2.�����ɶ�����p��(��)�}�l�ɶ�
		if (req.getEndDate() == null || req.getEndDate().isBefore(req.getStartDate())) {//
			return new BasicRes(ResMessage.PARAM_END_DATE_ERROR.getCode(), //
					ResMessage.PARAM_END_DATE_ERROR.getMessage());
		}
		// �ˬd���D�Ѽ�
		if (CollectionUtils.isEmpty(req.getQuestionList())) {
			return new BasicRes(ResMessage.PARAM_QUESTION_List_NOT_FOUND.getCode(), //
					ResMessage.PARAM_QUESTION_List_NOT_FOUND.getMessage());

		}
		// �]���D�إi��O�h�D�ҥH�n�ΰj���ˬd

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
			// .equalsIgnoreCase():�r�����A�����j�p�g
			// ��option_type �O���Φh��ɡAoptions�N����O�Ŧr��
			// ��option_type�O��r�ɡAoptions���\�O�Ŧr��
			// �H�U�����ˬd:��option_type�O���Φh��ɡA�Boptions�O�Ŧr��A��^���~
			if (item.getType().equalsIgnoreCase(OptionType.SINGLE_CHOICE.getType())
					|| item.getType().equalsIgnoreCase(OptionType.MULTI_CHOICE.getType())) {
				if (!StringUtils.hasText(item.getOptions())) {
					return new BasicRes(ResMessage.PARAM_OPTIONS_ERROR.getCode(), //
							ResMessage.PARAM_OPTIONS_ERROR.getMessage());
				}
			}
			// �H�U�O�W�z���if�X�֪��g�k:((����1||����2)&&����3)
			// �Ĥ@��if����&&�ĤG��if����
//			if((item.getType().equalsIgnoreCase(OptionType.SINGLE_CHOICE.getType())
//					|| item.getType().equalsIgnoreCase(OptionType.MULTI_CHOICE.getType()))
//					&&!StringUtils.hasText(item.getOptions())) {
//				return new BasicRes(ResMessage.PARAM_OPTIONS_ERROR.getCode(), //
//						ResMessage.PARAM_OPTIONS_ERROR.getMessage());
//			}
		}

		return null;// �o��]�i�H�I�s200���\

	}

	@Override
	public SearchRes search(SearchReq req) {
		String name = req.getName();
		LocalDate start = req.getStartDate();
		LocalDate end = req.getEndDate();
		// ���]name�Onull�άO���ťզr��A�ݭn��name�ܦ��Ŧr��A�i�H�����S����J����ȡA�N��ܭn���o����
		// JPA��containing��k,����ȬO�Ŧr��ɡA�|�j�M����
		// �ҥH�n��name���ȬOnull�Υ��ťզr��ɡA�ഫ���Ŧr��
		// �Yreq�̪�name���ȴN���|�]�iif�A���p�G�Onull�ΪŦr��N�|�]�U����if
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
		// �ˬd�ѼƬO�_���T
		// �ˬdList�]���O���X�A�n��collection
		// �ˬd List �O�_���šG�]�� req.getIdList() �O�@�Ӷ��X�]List�^�A�ҥH�ϥ� CollectionUtils.isEmpty()
		// ��k���ˬd���O�_���šC
		// �R���ݨ��G�p�G���X�����šA�h�I�s quizDao.deleteAllById(req.getIdList()) ��k�A�R�����X�����Ҧ� ID �������ݨ��C
		// ��^���G�G�R���ާ@������A��^�@�Ӫ�ܦ��\���T���C
		// ���ˬdList�̭n�R������ƬO�_�s�b�A�Y������(�Y�s�b)�A�h����R���ݨ�
		if (!CollectionUtils.isEmpty(req.getIdList())) {
			// �R���ݨ�
			try {
				quizDao.deleteAllById(req.getIdList());
			} catch (Exception e) {
				// �� deleteAllById ��k���Aid ���Ȥ��s�b�ɡAJPA �|����
				// �]���b�R�����e�AJPA �|���j�M�a�J�� id �ȡA�Y�S���G�N�|����
				// �ѩ��ڤW�S�R�������ơA�]���N���ݭn��o�� Exception �@�B�z
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
