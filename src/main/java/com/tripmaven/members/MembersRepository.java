package com.tripmaven.members;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tripmaven.productboard.ProductBoardEntity;

@Repository
public interface MembersRepository extends JpaRepository<MembersEntity, Long>{

	Optional<MembersEntity> findByEmail(String email);
	

}
