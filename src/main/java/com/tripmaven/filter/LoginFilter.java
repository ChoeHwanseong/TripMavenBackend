package com.tripmaven.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.auth.model.JWTUtil;
import com.tripmaven.auth.service.CustomUserDetails;
import com.tripmaven.members.model.MembersDto;
import com.tripmaven.members.service.MembersService;
import com.tripmaven.token.TokenEntity;
import com.tripmaven.token.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class LoginFilter extends UsernamePasswordAuthenticationFilter{

	private final Long accessExpiredMs;
    private final Long refreshExpiredMs;

    private final MembersService membersService;
    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    // 생성자를 통해 AuthenticationManager와 JWTUtil의 인스턴스를 주입받습니다.
    public LoginFilter(MembersService membersService, TokenService tokenService, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.membersService = membersService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        refreshExpiredMs = 86400000L;
        accessExpiredMs = 600000L;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
        	
        	String loginId = request.getParameter("email");
            if (loginId == null || loginId.isEmpty()) {
                loginId = "NONE_PROVIDED";
            }
            String password = obtainPassword(request);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password);
            return authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
        	throw new AuthenticationServiceException("인증 처리 중 오류가 발생했습니다.", e);
        }
    }

    // 로그인 성공 시 실행되는 메소드입니다. 인증된 사용자 정보를 바탕으로 JWT를 생성하고, 이를 응답 헤더에 추가합니다.	
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        // 인증 객체에서 CustomUserDetails를 추출합니다.
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        // CustomUserDetails에서 사용자 이름(이메일)을 추출합니다.
        String username = customUserDetails.getUsername();

        // 사용자 이름을 사용하여 JWT를 생성합니다.
        String access  = jwtUtil.generateToken(username,"access",accessExpiredMs);
        String refresh  = jwtUtil.generateToken(username,"refresh",refreshExpiredMs);
        MembersDto membersDto = membersService.searchByMemberEmail(username);
        String membersId = String.valueOf(membersDto.getId());
        if (membersDto != null) {
            TokenEntity token = TokenEntity.builder()
                    .status("activated")
                    .userAgent(request.getHeader("User-Agent"))
                    .members(membersDto.toEntity())
                    .tokenValue(refresh)
                    .expiresIn(refreshExpiredMs)
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
        responseBody.put("loginType","local");

        // ObjectMapper를 사용하여 Map을 JSON 문자열로 변환합니다.
        String responseBodyJson = new ObjectMapper().writeValueAsString(responseBody);
        
        // 응답 컨텐츠 타입을 설정합니다.
        response.setContentType("application/json");

        // 응답 바디에 JSON 문자열을 작성합니다.
        response.getWriter().write(responseBodyJson);
        response.getWriter().flush();
    }

    // 로그인 실패 시 실행되는 메소드입니다. 실패한 경우, HTTP 상태 코드 401을 반환합니다.
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        // failed 객체로부터 최종 원인 예외를 찾습니다.
        Throwable rootCause = failed;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }

        // rootCause를 기반으로 오류 메시지를 설정합니다.
        String message;
        if (rootCause instanceof UsernameNotFoundException) {
            message = "존재하지 않는 이메일입니다.";
        } else if (rootCause instanceof BadCredentialsException) {
            message = "잘못된 비밀번호입니다.";
        } else if (rootCause instanceof DisabledException) {
            message = "계정이 비활성화되었습니다.";
        } else if (rootCause instanceof LockedException) {
            message = "계정이 잠겨 있습니다.";
        } else {
            // 다른 예외들을 처리
            message = "인증에 실패했습니다.";
        }

        // 응답 데이터를 준비합니다.
        Map<String, Object> responseData = new HashMap<>();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        responseData.put("timestamp", LocalDateTime.now().toString());
        responseData.put("status", HttpStatus.BAD_REQUEST.value());
        responseData.put("error", "Unauthorized");
        responseData.put("message", message);
        responseData.put("path", request.getRequestURI());

        // 응답을 보냅니다.
        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(responseData);
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();
        } catch (IOException ignored) {
        }
    }
	
}
