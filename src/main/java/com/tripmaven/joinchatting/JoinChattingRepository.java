package com.tripmaven.joinchatting;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinChattingRepository extends JpaRepository<JoinChattingEntity, Long>{
	
	Boolean existsByUserId(Long member);

	JoinChattingEntity findByUserId(Long member);

	int countByUserId(Long id);
=======
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tripmaven.chattingroom.ChattingRoomEntity;
import com.tripmaven.members.model.MembersEntity;

public interface JoinChattingRepository extends JpaRepository<JoinChattingEntity, Long>{
	


	long countByMember(MembersEntity member);

	List<JoinChattingEntity> findAllByMember(MembersEntity user1);

	List<JoinChattingEntity> findAllByChattingRoom(ChattingRoomEntity chattingRoom);
>>>>>>> branch 'master' of https://github.com/ChoeHwanseong/TripMavenBackend.git

}
