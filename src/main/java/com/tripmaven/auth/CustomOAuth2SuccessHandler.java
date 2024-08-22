package com.tripmaven.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.auth.model.JWTUtil;
import com.tripmaven.auth.model.TokenEntity;
import com.tripmaven.auth.service.TokenService;
import com.tripmaven.auth.userdetail.CustomOauthUserDetails;
import com.tripmaven.members.model.MembersDto;
import com.tripmaven.members.service.MembersService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
		
    private final MembersService membersService;
    private final TokenService tokenService;
    private final JWTUtil jwtUtil;
    
    
    
	public CustomOAuth2SuccessHandler(MembersService membersService,
			TokenService tokenService, JWTUtil jwtUtil) {
		this.membersService = membersService;
		this.tokenService = tokenService;
		this.jwtUtil = jwtUtil;
	}



	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		CustomOauthUserDetails customUserDetails = (CustomOauthUserDetails) authentication.getPrincipal();
		// CustomUserDetails에서 사용자 이름(이메일)을 추출합니다.
        String username = customUserDetails.getUsername();

        // 사용자 이름을 사용하여 JWT를 생성합니다.
        String access  = jwtUtil.generateToken(username,"access",600000L);
        String refresh  = jwtUtil.generateToken(username,"refresh",86400000L);
        
        
        MembersDto membersDto = membersService.searchByMemberEmail(username);
        
        String membersId = String.valueOf(membersDto.getId());
        if (membersDto != null) {
            TokenEntity token = TokenEntity.builder()
                    .status("activated")
                    .userAgent(request.getHeader("User-Agent"))
                    .members(membersDto.toEntity())
                    .tokenValue(refresh)
                    .expiresIn(86400000L)
                    .build();
            tokenService.save(token);
        }

        // 응답 헤더에 JWT를 'Bearer' 토큰으로 추가합니다.
        response.addHeader("Authorization", "Bearer " + access);
        
        // 클라이언트가 Authorization 헤더를 읽을 수 있도록, 해당 헤더를 노출시킵니다.
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        // 여기서 부터 사용자 정보를 응답 바디에 추가하는 코드입니다.
        // 사용자의 권한이나 추가 정보를 JSON 형태로 변환하여 응답 바디에 포함시킬 수 있습니다.
        boolean isAdmin = customUserDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean isGuide = customUserDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_GUIDE"));
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("username", username);
        responseBody.put("isAdmin", isAdmin);
        responseBody.put("isGuide", isGuide);
        responseBody.put("refresh",refresh);
        responseBody.put("membersId",membersId);
        

        // ObjectMapper를 사용하여 Map을 JSON 문자열로 변환합니다.
        String responseBodyJson = new ObjectMapper().writeValueAsString(responseBody);
        
        // 응답 컨텐츠 타입을 설정합니다.
        response.setContentType("application/json");

        // 응답 바디에 JSON 문자열을 작성합니다.
        response.getWriter().write(responseBodyJson);
        response.getWriter().flush();
       
	}
	

}
