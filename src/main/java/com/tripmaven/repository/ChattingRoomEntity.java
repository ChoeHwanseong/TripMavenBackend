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
public class ChattingRoomEntity {

	/** 채팅방 고유 번호. PK*/
	@Id
	@SequenceGenerator(name="seq_chattingroom",sequenceName = "seq_chattingroom",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_chattingroom")
	private long id;
	
	/** 생성날짜. */
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	/** 활성화 여부. */
	@Column(nullable = false)
	@ColumnDefault("1")
	private String isActive;

	/** 삭제날짜. */
	@CreationTimestamp
	private LocalDateTime deletedAt;

	/** 삭제 여부. */
	@Column(nullable = false)
	@ColumnDefault("0")
	private String isDelete;
	
	/** 수정 날짜. */
	@CreationTimestamp
	private LocalDateTime updatedAt;

	/** 수정 여부. */
	@Column(nullable = false)
	@ColumnDefault("0")
	private String isUpdate;
}
