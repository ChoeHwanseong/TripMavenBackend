package com.tripmaven.productevaluation;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.productboard.ProductBoardEntity;

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

@Table(name = "ProductEvaluation")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class ProductEvaluationEntity {

	/** AI평가 고유 번호. PK */
	@Id
	@SequenceGenerator(name = "seq_productevaluation", sequenceName = "seq_productevaluation", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_productevaluation")
	private long id;

	/** 회원 고유 번호. FK*/
	@ManyToOne(optional = false)
	@JoinColumn(name="membersentity_id")
	private MembersEntity member;
	
	/** 가이드 상품 고유 번호. FK */
	@ManyToOne(optional = false)
	@JoinColumn(name = "productboardentity_id")
	private ProductBoardEntity productBoard;

	/** 평가 날짜. */
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	/** 삭제 유무. */
	@Column
	@ColumnDefault("0")
	private String isDelete;
	
	/** 전체 평균 점수. */
	@Column(nullable = false)
	private int score;

	/* 발음 점수 */
	@Column(nullable = false)
	private int pronunciation;
	
	/** 톤 높낮이에 대한 내용. */
	@Column(nullable = false)
	private String tone;

	/** 불필요한 단어사용에 대한 내용. */
	@Column(nullable = false)
	private int fillerwords;

	/** 평서문 대한 내용. */
	@Column(nullable = false)
	private int formal_speak;

	/** 의문문 대한 내용. */
	@Column(nullable = false)
	private int question_speak;

	/* 워드 클라우드 글자 */
	@Column(nullable = false)
	private String text;

	/* 워드 클라우드 글자 언급 횟수 */
	@Column(nullable = false)
	private String weight;
	
	/** 광대 변화율. */
	@Column(nullable = false)
	private String cheek;
	
	/** 입 변화율. */
	@Column(nullable = false)
	private String mouth;
	
	/** 미간 변화율. */
	@Column(nullable = false)
	private String brow;
		
	/** 팔자주름 변화율. */
	@Column(nullable = false)
	private String nasolabial;
	
	/** 눈 깜박임 횟수. */
	@Column(nullable = false)
	private String eye;
	
}
