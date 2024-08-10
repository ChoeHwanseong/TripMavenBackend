package com.tripmaven.messageread;

import com.tripmaven.chattingmessage.ChattingMessageEntity;
import com.tripmaven.members.model.MembersEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageReadDto {

	//필드
	private long id;
	private ChattingMessageEntity chattingMessage;
	private MembersEntity member;
	private String isRead;

	//DTO를 ENTITY로 변환하는 메소드
	public MessageReadEntity toEntity() {
		return MessageReadEntity.builder()
				.id(id)
				.chattingMessage(chattingMessage)
				.member(member)
				.isRead(isRead)
				.build();
	}

	//ENTITY를 DTO로 변환하는 메소드
	public static MessageReadDto toDto(MessageReadEntity messageReadEntity) {
		return MessageReadDto.builder()
				.id(messageReadEntity.getId())
				.chattingMessage(messageReadEntity.getChattingMessage())
				.member(messageReadEntity.getMember())
				.isRead(messageReadEntity.getIsRead())
				.build();

	}
}
