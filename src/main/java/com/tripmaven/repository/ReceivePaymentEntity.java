package com.tripmaven.repository;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceivePaymentEntity {

	/** 토스에서 받은 결제 고유 번호. PK*/
	@Id
	@SequenceGenerator(name="seq_receivepayment",sequenceName = "seq_receivepayment",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_receivepayment")
	private long id;
	
	/** 토스에서 받은 결제 고유 번호. FK*/
	@ManyToOne(optional = false)
	@JoinColumn(name="paymententity_id")
	private PaymentEntity payment;
	
	/** 결제 금액. */
	@Column
	private int price;
	
	/** 결제 수단. */
	@Column
	private String payMethod;
	
	/** 결제 시간. */
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	/** 결제 성공 유무. */
	@Column
	@ColumnDefault("1")
	private String isactive;
	
	
}
