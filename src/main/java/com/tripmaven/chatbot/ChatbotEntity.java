package com.tripmaven.chatbot;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Chatbot")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatbotEntity {

	/** 챗봇 고유 번호 PK*/
	@Id
	@SequenceGenerator(name="seq_chatbot",sequenceName = "seq_chatbot",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_chatbot")
	private long id;
	
	/** 질문 DB */
	@Lob
	@Column
	private String inquery;

	/** 답변 로그 */
	@Lob
	@Column
	private String answer;

}
