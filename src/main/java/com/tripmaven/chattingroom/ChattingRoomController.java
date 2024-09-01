package com.tripmaven.chattingroom;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
<<<<<<< HEAD
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripmaven.members.service.MembersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChattingRoomController {

	private final ChattingRoomService chattingRoomService;
	 private final MembersService membersService;

	    @GetMapping("/topic/{id}")
	    public ResponseEntity<String> getChattingRoomTopic(@PathVariable("id") Long id) {
	        String email = membersService.searchByMemberID(id).getEmail();
	        try {
	            String chattingRoomTopic = chattingRoomService.getChattingRoomTopic(email);
	            return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json")
	                    .body(chattingRoomTopic);
	        } catch (Exception e) {
	        	
	        	
	            e.printStackTrace();
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	// 채팅 내역 저장 ->
	// 파이썬에서 하는거임

	// 채팅 내역 불러오기
	@GetMapping("/users")
	public ResponseEntity<List<ChattingRoomDto>> getA11ChatRoom() {
		try {
			List<ChattingRoomDto> chattingRoom = chattingRoomService.getAllChattingRoom();
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json").body(chattingRoom);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
}
=======
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripmaven.joinchatting.JoinChattingDto;
import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.members.service.MembersService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChattingRoomController {

	private final ChattingRoomService chattingRoomService;
	private final MembersService membersService;

	@GetMapping("/topic/{myId}/{yourId}")
	public ResponseEntity<String> getChattingRoomTopic(@PathVariable("myId") Long myId, @PathVariable("yourId") Long yourId) {
		try {
			String chattingRoomTopic = chattingRoomService.getChattingRoomTopic(myId, yourId);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json")
					.body(chattingRoomTopic);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	채팅 내역 저장 ->
//	파이썬에서 하는거임
//	
//	채팅 내역 불러오기
	@GetMapping("/users")
	public ResponseEntity<List<ChattingRoomDto>> getA11ChatRoom() {
		try {
			List<ChattingRoomDto> chattingRoom = chattingRoomService.getAllChattingRoom();
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json").body(chattingRoom);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
package com.tripmaven.chattingroom;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripmaven.joinchatting.JoinChattingDto;
import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.members.service.MembersService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChattingRoomController {

	private final ChattingRoomService chattingRoomService;
	private final MembersService membersService;

	@GetMapping("/topic/{myId}/{yourId}")
	public ResponseEntity<String> getChattingRoomTopic(@PathVariable("myId") Long myId, @PathVariable("yourId") Long yourId) {
		try {
			String chattingRoomTopic = chattingRoomService.getChattingRoomTopic(myId, yourId);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json")
					.body(chattingRoomTopic);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	채팅 내역 저장 ->
//	파이썬에서 하는거임
//	
//	채팅 내역 불러오기
	@GetMapping("/users")
	public ResponseEntity<List<ChattingRoomDto>> getA11ChatRoom() {
		try {
			List<ChattingRoomDto> chattingRoom = chattingRoomService.getAllChattingRoom();
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json").body(chattingRoom);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
package com.tripmaven.chattingroom;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripmaven.joinchatting.JoinChattingDto;
import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.members.service.MembersService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChattingRoomController {

	private final ChattingRoomService chattingRoomService;
	private final MembersService membersService;

	@GetMapping("/topic/{myId}/{yourId}")
	public ResponseEntity<String> getChattingRoomTopic(@PathVariable("myId") Long myId, @PathVariable("yourId") Long yourId) {
		try {
			String chattingRoomTopic = chattingRoomService.getChattingRoomTopic(myId, yourId);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json")
					.body(chattingRoomTopic);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	채팅 내역 저장 ->
//	파이썬에서 하는거임
//	
//	채팅 내역 불러오기
	@GetMapping("/users")
	public ResponseEntity<List<ChattingRoomDto>> getA11ChatRoom() {
		try {
			List<ChattingRoomDto> chattingRoom = chattingRoomService.getAllChattingRoom();
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json").body(chattingRoom);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	//로그인한 사람 기준으로 채팅하는 상대방 모두 얻기
	@GetMapping("/topic/{myId}")
	public ResponseEntity<List<JoinChattingDto>> getChattingYour(@PathVariable("myId") Long myId) {
		try {
			List<JoinChattingDto> yourList = chattingRoomService.getChattingYour(myId);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json")
					.body(yourList);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
>>>>>>> branch 'master' of https://github.com/ChoeHwanseong/TripMavenBackend.git
