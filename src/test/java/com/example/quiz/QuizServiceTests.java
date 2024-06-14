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
		///////////////////////////// ID �D�� �ﶵ type
		questionList.add(new Question(1, "���d�\�Yԣ?", "�Q����;���ޱ�;�γ�;���ӫ�"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		questionList.add(new Question(2, "�����Yԣ?", "1���\;2���\;3���\;4���\"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		questionList.add(new Question(3, "�����Yԣ?", "�ަת���;���A����;�z�����a��(��);��X����"//
				, OptionType.SINGLE_CHOICE.getType(), true));
//�h�Ѧ�CreateReq�a�Ѽƪ��غc��k���Ƨ�     name    description �}�l�ɶ�(�O�o�n��t�Ϊ��ɶ��ߦܤ֤@�ѡA���M�]���X��)�����ɶ�
		CreateOrUpdateReq req = new CreateOrUpdateReq("���\�Yԣ?", "���\�Yԣ?", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30), //
				questionList, true);//
		// �I�s��k����A�����޿�
		BasicRes res = quizService.createOrUpdate(req);
		// �A�����r��N����G�Ofalse�ɦ^����r
		Assert.isTrue(res.getStatusCode() == 200, "create test false!!");
	}

	// �R�����ո�� TODO

	@Test
	// createNameTest��Name�����OCreateReq��name
	public void createNameTest() {
		List<Question> questionList = new ArrayList<>();
		///////////////////////////// ID �D�� �ﶵ type
		questionList.add(new Question(1, "���d�\�Yԣ?", "�Q����;���ޱ�;�γ�;���ӫ�"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		questionList.add(new Question(2, "�����Yԣ?", "1���\;2���\;3���\;4���\"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		questionList.add(new Question(3, "�����Yԣ?", "�ަת���;���A����;�z�����a��(��);��X����"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		CreateOrUpdateReq req = new CreateOrUpdateReq("", "���\�Yԣ?", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30), //
				questionList, true);//
		// �I�s��k����A�����޿�
		BasicRes res = quizService.createOrUpdate(req);
		// �A�����r��N����G�Ofalse�ɦ^����r
		Assert.isTrue(res.getMessage().equalsIgnoreCase("Param name error!!"), "create test false!!");
	}

	@Test
	// createStartDateTest��Name�����OCreateReq�̪�StartDate
	public void createStartDateTest() {
		List<Question> questionList = new ArrayList<>();
		// �h�Ѧ�CreateReq�a�Ѽƪ��غc��k���Ƨ� name description �}�l�ɶ�(�O�o�n��t�Ϊ��ɶ��ߦܤ֤@�ѡA���M�]���X��)�����ɶ�
		///////////////////////////// ID �D�� �ﶵ type
		questionList.add(new Question(1, "���d�\�Yԣ?", "�Q����;���ޱ�;�γ�;���ӫ�"//
				, OptionType.SINGLE_CHOICE.getType(), true));

		questionList.add(new Question(2, "�����Yԣ?", "1���\;2���\;3���\;4���\"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		questionList.add(new Question(3, "�����Yԣ?", "�ަת���;���A����;�z�����a��(��);��X����"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		// ���ѬO2024/5/30�A�ҥH�}�l�������O��ѩΤ��e
		CreateOrUpdateReq req = new CreateOrUpdateReq("���\�Yԣ?", "���\�Yԣ?", LocalDate.of(2024, 5, 30), LocalDate.of(2024, 6, 30), //
				questionList, true);//
		// �I�s��k����A�����޿�
		BasicRes res = quizService.createOrUpdate(req);
		// �A�����r��N����G�Ofalse�ɦ^����r
		Assert.isTrue(res.getMessage().equalsIgnoreCase("Start date error!!"), "create test false!!");

	}

	@Test
	public void createTest1() {
		List<Question> questionList = new ArrayList<>();
		questionList.add(new Question(1, "���d�\�Yԣ?", "�Q����;���ޱ�;�γ�;���ӫ�"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		// �h�Ѧ�CreateReq�a�Ѽƪ��غc��k���Ƨ� name description �}�l�ɶ�(�O�o�n��t�Ϊ��ɶ��ߦܤ֤@�ѡA���M�]���X��)�����ɶ�
		// ����name error
		CreateOrUpdateReq req = new CreateOrUpdateReq("", "���\�Yԣ?", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30), //
				questionList, true);//
		// �I�s��k����A�����޿�
		BasicRes res = quizService.createOrUpdate(req);
		// �A�����r��N����G�Ofalse�ɦ^����r
		//res.getMessage().equalsIgnoreCase("Param name error!!")�o�̬O���L�ȡA��true
		Assert.isTrue(res.getMessage().equalsIgnoreCase("Param name error!!"), "create test false!!");
		
		// ����start date error:���]���ѬO2024/5/30�ҥH�}�l�������O��ѩΤ��e
		req = new CreateOrUpdateReq("���\�Yԣ?", "���\�Yԣ?", LocalDate.of(2024, 5, 30), LocalDate.of(2024, 6, 1), //
				questionList, true);//
		res = quizService.createOrUpdate(req);
		Assert.isTrue(res.getMessage().equalsIgnoreCase("Start date error!!"), "create test false!!");
		// ����end date error:�����ɶ������}�l�ɶ���
		req = new CreateOrUpdateReq("���\�Yԣ?", "���\�Yԣ?", LocalDate.of(2024, 5,31 ), LocalDate.of(2024, 5, 5), //
				questionList, true);//
		res = quizService.createOrUpdate(req);
		Assert.isTrue(res.getMessage().equalsIgnoreCase("End date error!!"), "create test false!!");
		// �Ѿl�޿�����P�_������A�̫�~�|�O���զ��\������
		req = new CreateOrUpdateReq("���\�Yԣ?", "���\�Yԣ?", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 1), //
				questionList, true);//
		res = quizService.createOrUpdate(req);
		Assert.isTrue(res.getStatusCode()==200, "create test false!!");
		
	}
	@Test
	public void createOrUpdateTest(){
		List<Question> questionList = new ArrayList<>();
		questionList.add(new Question(1, "���d�\�Yԣ?", "�Q����;���ޱ�;�γ�;���ӫ�"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		// �h�Ѧ�CreateReq�a�Ѽƪ��غc��k���Ƨ� name description �}�l�ɶ�(�O�o�n��t�Ϊ��ɶ��ߦܤ֤@�ѡA���M�]���X��)�����ɶ�
		// ����name error
		CreateOrUpdateReq req = new CreateOrUpdateReq("", "���\�Yԣ?", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30), //
				questionList, true);//
		
	}
	
	/////
	
	@Test
	public void gitUpdateTest(){
		List<Question> questionList = new ArrayList<>();
		questionList.add(new Question(1, "���d�\�Yԣ?", "�Q����;���ޱ�;�γ�;���ӫ�"//
				, OptionType.SINGLE_CHOICE.getType(), true));
		// �h�Ѧ�CreateReq�a�Ѽƪ��غc��k���Ƨ� name description �}�l�ɶ�(�O�o�n��t�Ϊ��ɶ��ߦܤ֤@�ѡA���M�]���X��)�����ɶ�
		// ����name error
		CreateOrUpdateReq req = new CreateOrUpdateReq("", "���\�Yԣ?", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30), //
				questionList, true);
		System.out.println("=============");
		
	}
	
}
