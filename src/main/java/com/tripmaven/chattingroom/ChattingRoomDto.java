package com.tripmaven.chattingroom;

import java.time.LocalDateTime;

import com.tripmaven.productboard.ProductBoardEntity;

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
	private Integer isActive;
	private LocalDateTime deletedAt;
	private Integer isDelete;
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
	public static ChattingRoomDto toDTO(ChattingRoomEntity chattingRoomEntity) {
        return ChattingRoomDto.builder()
                .id(chattingRoomEntity.getId())
                .createdAt(chattingRoomEntity.getCreatedAt())
                .isActive(chattingRoomEntity.getIsActive())
                .deletedAt(chattingRoomEntity.getDeletedAt())
                .isDelete(chattingRoomEntity.getIsDelete())
                .updatedAt(chattingRoomEntity.getUpdatedAt())
                .isUpdate(chattingRoomEntity.getIsUpdate())
                .build();
    }
}
