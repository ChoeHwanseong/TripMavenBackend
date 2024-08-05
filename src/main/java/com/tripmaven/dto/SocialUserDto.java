package com.tripmaven.dto;



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
public class SocialUserDto {

	//필드
	private Long id;
	private MembersEntity members;    
	private String provider;
	private String providerId;

	//DTO를 ENTITY로 변환하는 메소드
	public SocialUserEntity toEntity() {
		return SocialUserEntity.builder()
				.id(id)
				.members (members)
				.provider(provider)
				.providerId(providerId)
				.build();
	}

	//ENTITY를 DTO로 변환하는 메소드
	public static SocialUserDto toDto(SocialUserEntity socialUserEntity) {
		return SocialUserDto.builder()
				.id(socialUserEntity.getId())
				.members (socialUserEntity.getMembers())
				.provider(socialUserEntity.getProvider())
				.providerId(socialUserEntity.getProviderId())
				.build();

	}

}
