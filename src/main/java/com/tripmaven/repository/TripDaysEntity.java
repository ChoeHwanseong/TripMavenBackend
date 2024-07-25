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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripDaysEntity {

	/** 여행 일수 고유 번호. PK*/
	@Id
	@SequenceGenerator(name="seq_tripdays",sequenceName = "seq_tripdays",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_tripdays")
	private long id;
	
	/** 가이드 상품 고유 번호. FK*/
	@ManyToOne(optional = false)
	@JoinColumn(name = "productboardentity_id")
	private ProductBoardEntity productBoard;
	
	/** 날짜. */
	@Column(nullable = false)
    private Integer day;

	/** 수정 여부. */
	@Column(nullable = false)
	@ColumnDefault("0")
	private String isUpdate;
	
	/** 수정 날짜. */
	@CreationTimestamp
	private LocalDateTime updatedAt;
	
	/** 생성 여부. */
	@Column(nullable = false)
	@ColumnDefault("0")
	private String isCreate;
	
	/** 생성 날짜. */
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime createdAt;
}
