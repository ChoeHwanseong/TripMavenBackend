package com.tripmaven.repository;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.tripmaven.members.repository.MembersEntity;

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

@Table(name = "Report")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportEntity {

	/** 신고 고유 번호 PK*/
	@Id
	@SequenceGenerator(name="seq_report",sequenceName = "seq_report",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_report")
	private long id;
	
	/** 가이드 상품 고유 번호 FK*/
	@ManyToOne(optional = false)
	@JoinColumn(name = "productboardentity_id")
	private ProductBoardEntity productBoard;
	
	/** 회원 고유 번호. FK*/
	@ManyToOne(optional = false)
	@JoinColumn(name="membersentity_id")
	private MembersEntity member;
	
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
	
	/** 평가항목 : 불친절한 태도 */
	@Column(nullable = false)
	private String attitude;
	
	/** 평가항목 : 부정확한 정보 */
	@Column(nullable = false)
	private String information;
	
	/** 평가항목 : 혐오발언 */
	@Column(nullable = false)
	private String disgust;
	
	/** 평가항목 : 공격적인 언어 */
	@Column(nullable = false)
	private String offensive;
	
	/** 평가항목 : 예약 불이행 */
	@Column(nullable = false)
	private String noShow;

	//
}
