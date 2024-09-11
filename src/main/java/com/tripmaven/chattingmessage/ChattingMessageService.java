package com.tripmaven.chattingmessage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.chattingroom.ChattingRoomEntity;
import com.tripmaven.chattingroom.ChattingRoomRepository;
import com.tripmaven.joinchatting.JoinChattingEntity;
import com.tripmaven.joinchatting.JoinChattingRepository;
import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.members.service.MembersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChattingMessageService {

	private final ChattingMessageRepository chattingMessageRepository;
	private final ChattingRoomRepository chattingRoomRepository;
	private final JoinChattingRepository joinChattingRepository;
	private final MembersRepository membersRepository;
	private final ObjectMapper objectMapper;


	
	//메세지 저장
	 @Transactional
	  public void saveMessage(Long chattingRoomId, String text, Long sender) {
		
        ChattingRoomEntity chattingRoom = chattingRoomRepository.findById(chattingRoomId).orElseThrow();
        
            // 새로운 메시지 엔티티 생성
            ChattingMessageEntity newMessage = ChattingMessageEntity.builder()
                .chattingRoom(chattingRoom)
                .text(text)
                .sender(sender)
                .createdAt(LocalDateTime.now()) 
                .isActive("1") 
                .isDelete("0") 
                .build();
            
            chattingMessageRepository.save(newMessage);
        }


	 @Transactional
		public List<Map<String, Object>> getMessages(Long chattingRoomId) {

			List<ChattingMessageEntity> chatMessages = chattingMessageRepository.findAllByChattingRoom_IdOrderByCreatedAtAsc(chattingRoomId);
			
			return chatMessages.stream().map(chatMessage -> {
				Map<String, Object> messageMap = new HashMap<>();
				messageMap.put("text", chatMessage.getText());
				messageMap.put("sender", chatMessage.getSender());
				messageMap.put("timestamp", chatMessage.getCreatedAt());
				return messageMap;
			}).collect(Collectors.toList());
		}
}
