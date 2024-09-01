package com.tripmaven.joinchatting;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinChattingRepository extends JpaRepository<JoinChattingEntity, Long>{
	
	Boolean existsByUserId(Long member);

	JoinChattingEntity findByUserId(Long member);

	int countByUserId(Long id);

}
