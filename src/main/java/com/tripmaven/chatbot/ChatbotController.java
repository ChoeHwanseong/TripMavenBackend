package com.tripmaven.chatbot;

import java.util.List;
import java.util.Map;

import org.apache.catalina.mapper.Mapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.csboard.CSBoardDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost")
public class ChatbotController {

	private final ChatbotService chatbotService;
	private final ObjectMapper mapper;

	//질문받고 답변 저장
	@PostMapping("/chatbot/post")
	public ResponseEntity<ChatbotDto> createInquiryAndAnswer(@RequestParam Map map) {
		System.out.println(map.get("inquery"));
		try {
			ChatbotDto dto = mapper.convertValue(map, ChatbotDto.class);
			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	//모든 질문과 답변 조회
	@GetMapping("/chatbot")
	@CrossOrigin
	public ResponseEntity<List<ChatbotDto>> getAllInquiriesAndAnswers() {
		try {
			List<ChatbotDto> dto = chatbotService.getAllInquiriesAndAnswers();
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json").body(dto);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}