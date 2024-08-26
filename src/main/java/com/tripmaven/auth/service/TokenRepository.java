package com.tripmaven.auth.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tripmaven.auth.model.TokenEntity;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long>{
	Optional<TokenEntity> findByTokenValue(String tokenValue);

    Boolean existsByTokenValue(String refresh);

    void deleteByTokenValue(String refresh);

    Optional<TokenEntity> findByMembersId(Long id);

	
}
