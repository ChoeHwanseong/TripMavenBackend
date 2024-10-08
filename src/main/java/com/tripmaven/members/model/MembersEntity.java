package com.tripmaven.members.model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tripmaven.token.TokenEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 회원테이블 모델 클래스.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
@Table(name = "members")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class MembersEntity {

	/** 회원 고유 번호. */
	@Id
	@SequenceGenerator(name="seq_members",sequenceName = "seq_members", allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_members")
	private long id;

	/** 이메일. */
	@Column(nullable = false, length = 50)
	private String email;

	/** 이름. */
	@Column(length = 100, nullable= false)
	private String name;

	/** 비밀번호. */
	@Column(nullable = false)
	private String password;

	/** 성별. */
	@Column(length = 6)
	private String gender;

	/** BIRTHDAY. */
	private LocalDate birthday;

	/** 전화. */
	@Column(length = 18)
	private String telNumber;

	/** 주소. */
	@Column(length = 255)
	private String address;

	/** 프로필 사진. */
	@Column
	@Lob
	private String profile;
	
	/** 자기 소개 */
	@Column
	@Lob
	private String introduce;
	
	/** 자기 소개 */
	@Column
	@Lob
	private String keywords;
	
	/** 생성일. */
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime createdAt;

	/** 활성화 여부. */
	@Column(length = 1)
	@ColumnDefault("1")
	private String isactive;

	/** 수정 시간. */
	private LocalDateTime updatedAt;

	/** 수정 여부. */
	@Column(length = 1)
	@ColumnDefault("0")
	private String isupdate;

	/** 삭제날짜. */
	private LocalDateTime deletedAt;

	/** 삭제 여부. */
	@Column(length = 1)
	@ColumnDefault("0")
	private String isdelete;

	/** 가이드, 어드민, 유저 인지. */
	@Column(length = 10)
	@ColumnDefault("USER")
	private String role;

	/** 가이드 자격증. */
	private String guidelicense;
	

	/** 로그인 타입 */
	@Column(name = "login_type",length = 10 )
	@ColumnDefault("local")
    private String loginType;

	/** 소셜로그인 어세스 토큰 */
	@Column(name = "sns_access_token", nullable = true)
    private String snsAccessToken;

	
	@Column(name = "inter_city",length = 50)
    private String interCity;
	
	/** 토큰. (양방향) FK*/
	@OneToMany(mappedBy = "members",cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<TokenEntity> token;
	

}
