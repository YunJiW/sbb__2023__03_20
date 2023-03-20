package com.mysite.sbb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SbbApplicationTests {


	@Autowired
	private QuestionRepository questionRepository;
	@Test
	void testJpa(){
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
	void testJpa2(){
		Optional<Question> oq = this.questionRepository.findById(1);
		//null 아닌경우
		if(oq.isPresent()){
			Question q= oq.get();
			assertEquals("sbb가 무엇인가요?",q.getSubject());
			
		}
	}

	@Test
	@DisplayName("subject 값으로 데이터를 조회")
	void testJpa3(){
		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1,q.getId());
	}

	@Test
	@DisplayName("subject 값과 content를 and로 묶어서 데이터를 조회")
	void testJpa4(){
		Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?","sbb에 대해서 알고 싶습니다.");
		assertEquals(1,q.getId());
	}
}
