package com.mysite.sbb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
}
