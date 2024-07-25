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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "ChattingMessage")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChattingMessageEntity {

	/** 채팅 메시지 고유 번호. PK*/
	@Id
	@SequenceGenerator(name="seq_chattingmessage",sequenceName = "seq_chattingmessage",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_chattingmessage")
	private long id;
	
	/** 채팅방 고유 번호. FK*/
	@ManyToOne(optional = false)
	@JoinColumn(name="chattingroomentity_id")
	private ChattingRoomEntity chattingroom;
	
	/** 유저 고유 번호. */
    @Column(nullable = false)
    private long userId;
    
	/** 메시지 내용. */
    @Column(nullable = false, length = 100)
	private String content;
    
	/** 생성 날짜. */
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	/** 생성 여부. */
	@Column(nullable = false)
	@ColumnDefault("0")
	private String isActive;
    
	/** 삭제 날짜. */
	@CreationTimestamp
	private LocalDateTime deletedAt;

	/** 삭제 여부. */
	@Column(nullable = false)
	@ColumnDefault("0")
	private String isDelete;
    
	
}
