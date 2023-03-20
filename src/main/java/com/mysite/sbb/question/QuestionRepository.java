package com.mysite.sbb.question;

import com.mysite.sbb.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
    Question findBySubject(String subject);
    List<Question> findBySubjectAndContent(String subject,String content);

    Question findBySubjectAndId(String subject,Integer id);
    List<Question> findBySubjectLike(String subject);
}
