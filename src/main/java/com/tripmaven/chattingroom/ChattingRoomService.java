package com.tripmaven.chattingroom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.joinchatting.JoinChattingEntity;
import com.tripmaven.joinchatting.JoinChattingRepository;
import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.members.service.MembersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChattingRoomService {

	private final ChattingRoomRepository chattingRoomRepository;
	private final JoinChattingRepository joinChattingRepository;
	private final MembersRepository membersRepository;
	private final ObjectMapper objectMapper;

	public String getChattingRoomTopic(String email) {
		if (membersRepository.existsByEmail(email)) {
			MembersEntity user = membersRepository.findByEmail(email).get();

			// if 있오면 돌려주기
			if (joinChattingRepository.countByUserId(user.getId()) == 1) { 
				JoinChattingEntity isEntered = joinChattingRepository.findByUserId(user.getId());
				ChattingRoomEntity chatroom = isEntered.getChattingRoom();
				chatroom.setIsActive(1);
				chatroom = chattingRoomRepository.save(chatroom);
				Long roomId = chatroom.getId();
				return roomId.toString();
			} else { // 없으면 생성해서 돌려주기
				ChattingRoomEntity chatroom = new ChattingRoomEntity();
				chatroom.setIsActive(1);
				chatroom = chattingRoomRepository.save(chatroom);

				JoinChattingEntity newEnteredChatRoom = new JoinChattingEntity();
				newEnteredChatRoom.setChattingRoom(chatroom);
				newEnteredChatRoom.setMember(user);
				newEnteredChatRoom = joinChattingRepository.save(newEnteredChatRoom);
				Long roomId = chatroom.getId();
				return roomId.toString();
			}
		} else {
			return "temp";
		}
	}

	public List<ChattingRoomDto> getAllChattingRoom() {
		List<ChattingRoomEntity> chatrooms = chattingRoomRepository.findAll();
		List<ChattingRoomDto> chatroomDTOs = new ArrayList<ChattingRoomDto>();
		for (ChattingRoomEntity room : chatrooms) {
			if (room.getIsDelete() == 0) {
				chatroomDTOs.add(ChattingRoomDto.toDTO(room));
			}
		}
		return chatroomDTOs;
	}

}
