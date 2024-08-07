package com.tripmaven.chattingmessage;

import java.time.LocalDateTime;

import com.tripmaven.chattingroom.ChattingRoomEntity;

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
public class ChattingMessageDto {

	private long id;
	private ChattingRoomEntity chattingroom;
	private long userId;
	private String content;
	private LocalDateTime createdAt;
	private String isActive;
	private LocalDateTime deletedAt;
	private String isDelete;
	
	//DTO를 ENTITY로 변환하는 메소드
	public ChattingMessageEntity toEntity() {
		return ChattingMessageEntity.builder()
				.id(id)
				.chattingroom(chattingroom)
				.userId(userId)
				.content(content)
				.createdAt(createdAt)
				.isActive(isActive)
				.deletedAt(deletedAt)
				.isDelete(isDelete)
				.build();
	}
	//ENTITY를 DTO로 변환하는 메소드
	public static ChattingMessageDto toDto(ChattingMessageEntity chattingMessageEntity) {
		return ChattingMessageDto.builder()
				.id(chattingMessageEntity.getId())
				.chattingroom(chattingMessageEntity.getChattingroom())
				.userId(chattingMessageEntity.getUserId())
				.content(chattingMessageEntity.getContent())
				.createdAt(chattingMessageEntity.getCreatedAt())
				.isActive(chattingMessageEntity.getIsActive())
				.deletedAt(chattingMessageEntity.getDeletedAt())
				.isDelete(chattingMessageEntity.getIsDelete())
				.build();
	}
	
}
