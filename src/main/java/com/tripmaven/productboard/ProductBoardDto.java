package com.tripmaven.productboard;

import java.time.LocalDateTime;

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
public class ProductBoardDto {

	//데이터 전달에 필요한 필드만 지정하라는데 뭘까?
	private long id;
	private MembersEntity member;	
	private String title;
	private String content;	
	private LocalDateTime createdAt;
	private String isActive;
	private String isEvaluation;
	private String city;
	private LocalDateTime updatedAt;
	private String isUpdate;
	private LocalDateTime deletedAt;
	private String isDelete;
	private String files;
	
	
	//DTO를 ENTITY로 변환하는 메소드
		public ProductBoardEntity toEntity() {
			return ProductBoardEntity.builder()
					.id(id)
					.member(member)
					.title(title)
					.content(content)
					.createdAt(createdAt)
					.isActive(isActive)
					.isEvaluation(isEvaluation)
					.city(city)
					.updatedAt(updatedAt)
					.isUpdate(isUpdate)
					.deletedAt(deletedAt)
					.isDelete(isDelete)
					.files(files)
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
					.isActive(productBoardEntity.getIsActive())
					.isEvaluation(productBoardEntity.getIsEvaluation())
					.city(productBoardEntity.getCity())
					.updatedAt(productBoardEntity.getUpdatedAt())
					.isUpdate(productBoardEntity.getIsUpdate())
					.deletedAt(productBoardEntity.getDeletedAt())
					.isDelete(productBoardEntity.getIsDelete())
					.files(productBoardEntity.getFiles())					
					.build();
					
		}
}
