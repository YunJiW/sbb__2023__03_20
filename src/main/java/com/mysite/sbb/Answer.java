package com.mysite.sbb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne //다른 엔티티 클래스를 저장할때는 꼭 관계를 적어줘야함.
    private Question question;

    //Answer  @ManyToOne question
    //질문 1개에 답변이 여러개 달린다.
}
