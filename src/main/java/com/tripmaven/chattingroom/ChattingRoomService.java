package com.tripmaven.chattingroom;

import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD

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
=======
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.joinchatting.JoinChattingDto;
import com.tripmaven.joinchatting.JoinChattingEntity;
import com.tripmaven.joinchatting.JoinChattingRepository;
import com.tripmaven.members.model.MembersDto;
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

	public String getChattingRoomTopic(Long myId, Long yourId) {
		MembersEntity user1 = membersRepository.findById(myId).get();
		MembersEntity user2 = membersRepository.findById(yourId).get();
		List<JoinChattingEntity> user1List = joinChattingRepository.findAllByMember(user1);
		List<JoinChattingEntity> user2List = joinChattingRepository.findAllByMember(user2);
		long roomId = 0;
		//2명 다 들어있는 채팅방 찾기
		for(JoinChattingEntity user1entity : user1List) {
			for(JoinChattingEntity user2entity : user2List) {
				if(user1entity.getChattingRoom().getId() == user2entity.getChattingRoom().getId())
					roomId = user1entity.getChattingRoom().getId();
				break;
			}
			if(roomId != 0) break;
		}
		
		//채팅방 있으면 채팅방 id 반환
		if(roomId != 0) return String.valueOf(roomId);
		//없으면 만들어서 반환
		else {
			ChattingRoomEntity chatroom = new ChattingRoomEntity();
			chatroom = chattingRoomRepository.save(chatroom);
			
			JoinChattingEntity newEnteredChatRoom1 = JoinChattingEntity.builder().chattingRoom(chatroom).member(user1).build();
			joinChattingRepository.save(newEnteredChatRoom1);
			JoinChattingEntity newEnteredChatRoom2 = JoinChattingEntity.builder().chattingRoom(chatroom).member(user2).build();
			newEnteredChatRoom2 = joinChattingRepository.save(newEnteredChatRoom2);
			roomId = newEnteredChatRoom2.getChattingRoom().getId();
			return String.valueOf(roomId);
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

	public List<JoinChattingDto> getChattingYour(Long myId) {
		MembersEntity my = membersRepository.findById(myId).get();
		List<JoinChattingEntity> myChatList = joinChattingRepository.findAllByMember(my);
		List<JoinChattingEntity> yourChatList = new Vector<>();
		for(JoinChattingEntity chat:myChatList) {
			List<JoinChattingEntity> chattingRoomList = joinChattingRepository.findAllByChattingRoom(chat.getChattingRoom());
			for(JoinChattingEntity chatting:chattingRoomList) {
				if(chatting.getMember().getId()!=myId) yourChatList.add(chatting);
			}
		}
		return objectMapper.convertValue(yourChatList, objectMapper.getTypeFactory().defaultInstance().constructCollectionType(List.class,JoinChattingDto.class));
	}

}
>>>>>>> branch 'master' of https://github.com/ChoeHwanseong/TripMavenBackend.git
