package com.tripmaven.chattingmessage;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tripmaven.chattingroom.ChattingRoomEntity;

public interface ChattingMessageRepository extends JpaRepository<ChattingMessageEntity, Long>{

	List<ChattingMessageEntity> findAllByChattingRoom_Id(Long chattingRoomId);

	List<ChattingMessageEntity> findAllByChattingRoom_IdOrderByCreatedAtAsc(Long chattingRoomId);

}
