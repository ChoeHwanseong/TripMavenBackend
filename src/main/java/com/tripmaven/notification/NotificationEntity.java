package com.tripmaven.notification;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.tripmaven.chattingroom.ChattingRoomEntity;
import com.tripmaven.joinchatting.JoinChattingEntity;
import com.tripmaven.members.model.MembersEntity;

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

@Table(name = "Notification")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class NotificationEntity {
	
	/** 알림 고유 번호. PK*/
	@Id
	@SequenceGenerator(name="seq_notification",sequenceName = "seq_notification",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_notification")
	private long id;
	
	/** 알림 받는 사용자 id. FK*/
	@Column(nullable = false)
	private long memberId;
	
	/** 보낸 사용자 id. FK*/
	@Column(nullable = false)
	private long senderId;
	
	/** 알림 내용 */
	private String content;

	/** 알림이 읽혔는지 여부 */
	@ColumnDefault("0")
	private String isRead;

	/** 알림 생성 시간 */
	private LocalDateTime createAt;

	/** 알림 유형(chat, system, review 등등) */
	@Column(length = 20, nullable = false)
	private String type;

	/** 알림과 연결될 url 링크(엔드포인트) */
	@Column(length = 100)
	private String link;

	
	/*
	  user_id BIGINT, -- 알림을 받는 사용자 ID (어느 사용자를 위한 알림인지)
	  message TEXT, -- 알림 내용
	  is_read BOOLEAN DEFAULT FALSE, -- 알림이 읽혔는지 여부
	  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 알림 생성 시간
	  type VARCHAR(50), -- 알림 유형 (ex: 'chat', 'system', 'message' 등)
	  link VARCHAR(255) -- 알림과 연결될 리소스 (optional, ex: 특정 채팅방 ID 링크)
	 */
}
