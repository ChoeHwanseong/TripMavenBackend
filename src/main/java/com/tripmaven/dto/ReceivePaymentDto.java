package com.tripmaven.dto;

import java.time.LocalDateTime;


import com.tripmaven.repository.PaymentEntity;
import com.tripmaven.repository.ReceivePaymentEntity;


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
public class ReceivePaymentDto {

	//필드
	private long id;
	private PaymentEntity payment;
	private int price;
	private String payMethod;
	private LocalDateTime createdAt;
	private String isactive;

	//DTO를 ENTITY로 변환하는 메소드
	public ReceivePaymentEntity toEntity() {
		return ReceivePaymentEntity.builder()
				.id(id)
				.payment(payment)
				.price(price)
				.payMethod(payMethod)
				.createdAt(createdAt)
				.isactive(isactive)
				.build();
	}

	//ENTITY를 DTO로 변환하는 메소드
	public static ReceivePaymentDto toDto(ReceivePaymentEntity receivePaymentEntity) {
		return ReceivePaymentDto.builder()
				.id(receivePaymentEntity.getId())
				.payment(receivePaymentEntity.getPayment())
				.price(receivePaymentEntity.getPrice())
				.payMethod(receivePaymentEntity.getPayMethod())
				.createdAt(receivePaymentEntity.getCreatedAt())
				.isactive(receivePaymentEntity.getIsactive())
				.build();

	}
}
