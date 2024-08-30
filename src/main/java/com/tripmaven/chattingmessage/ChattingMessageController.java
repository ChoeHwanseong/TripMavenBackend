//package com.tripmaven.chattingmessage;
//
//import java.util.List;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tripmaven.members.service.MembersService;
//
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/chat")
//public class ChattingMessageController {
//
//	private final ChattingMessageService chattingMessageService;

//	// 채팅 내액 블러오기 - 회원별 채팅 내용
//	@GetMapping("/history")
//	public ResponseEntity<List<ChattingMessageDto>> getMessagesByUser(HttpServletRequest request) {
//		String email = isMember.checkContainsUseremail(request);
//		try {
//			List<ChattingMessageDto> messages = chattingMessageService.getAllMessagesByUser(email);
//			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(messages);
//		}catch(Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	// 채팅 내역 불러오기
//	@GetMapping("/{chatroomId}")
//	public ResponseEntity<List<ChattingMessageDto>> getMessagesByChatRoomId(@PathVariable("chatroomId") String chatroomId, HttpServletRequest request){
//
//		try {
//			List<ChattingMessageDto> messages = chattingMessageService.getAllMessagesByChattingRoom(Long.parseLong(chatroomId));
//			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(messages);
//		}catch(Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//}