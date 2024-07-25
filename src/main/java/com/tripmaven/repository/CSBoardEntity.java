package com.tripmaven.repository;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "CSBoard")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CSBoardEntity {

    /** 고객센터 고유 번호. PK */
    @Id
    @SequenceGenerator(name = "seq_csboard", sequenceName = "seq_csboard", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_csboard")
    private long id;

    /** 회원 고유 번호. FK */
    @ManyToOne(optional = false)
    @JoinColumn(name = "membersentity_id")
    private MembersEntity member;

    /** 제목 */
    @Column(nullable = false, length = 50)
    private String title;

    /** 질문 내용 */
    @Column(nullable = false)
    @Lob
    private String content;

    /** 답변 내용 */
    @Lob
    private String comments;

    /** 게시 날짜 */
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    /** 활성화 여부 */
    @Column(nullable = false)
    @ColumnDefault("1")
    private String isActive;

    /** 수정 날짜 */
    @Column
    @CreationTimestamp
    private LocalDateTime updatedAt;

    /** 수정 유무 */
    @Column
    @ColumnDefault("0")
    private String isUpdate;

    /** 삭제 날짜 */
    @Column
    @CreationTimestamp
    private LocalDateTime deletedAt;

    /** 삭제 유무 */
    @Column
    @ColumnDefault("0")
    private String isDelete;
}
