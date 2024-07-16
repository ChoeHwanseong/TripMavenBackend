package com.tripmaven.service;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import com.tripmaven.repository.MembersEntity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
public class MembersDTO {
	
	private long id;
	private String eMail;
	private String name;
	private String password;
	private String gender;
	private Date birthday;
	private String telnumber;
	private String address;
	private LocalDateTime createdAt;
	private String issocial;
	private String Role;
	private String guidelicense;
	
	//DTO를 ENTITY로 변환하는 메소드
		public MembersEntity toEntity() {
			return MembersEntity.builder()
					.id(id)
					.eMail(eMail)
					.name(name)
					.password(password)
					.gender(gender)
					.birthday(birthday)
					.telnumber(telnumber)
					.address(address)
					.createdAt(createdAt)
					.issocial(issocial)
					.Role(Role)
					.guidelicense(guidelicense)
					.build();
		}
		
		//Entity를 DTO로 변환하는 메소드
		public static MembersDTO toDto(MembersEntity membersEntity) {
			return MembersDTO.builder()
					.id(membersEntity.getId())
					.eMail(membersEntity.getEMail())
					.name(membersEntity.getName())
					.password(membersEntity.getPassword())
					.gender(membersEntity.getGender())
					.birthday(membersEntity.getBirthday())
					.telnumber(membersEntity.getTelnumber())
					.address(membersEntity.getAddress())
					.createdAt(membersEntity.getCreatedAt())
					.issocial(membersEntity.getIssocial())
					.Role(membersEntity.getRole())
					.guidelicense(membersEntity.getGuidelicense())
					.build();
		}
}
