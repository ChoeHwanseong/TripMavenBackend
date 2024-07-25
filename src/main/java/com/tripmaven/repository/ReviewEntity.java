package com.tripmaven.repository;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Review")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewEntity {

	/** 상품 고유 번호/신고 대상자. PK*/
	@Id
	@SequenceGenerator(name="seq_review",sequenceName = "seq_review",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_review")
	private long id;
	
	/** 회원 고유 번호/신고 대상자. PK*/
	
	/** 회원 고유 번호. FK*/
	@ManyToOne(optional = false)
	@JoinColumn(name="membersentity_id")
	private MembersEntity member;
	
	/** 가이드 상품 고유 번호 FK*/
	@ManyToOne(optional = false)
	@JoinColumn(name = "productboardentity_id")
	private ProductBoardEntity productBoard;
	
	/** 평가항목 LIST */
	@Column(nullable = false)
	private String list1;
	
	/** 평가항목 2 */
	@Column(nullable = false)
	private String list2;
	
	/** 리스트 3 */
	@Column(nullable = false)
	private String list3;
	
	/** 기타 */
	@Column(length = 20)
	private String etc;
	
	/** 리뷰 날짜 */
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	/** 활성화 여부 */
	@Column
	@ColumnDefault("1")
	private String isactive;
	
	/** 수정 날짜. */
	@CreationTimestamp
	private LocalDateTime updatedAt;

	/** 수정 여부. */
	@Column(nullable = false)
	@ColumnDefault("0")
	private String isupdate;

	/** 삭제 날짜. */
	@CreationTimestamp
	private LocalDateTime deletedAt;

	/** 삭제 여부. */
	@Column(nullable = false)
	@ColumnDefault("0")
	private String isdelete;


}
