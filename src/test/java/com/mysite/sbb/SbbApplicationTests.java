package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
	private AnswerRepository answerRepository;
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
		Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?","sbb에 대해서 알고 싶습니다.");
		assertEquals(1,q.getId());
	}

	@Test
	@DisplayName("Like 데이터를 조회")
	void testJpafif(){
		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
		Question q = qList.get(2);
		assertEquals("sbb가 무엇인가요?",q.getSubject());
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
		assertEquals(1,this.questionRepository.count());

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
	@DisplayName("질문을 통해서 답변을 조회")
	@Transactional
	//매서드가 종룔될때 까지 DB세션이 유지됩니다.
	void testJpa_answerthird(){
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = q.getAnswerList();

		assertEquals(2,answerList.size());
		assertEquals("네 자동으로 생성됩니다.",answerList.get(0).getContent());
	}
}
