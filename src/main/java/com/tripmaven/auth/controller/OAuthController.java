package com.tripmaven.auth.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.auth.model.JWTTOKEN;
import com.tripmaven.auth.model.TokenDTO;
import com.tripmaven.auth.service.TokenService;
import com.tripmaven.members.model.MembersDto;
import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.members.service.MembersRepository;
import com.tripmaven.members.service.MembersService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {
	
	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;


    private final MembersRepository membersRepository;
    private final TokenService tokenService;
    private final JWTTOKEN jwttoken;
    private final ObjectMapper mapper;
   

    @CrossOrigin
    @GetMapping("/oauth2/code/kakao")
    public void kakaoLogin(@RequestParam String code, HttpServletResponse response) throws IOException {
        log.info("code = {}", code);

        // 액세스 토큰을 요청하기 위한 URL 및 헤더 설정
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String tokenRequestBody = "grant_type=authorization_code"
                + "&client_id=" + kakaoClientId
                + "&redirect_uri=" + kakaoRedirectUri
                + "&code=" + code;

        // 토큰 요청
        HttpEntity<String> tokenRequestEntity = new HttpEntity<>(tokenRequestBody, tokenHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> tokenResponse = restTemplate.exchange(tokenUrl, HttpMethod.POST, tokenRequestEntity, String.class);
        log.info("token response = {}", tokenResponse.getBody());

        Map<String, Object> tokenJson = mapper.readValue(tokenResponse.getBody(), Map.class);
        String accessToken = tokenJson.get("access_token").toString();
        log.info("accessToken = {}", accessToken);

        // 사용자 정보를 요청하기 위한 URL 및 헤더 설정
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.set("Authorization", "Bearer " + accessToken);

        // 사용자 정보 요청
        HttpEntity<String> userInfoRequestEntity = new HttpEntity<>(userInfoHeaders);
        ResponseEntity<String> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userInfoRequestEntity, String.class);
        log.info("user info response = {}", userInfoResponse.getBody());

        Map<String, Object> userJson = mapper.readValue(userInfoResponse.getBody(), Map.class);
        // 이메일 정보 추출
        Map<String, Object> kakaoAccount = (Map<String, Object>) userJson.get("kakao_account");
        String email = kakaoAccount != null && kakaoAccount.containsKey("email") ?
                (String) kakaoAccount.get("email") : "이메일 정보가 없습니다.";
        log.info("email = {}", email);

        Optional<MembersEntity> optionalMembers = membersRepository.findByEmailAndLoginType(email, "kakao");

        if (optionalMembers.isPresent()) {
            MembersEntity membersEntity = optionalMembers.get();
            membersEntity.setSnsAccessToken(accessToken);
            membersRepository.save(membersEntity);

            // JWT 토큰 발급
            Long accessExpiredMs = 600000L;
            String accessTokenJwt = jwttoken.generateToken(email, "access", accessExpiredMs);
            Long refreshExpiredMs = 86400000L;
            String refreshTokenJwt = jwttoken.generateToken(email, "refresh", refreshExpiredMs);

            TokenDTO token = TokenDTO.builder()
                    .status("activated")
                    .userAgent(response.getHeader("User-Agent"))
                    .members(membersEntity)
                    .tokenValue(refreshTokenJwt)
                    .expiresIn(refreshExpiredMs)
                    .build();

            tokenService.save(token.toEntity());

            // 로그인 성공 후 URL에 토큰 정보 포함
            String redirectUrl = String.format("http://localhost:58337?access=%s&refresh=%s&isAdmin=%s",
                    accessTokenJwt, refreshTokenJwt, membersEntity.getRole());

            response.sendRedirect(redirectUrl);
            log.info("로그인 성공: {}", email);
        } else {
            log.info("회원가입 필요: {}", email);
            response.sendRedirect("http://localhost:58337/");
        }
    }

}
