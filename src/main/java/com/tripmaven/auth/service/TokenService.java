package com.tripmaven.auth.service;


import org.springframework.stereotype.Service;

import com.tripmaven.auth.model.TokenDTO;
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

    public TokenDTO findByTokenValue(String token) {
        return TokenDTO.toDto(tokenRepository.findByTokenValue(token).get());
    }

    public Boolean existsByRefresh(String tokenValue) {
        return tokenRepository.existsByTokenValue(tokenValue);
    }

    public void deleteByRefresh(String tokenValue) {
    	tokenRepository.deleteByTokenValue(tokenValue);
    }

}
