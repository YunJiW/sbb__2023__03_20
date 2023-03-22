package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.question.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SbbApplicationTests {


	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerRepository answerRepository;


	@BeforeEach
		// 아래 메서드는 각 테스트케이스가 실행되기 전에 실행된다.
	void beforeEach() {


		answerRepository.deleteAll();
		answerRepository.clearAutoIncrement();

		// 모든 데이터 삭제
		questionRepository.deleteAll();

		// 흔적삭제(다음번 INSERT 때 id가 1번으로 설정되도록)
		questionRepository.clearAutoIncrement();

		// 질문 1개 생성
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);  // 첫번째 질문 저장

		// 질문 1개 생성
		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);  // 두번째 질문 저장


		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		//a.setQuestion(q2); //db에 저장됨.
		q2.addAnswer(a); // 설정으로 저장한다.
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
	}

	@Test
	@DisplayName("데이터 300개 넣기")
	void TestdataAdding(){
		for(int idx = 1; idx <=300; idx++){
			String subject = String.format("테스트 데이터입니다 :[%03d]",idx);

			String content ="내용무";
			this.questionService.create(subject,content);
		}
	}



	@Test
	void testJpafirst(){
		/*
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);


		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);


		 */
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2,all.size());

		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?",q.getSubject());
	}

	@Test
	@DisplayName("id값으로 데이터를 조회해보자")
	void testJpasecond(){
		Optional<Question> oq = this.questionRepository.findById(1);
		//null 아닌경우
		if(oq.isPresent()){
			Question q= oq.get();
			assertEquals("sbb가 무엇인가요?",q.getSubject());
			
		}
	}

	@Test
	@DisplayName("subject 값으로 데이터를 조회")
	void testJpathird(){
		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1,q.getId());
	}

	@Test
	@DisplayName("subject 값과 content를 and로 묶어서 데이터를 조회")
	void testJpafourth(){
		List<Question> qList = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?","sbb에 대해서 알고 싶습니다.");
		assertEquals(1,qList.size());
	}

	@Test
	@DisplayName("Like 데이터를 조회")
	void testJpafif(){
		List<Question> qList = this.questionRepository.findBySubjectLike("스프%");
		Question q = qList.get(0);
		assertEquals("스프링부트 모델 질문입니다.",q.getSubject());
	}
	@Test
	@DisplayName("질문 데이터 수정")
	void testJpasix(){
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정된 제목");
		this.questionRepository.save(q);
	}

	@Test
	@DisplayName("질문 삭제")
	void testJpaseven(){
		assertEquals(2,this.questionRepository.count());

		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1,this.questionRepository.count());
	}
	@Test
	@DisplayName("질문에 대한 answer 생성")
	void testJpa_answerfirst(){
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());

		Question q = oq.get();

		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(q);
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
	}

	@Test
	@DisplayName("answer 답변 조회")
	void testJpa_answersecond(){
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2,a.getQuestion().getId());
	}

	@Test
	@DisplayName("질문을 연결된 답변을 조회")
	@Transactional
	//매서드가 종룔될때 까지 DB세션이 유지됩니다.
	void testJpa_answerthird(){
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = q.getAnswerList();

		assertEquals(1,answerList.size());
		assertEquals("네 자동으로 생성됩니다.",answerList.get(0).getContent());
	}
}
