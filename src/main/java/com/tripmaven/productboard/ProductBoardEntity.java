package com.tripmaven.productboard;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.tripmaven.members.MembersEntity;

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
	private String isactive;
	
	/** Al평가 유무 */
	@Column(nullable = false)
	@ColumnDefault("1")
	private String isevaluation;
	
	/** 여행도시 */
	@Column(length = 30, nullable = false)
	private String city;
	
	/** 수정날짜 */
	@CreationTimestamp
	private LocalDateTime updatedAt;
	
	/** 수정여부 */
	@Column
	@ColumnDefault("0")
	private String isupdate;
	
	/** 삭제날짜 */
	@CreationTimestamp
	private LocalDateTime deletedAt;
	
	/** 삭제여부 */
	@Column
	@ColumnDefault("0")
	private String isdelete;
	
	/**파일*/
	@Column
	private String files;
}
