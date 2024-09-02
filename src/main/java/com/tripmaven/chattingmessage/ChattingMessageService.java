//package com.tripmaven.chattingmessage;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.stereotype.Service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tripmaven.chattingroom.ChattingRoomEntity;
//import com.tripmaven.chattingroom.ChattingRoomRepository;
//import com.tripmaven.joinchatting.JoinChattingEntity;
//import com.tripmaven.joinchatting.JoinChattingRepository;
//import com.tripmaven.members.model.MembersEntity;
//import com.tripmaven.members.service.MembersRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class ChattingMessageService {
//
//	private final ChattingMessageRepository chattingMessageRepository;
//	private final ChattingRoomRepository chattingRoomRepository;
//	private final JoinChattingRepository joinChattingRepository;
//	private final MembersRepository membersRepository;
//	private final ObjectMapper objectMapper;
//
//	public List<ChattingMessageDto> getAllMessagesByUser(String email) {
//
//		MembersEntity member = membersRepository.findByEmail(email).get();
//		List<ChattingMessageEntity> messages = new ArrayList<ChattingMessageEntity>();
//
//		if (joinChattingRepository.existsById(member.getId())) {
//			JoinChattingEntity isEntered = joinChattingRepository.findAllByMember(member.getId());
//			ChattingRoomEntity chattingroom = isEntered.getChattingRoom();
//			if (chattingroom.getIsActive() == 1) {
//				messages = chattingMessageRepository.findAllByChattingRoom_Id(chattingroom.getId());
//				return messages.stream().map(msg->ChattingMessageDto.toDto(msg)).collect(Collectors.toList());
//			} else
//				throw new IllegalArgumentException("활성화된 채팅방 없음");
//		}
//		return null;
//	}
//
//	public List<ChattingMessageDto> getAllMessagesByChattingRoom(long chattingRoom) {
//		// 채팅방아이디로 구분, 채팅방 있으면 톨려주기
//		if(chattingRoomRepository.existsById(chattingRoom)) {
//		List<ChattingMessageEntity> messages = chattingMessageRepository.findAllByChattingRoom_Id(chattingRoom);
//		return messages.stream().map(msg->ChattingMessageDto.toDto(msg)).collect(Collectors.toList());
//		}
//		return null;
//	}
//}
