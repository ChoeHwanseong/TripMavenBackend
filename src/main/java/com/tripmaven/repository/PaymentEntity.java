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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Payment")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEntity {

	/** 토스에서 받은 결제 고유 번호. PK*/
	@Id
	@SequenceGenerator(name="seq_payment",sequenceName = "seq_payment",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_payment")
	private long id;
	
	/** 가이드 상품 고유 번호. FK*/
	@ManyToOne(optional = false)
	@JoinColumn(name = "productboardentity_id")
	private ProductBoardEntity productBoard;
	
	/** 결제 금액. */
	@Column(nullable = false)
	private int price;
	
	/** 결제 수단. */
	@Column(nullable = false)
	private String payMethod;
	
	/** 결제 시간. */
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	/** 결제 성공 유무. */
	@Column(nullable = false)
	@ColumnDefault("1")
	private String isactive;

	
}
