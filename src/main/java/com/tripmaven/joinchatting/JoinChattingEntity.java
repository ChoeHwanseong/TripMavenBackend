package com.tripmaven.joinchatting;

import com.tripmaven.chattingroom.ChattingRoomEntity;
import com.tripmaven.members.model.MembersEntity;


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

@Table(name = "JoinChatting")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinChattingEntity {

	/** 채팅방 입장 고유 번호. PK*/
	@Id
	@SequenceGenerator(name="seq_joinchatting",sequenceName = "seq_joinchatting",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_joinchatting")
	private long id;
	
	/** 채팅방 고유 번호. FK*/
	@ManyToOne(optional = false)
	@JoinColumn(name="chattingroomentity_id")
	private ChattingRoomEntity chattingRoom;
	
	/** 회원 고유 번호. FK*/
	@ManyToOne(optional = false)
	@JoinColumn(name="membersentity_id")
	private MembersEntity member;
	
	
}
