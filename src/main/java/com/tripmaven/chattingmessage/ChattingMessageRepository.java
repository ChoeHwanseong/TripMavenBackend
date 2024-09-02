package com.tripmaven.chattingmessage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingMessageRepository extends JpaRepository<ChattingMessageEntity, Long>{

	List<ChattingMessageEntity> findByUserId(Long memberId);
	
	List<ChattingMessageEntity> findAllByChattingRoom_Id(Long chattingRoomId);
}
