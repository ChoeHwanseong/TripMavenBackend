package com.tripmaven.chatbot;



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
public class ChatbotDto {

	private long id;
	private String inquery;
	private String answer;
	
	//DTO를 ENTITY로 변환하는 메소드
	public ChatbotEntity toEntity() {
		return ChatbotEntity.builder()
				.id(id)
				.inquery(inquery)
				.answer(answer)
				.build();
	}
	
	//ENTITY를 DTO로 변환하는 메소드
	public static ChatbotDto toDto(ChatbotEntity chatbotEntity) {
		return ChatbotDto.builder()
				.id(chatbotEntity.getId())
				.inquery(chatbotEntity.getInquery())
				.answer(chatbotEntity.getAnswer())
				.build();
	}	
}
