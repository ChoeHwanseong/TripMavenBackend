package com.tripmaven.productboard;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.tripmaven.likey.LikeyEntity;
import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.productevaluation.ProductEvaluationEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "ProductBoard")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class ProductBoardEntity {

	/** 가이드 상품 고유 번호. PK*/
	@Id
	@SequenceGenerator(name="seq_productboard",sequenceName = "seq_productboard",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_productboard")
	private long id;
	
	/** 회원 고유 번호. FK*/
	@ManyToOne(optional = false)
	@JoinColumn(name="membersentity_id")
	private MembersEntity member;
	
//	/** 상품 찜 고유 번호. FK*/
//	@ManyToOne(optional = false)
//	@JoinColumn(name="likeyentity_id")
//	private LikeyEntity likey;
//	
//	
//	/** AI평가 고유 번호. FK*/
//	@ManyToOne(optional = false)
//	@JoinColumn(name="productevaluationEntity_id")
//	private ProductEvaluationEntity productevaluation;
	
	/** 제목 */
	@Column(length = 20, nullable = false)
	private String title;
	
	/** 내용 */
	@Lob
	@Column
	private String content;	
	
	/** 생성날짜 */
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	/** 활성화 유무 */
	@Column
	@ColumnDefault("1")
	private String isActive;
	
	/** Al평가 유무 */
	@ColumnDefault("1")
	private String isEvaluation;
	
	/** 여행도시 */
	@Column(length = 30, nullable = false)
	private String city;
	
	/** 수정날짜 */
	private LocalDateTime updatedAt;
	
	/** 수정여부 */
	@Column
	@ColumnDefault("0")
	private String isUpdate;
	
	/** 삭제날짜 */
	private LocalDateTime deletedAt;
	
	/** 삭제여부 */
	@Column
	@ColumnDefault("0")
	private String isDelete;
	
	/**파일*/
	@Column
	private String files;
	
	//tripdays 들어와야함. 
	
}
