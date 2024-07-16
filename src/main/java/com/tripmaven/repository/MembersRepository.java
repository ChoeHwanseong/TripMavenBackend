package com.tripmaven.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRepository extends JpaRepository<MembersEntity,Long> {

	//아이디 조회용(단일 레코드 반환:Optional<엔터티타입>)
	Optional<MembersEntity> findByEmail(String username);

}
