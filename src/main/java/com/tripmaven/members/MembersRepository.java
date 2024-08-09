package com.tripmaven.members;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRepository extends JpaRepository<MembersEntity, Long>{
    Optional<MembersEntity> findByEmail(String email);
	List<MembersEntity> findByName(String name);


}
