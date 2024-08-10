package com.tripmaven.intercity;



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
public class InterCityDto {

	private long id;
	private MembersEntity member;
	
	//DTO를 ENTITY로 변환하는 메소드
	public InterCityEntity toEntity() {
		return InterCityEntity.builder()
				.id(id)
				.member(member)
				.build();
	}
	//ENTITY를 DTO로 변환하는 메소드
	public static InterCityDto toDto(InterCityEntity interCityEntity) {
		return InterCityDto.builder()
				.id(interCityEntity.getId())
				.member(interCityEntity.getMember())
				.build();
	}
}
