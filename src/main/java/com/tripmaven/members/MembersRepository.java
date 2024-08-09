package com.tripmaven.members;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRepository extends JpaRepository<MembersEntity, Long>{


    Optional<MembersEntity> findByemail(String email);
    void deleteByid(Long id);
	List<MembersEntity> findByname(String name);
	Optional<MembersEntity> findByid(Long id);


}
