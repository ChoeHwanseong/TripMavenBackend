package com.tripmaven.repository;

import org.hibernate.annotations.ColumnDefault;

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
public class MessageReadEntity {
	
	/** 읽음 표시 고유 번호. PK*/
	@Id
	@SequenceGenerator(name="seq_messageread",sequenceName = "seq_messageread",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_messageread")
	private long id;
	
	/** 채팅방 메시지 고유 번호. FK*/
	@ManyToOne(optional = false)
	@JoinColumn(name="chattingmessageentity_id")
	private ChattingMessageEntity chattingmessage;
	
	/** 회원 고유 번호. FK*/
	@ManyToOne(optional = false)
	@JoinColumn(name="membersentity_id")
	private MembersEntity member;
	
	/** 읽음 여부. */
	@Column(nullable = false)
	@ColumnDefault("0")
	private String isRead;
}
