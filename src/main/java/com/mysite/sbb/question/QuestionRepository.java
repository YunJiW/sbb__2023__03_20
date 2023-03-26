package com.mysite.sbb.question;

import com.mysite.sbb.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
    Question findBySubject(String subject);
    List<Question> findBySubjectAndContent(String subject,String content);

    List<Question> findBySubjectLike(String subject);

    Page<Question> findAll(Pageable pageable);

    Page<Question> findAll(Specification<Question> spec,Pageable pageable);
    @Transactional
    @Modifying //쿼리가 select가 아니라면 modify를 붙여야한다.
    @Query(value = "ALTER TABLE question AUTO_INCREMENT = 1", nativeQuery = true)
    void clearAutoIncrement();


    @Query("select "
    + "distinct q "
    + "from Question q "
    + "left outer join SiteUser u1 on q.author=u1 "
    + "left outer join Answer a on a.question=q "
    + "left outer join SiteUser u2 on a.author=u2 "
    + "where "
    + "  q.subject like %:kw% "
    + "  or q.content like %:kw% "
    + "  or u1.username like %:kw% "
    + "  or a.content like %:kw% "
    + "  or u2.username like %:kw% ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
