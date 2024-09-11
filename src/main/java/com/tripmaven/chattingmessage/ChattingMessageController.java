package com.tripmaven.chattingmessage;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripmaven.members.service.MembersService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:58338")
public class ChattingMessageController {

	private final ChattingMessageService chattingMessageService;
	private final MembersService membersService;

	// 채팅 내역 저장하기
	@PostMapping("/save")
	public void saveMessage(@RequestBody Map<String, Object> map) {
		Long chattingRoomId = ((Integer) map.get("topic")).longValue();
		String text = (String) map.get("userMessage");
		Long sender = Long.parseLong(map.get("membersId").toString());
		// 서비스에서 메시지 저장하기
		chattingMessageService.saveMessage(chattingRoomId, text, sender);
	}

	// 채팅 내역 불러오기
	@GetMapping("/history/{chattingRoomId}")
	public List<Map<String, Object>> getMessages(@PathVariable("chattingRoomId") Long chattingRoomId) {
		return chattingMessageService.getMessages(chattingRoomId);
	}
}