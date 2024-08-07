package com.tripmaven.intercity;

import java.util.Date;

import com.tripmaven.members.MembersEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "InterCity")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterCityEntity {

	/** 선호 도시 고유 번호. PK*/
	@Id
	@SequenceGenerator(name="seq_intercity",sequenceName = "seq_intercity",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_intercity")
	private long id;
	
	/** 회원 고유 번호. FK*/
	@ManyToOne(optional = false)
	@JoinColumn(name="membersentity_id")
	private MembersEntity member;
	
}
