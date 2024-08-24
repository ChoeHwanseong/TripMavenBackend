package com.tripmaven.auth.service;


import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tripmaven.auth.model.TokenEntity;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {
	
	private final TokenRepository tokenRepository;


    public void save(TokenEntity Token) {
        tokenRepository.save(Token);
    }

    public TokenEntity findByTokenValue(String token) {
        return tokenRepository.findByTokenValue(token).get();
    }

    public Boolean existsByRefresh(String tokenValue) {
        return tokenRepository.existsByTokenValue(tokenValue);
    }

    public void deleteByRefresh(String tokenValue) {
    	tokenRepository.deleteByTokenValue(tokenValue);
    }
    
    public Optional<TokenEntity> findByMembersId(long id) {
        return tokenRepository.findByMembersId(id);
    }

}
