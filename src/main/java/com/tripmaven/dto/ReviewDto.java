package com.tripmaven.dto;

import java.time.LocalDateTime;



import com.tripmaven.repository.MembersEntity;
import com.tripmaven.repository.ProductBoardEntity;
import com.tripmaven.repository.ReviewEntity;


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
public class ReviewDto {


	//필드
	private long id;
	private MembersEntity member;
	private ProductBoardEntity productBoard;
	private String list1;
	private String list2;
	private String list3;
	private String etc;
	private LocalDateTime createdAt;
	private String isactive;
	private LocalDateTime updatedAt;
	private String isupdate;
	private LocalDateTime deletedAt;
	private String isdelete;

	//DTO를 ENTITY로 변환하는 메소드
	public ReviewEntity toEntity() {
		return ReviewEntity.builder()
				.id(id)
				.member(member)
				.productBoard(productBoard)
				.list1(list1)
				.list2(list2)
				.list3(list3)
				.etc(etc)
				.createdAt(createdAt)
				.isactive(isactive)
				.updatedAt(updatedAt)
				.isupdate(isupdate)
				.deletedAt(deletedAt)
				.isdelete(isdelete)
				.build();
	}

	//ENTITY를 DTO로 변환하는 메소드
	public static ReviewDto toDto(ReviewEntity reviewEntity) {
		return ReviewDto.builder()
				.id(reviewEntity.getId())
				.member(reviewEntity.getMember())
				.productBoard(reviewEntity.getProductBoard())
				.list1(reviewEntity.getList1())
				.list2(reviewEntity.getList2())
				.list3(reviewEntity.getList3())
				.etc(reviewEntity.getEtc())
				.createdAt(reviewEntity.getCreatedAt())
				.isactive(reviewEntity.getIsactive())
				.updatedAt(reviewEntity.getUpdatedAt())
				.isupdate(reviewEntity.getIsupdate())
				.deletedAt(reviewEntity.getDeletedAt())
				.isdelete(reviewEntity.getIsdelete())
				.build();
	}
}
