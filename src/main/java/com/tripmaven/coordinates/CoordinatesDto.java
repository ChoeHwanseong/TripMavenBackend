package com.tripmaven.coordinates;

import java.time.LocalDateTime;

import com.tripmaven.tripdays.TripDaysEntity;

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
public class CoordinatesDto {

	private long id;
	private TripDaysEntity tripDays;
	private String coordinate;
	private String content;
	private String isCreate;
	private LocalDateTime createdAt;
	private String isDelete;
	private LocalDateTime deletedAt;
	
	//DTO를 ENTITY로 변환하는 메소드
	public CoordinatesEntity toEntity() {
		return CoordinatesEntity.builder()
				.id(id)
				.tripDays(tripDays)
				.coordinate(coordinate)
				.content(content)
				.isCreate(isCreate)
				.createdAt(createdAt)
				.isDelete(isDelete)
				.deletedAt(deletedAt)
				.build();
	}
	//ENTITY를 DTO로 변환하는 메소드
	public static CoordinatesDto toDto(CoordinatesEntity coordinatesEntity) {
		return CoordinatesDto.builder()
				.id(coordinatesEntity.getId())
				.tripDays(coordinatesEntity.getTripDays())
				.coordinate(coordinatesEntity.getCoordinate())
				.content(coordinatesEntity.getContent())
				.isCreate(coordinatesEntity.getIsCreate())
				.createdAt(coordinatesEntity.getCreatedAt())
				.isDelete(coordinatesEntity.getIsDelete())
				.deletedAt(coordinatesEntity.getDeletedAt())
				.build();
	}
}
