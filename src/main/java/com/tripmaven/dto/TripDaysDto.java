package com.tripmaven.dto;

import java.time.LocalDateTime;


import com.tripmaven.repository.ProductBoardEntity;
import com.tripmaven.repository.TripDaysEntity;


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
public class TripDaysDto {

	//필드
	private long id;
	private ProductBoardEntity productBoard;
	private Integer day;
	private String isUpdate;
	private LocalDateTime updatedAt;
	private String isCreate;
	private LocalDateTime createdAt;

	//DTO를 ENTITY로 변환하는 메소드
	public TripDaysEntity toEntity() {
		return TripDaysEntity.builder()
				.id(id)
				.productBoard(productBoard)
				.day(day)
				.isUpdate(isUpdate)
				.updatedAt(updatedAt)
				.isCreate(isCreate)
				.createdAt(createdAt)
				.build();
	}

	//ENTITY를 DTO로 변환하는 메소드
	public static TripDaysDto toDto(TripDaysEntity tripDaysEntity ) {
		return TripDaysDto.builder()
				.id(tripDaysEntity.getId())
				.productBoard(tripDaysEntity.getProductBoard())
				.day(tripDaysEntity.getDay())
				.isUpdate(tripDaysEntity.getIsUpdate())
				.updatedAt(tripDaysEntity.getUpdatedAt())
				.isCreate(tripDaysEntity.getIsCreate())
				.createdAt(tripDaysEntity.getCreatedAt())
				.build();

	}
}
