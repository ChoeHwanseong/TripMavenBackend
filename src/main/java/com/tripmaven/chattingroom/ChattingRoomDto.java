package com.tripmaven.chattingroom;

import java.time.LocalDateTime;

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
public class ChattingRoomDto {

	private long id;
	private LocalDateTime createdAt;
	private String isActive;
	private LocalDateTime deletedAt;
	private String isDelete;
	private LocalDateTime updatedAt;
	private String isUpdate;
	
	//DTO를 ENTITY로 변환하는 메소드
	public ChattingRoomEntity toEntity() {
		return ChattingRoomEntity.builder()
				.id(id)
				.createdAt(createdAt)
				.isActive(isActive)
				.deletedAt(deletedAt)
				.isDelete(isDelete)
				.updatedAt(updatedAt)
				.isUpdate(isUpdate)
				.build();		
	}
	//ENTITY를 DTO로 변환하는 메소드
	public ChattingRoomDto toDto(ChattingRoomDto chattingRoomDto) {
		return ChattingRoomDto.builder()
				.id(chattingRoomDto.getId())
				.createdAt(chattingRoomDto.getCreatedAt())
				.isActive(chattingRoomDto.getIsActive())
				.deletedAt(chattingRoomDto.getDeletedAt())
				.isDelete(chattingRoomDto.getIsDelete())
				.updatedAt(chattingRoomDto.getUpdatedAt())
				.isUpdate(chattingRoomDto.getIsUpdate())
				.build();		
	}
}
