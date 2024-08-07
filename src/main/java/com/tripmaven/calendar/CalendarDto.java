package com.tripmaven.calendar;

import java.util.Date;

import com.tripmaven.members.MembersEntity;

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
public class CalendarDto {

	private long id;
	private MembersEntity member;
	private Date startDate;
	private Date endDate;
	
	
	//DTO를 ENTITY로 변환하는 메소드
	public CalendarEntity toEntity() {
		return CalendarEntity.builder()
				.id(id)
				.member(member)
				.startDate(startDate)
				.endDate(endDate)
				.build();	
	}
	//ENTITY를 DTO로 변환하는 메소드
	public static CalendarDto toDto(CalendarEntity calendarEntity) {
		return CalendarDto.builder()
				.id(calendarEntity.getId())
				.member(calendarEntity.getMember())
				.startDate(calendarEntity.getStartDate())
				.endDate(calendarEntity.getEndDate())
				.build();
	}
}
