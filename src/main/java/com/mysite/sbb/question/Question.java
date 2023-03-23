package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //질문 제목
    @Column(length = 200)
    private String subject;

    //질문 내용
    @Column(columnDefinition = "TEXT")
    private String content;

    //작성 일시
    private LocalDateTime createDate;

    //답을 저장할 리스트
    //질문을 지울경우 질문에 답한 답변을 전부 지운다.
    //자바의 편의를 위해서 만들어짐 실제 db테이블 컬럼은 만들어지지 않음.
    //만들어도되고 안만들어도된다.
    //다만 만들면 해당 객체(질문 객체)에서 관련된 답변들을 찾을때 편하다.
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList = new ArrayList<>();

    //규칙이라 생각하자!
    public void addAnswer(Answer a) {
        a.setQuestion(this);
        answerList.add(a);
    }

    @ManyToOne
    private SiteUser author;
}
