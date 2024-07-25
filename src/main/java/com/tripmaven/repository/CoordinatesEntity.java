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
public class CoordinatesEntity {

	/** 좌표 고유 번호. PK*/
	@Id
	@SequenceGenerator(name="seq_coordinates",sequenceName = "seq_coordinates",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_coordinates")
	private long id;
	
	/** 날짜 고유 번호. FK*/
	@ManyToOne(optional = false)
	@JoinColumn(name="tripdaysentity_id")
	private TripDaysEntity tripDays;
	
	/** 좌표. */
	@Column(nullable = false, length = 16)
	private String coordinate;
	
	/** 좌표 내용. */
	@Column(nullable = false, length = 200)
	private String content;
	
	/** 생성 여부. */
	@Column(nullable = false)
	@ColumnDefault("0")
	private String isCreate;
	
	/** 생성 날짜. */
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime createdAt;

	/** 삭제 여부. */
	@Column(nullable = false)
	@ColumnDefault("0")
	private String isDelete;
	
	/** 삭제 날짜. */
	@CreationTimestamp
	private LocalDateTime deletedAt;
	
}
