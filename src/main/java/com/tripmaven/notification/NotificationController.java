package com.tripmaven.notification;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.chattingroom.ChattingRoomService;
import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.members.service.MembersService;
import com.tripmaven.productboard.ProductBoardDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/noti")
@RequiredArgsConstructor
public class NotificationController {
	
	private final NotificationService notificationService;
	private final ObjectMapper mapper;
	
	//알림 테이블에 등록하는 메소드
	@PostMapping("/")
	public ResponseEntity<NotificationDto> postNotification(@RequestBody NotificationDto dto) {
		try {
			NotificationDto savedDto = notificationService.create(dto);
			return ResponseEntity.ok(savedDto);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	//알림 목록 불러오는 메소드
	@GetMapping("/{myId}")
	public ResponseEntity<List<NotificationDto>> getNotifications(@PathVariable(name = "myId") Long myId) {
		try {
			List<NotificationDto> notificationList = notificationService.readAll(myId);
			List<NotificationDto> filteredList = notificationList.stream().filter(t-> !(t.getIsRead().equals("1"))).toList();
			return ResponseEntity.ok(filteredList);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	//알림 목록 클릭시 isRead변경 메소드
}
