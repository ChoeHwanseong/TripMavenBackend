package com.tripmaven.chatbot;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.csboard.CSBoardDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatbotService {

	private final ChatbotRepository chatbotRepository;
	private final ObjectMapper objectMapper;

	// 모든 질문 답변 조회
	@Transactional(readOnly = true)
	public List<ChatbotDto> getAllInquiriesAndAnswers() {
		List<ChatbotEntity> inquireEntityList = chatbotRepository.findAll();
		return objectMapper.convertValue(inquireEntityList,
				objectMapper.getTypeFactory().constructCollectionLikeType(List.class, ChatbotDto.class));
	}

	// 질문 답변 저장
	public ChatbotDto saveInquiryAndAnswer(ChatbotDto chatbotDto) {
		return ChatbotDto.toDto(chatbotRepository.save(chatbotDto.toEntity()));
	}

}
