package com.tripmaven.dto;

import java.time.LocalDateTime;
import java.util.Date;

import com.tripmaven.repository.CalendarEntity;
import com.tripmaven.repository.MembersEntity;
import com.tripmaven.repository.SocialUserEntity;

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
public class MembersDto {

	private long id;
	private String email;
	private String name;
	private String password;
	private String gender;
	private Date birthday;
	private String telNumber;
	private String address;
	private LocalDateTime createdAt;
	private String isactive;
	private LocalDateTime updatedAt;
	private String isupdate;
	private LocalDateTime deletedAt;
	private String isdelete;
	private String issocial;
	private LocalDateTime socialAt;
	private String role;
	private String guidelicense;
	private SocialUserEntity socialUser;
	
	//DTO를 ENTITY로 변환하는 메소드
	public MembersEntity toEntity() {
		return MembersEntity.builder()
				.id(id)
				.email(email)
				.name(name)
				.password(password)
				.gender(gender)
				.birthday(birthday)
				.telNumber(telNumber)
				.address(address)
				.createdAt(createdAt)
				.isactive(isactive)
				.updatedAt(updatedAt)
				.isupdate(isupdate)
				.deletedAt(deletedAt)
				.isdelete(isdelete)
				.issocial(issocial)
				.socialAt(socialAt)
				.role(role)
				.guidelicense(guidelicense)
				.socialUser(socialUser)
				.build();
	}
	//ENTITY를 DTO로 변환하는 메소드
	public static MembersDto toDto(MembersEntity membersEntity) {
		return MembersDto.builder()
				.id(membersEntity.getId())
				.email(membersEntity.getEmail())
				.name(membersEntity.getName())
				.password(membersEntity.getPassword())
				.gender(membersEntity.getGender())
				.birthday(membersEntity.getBirthday())
				.telNumber(membersEntity.getTelNumber())
				.address(membersEntity.getAddress())
				.createdAt(membersEntity.getCreatedAt())
				.isactive(membersEntity.getIsactive())
				.updatedAt(membersEntity.getUpdatedAt())
				.isupdate(membersEntity.getIsupdate())
				.deletedAt(membersEntity.getDeletedAt())
				.isdelete(membersEntity.getIsdelete())
				.issocial(membersEntity.getIssocial())
				.socialAt(membersEntity.getSocialAt())
				.role(membersEntity.getRole())
				.guidelicense(membersEntity.getGuidelicense())
				.socialUser(membersEntity.getSocialUser())
				.build();
	}
}
