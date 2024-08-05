package com.tripmaven.dto;

import java.time.LocalDateTime;
import com.tripmaven.repository.MembersEntity;
import com.tripmaven.repository.ProductBoardEntity;
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
public class ProductBoardDto {

	//데이터 전달에 필요한 필드만 지정하라는데 뭘까?
	private long id;
	private MembersEntity member;	
	private String title;
	private String content;	
	private LocalDateTime createdAt;
	private String isactive;
	private String isevaluation;
	private String city;
	private LocalDateTime updatedAt;
	private String isupdate;
	
	//DTO를 ENTITY로 변환하는 메소드
		public ProductBoardEntity toEntity() {
			return ProductBoardEntity.builder()
					.id(id)
					.member(member)
					.title(title)
					.content(content)
					.createdAt(createdAt)
					.isactive(isactive)
					.isevaluation(isevaluation)
					.city(city)
					.updatedAt(updatedAt)
					.isupdate(isupdate)
					.build();
		}
		//ENTITY를 DTO로 변환하는 메소드
		public static ProductBoardDto toDto(ProductBoardEntity productBoardEntity) {
			return ProductBoardDto.builder()
					.id(productBoardEntity.getId())
					.member(productBoardEntity.getMember())
					.title(productBoardEntity.getTitle())
					.content(productBoardEntity.getContent())
					.createdAt(productBoardEntity.getCreatedAt())
					.isactive(productBoardEntity.getIsactive())
					.isevaluation(productBoardEntity.getIsevaluation())
					.city(productBoardEntity.getCity())
					.updatedAt(productBoardEntity.getUpdatedAt())
					.isupdate(productBoardEntity.getIsupdate())
					.build();
					
		}
}
