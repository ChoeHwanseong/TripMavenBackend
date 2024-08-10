package com.tripmaven.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.auth.CustomOauthUserDetails;

import com.tripmaven.auth.model.JWTTOKEN;
import com.tripmaven.auth.model.TokenDTO;
import com.tripmaven.auth.service.TokenService;
import com.tripmaven.members.model.MembersDto;
import com.tripmaven.members.service.MembersService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter{
//	private final AuthenticationManager authenticationManager;
//	private final JWTTOKEN jwttoken;
//	
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
//		String loginId = obtainUsername(request);
//		String password = obtainPassword(request);
//		
//		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password, null);
//		return authenticationManager.authenticate(authenticationToken);
//	}
//
//	//로그인 성공 시 
//	@Override
//	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//			Authentication authResult) throws IOException, ServletException {
//		//Email추출
//		CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
//		String email = customUserDetails.getUsername();
//		
//		//role 추출
//		Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
//		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//		GrantedAuthority auth = iterator.next();
//		String role = auth.getAuthority();
//		
//		//JWT에 토큰 생성 요청 1시간짜리
//		String token = jwttoken.generateToken(email, role, 60*60*1000L);
//		
//		//JWT를 response에 담아서 응답(header 부분에)
//		// key : "Authorization"
//        // value : "Bearer " (인증방식) + token
//		response.addHeader(jwttoken.AUTHORIZATION, jwttoken.BEARER + token);
//		
//	}
//
//	//로그인 실패시
//	@Override
//	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException failed) throws IOException, ServletException {
//		 response.setStatus(401);
//	}
	private final Long accessExpiredMs;
    private final Long refreshExpiredMs;

    private final MembersService membersService;
    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;
    private final JWTTOKEN jwttoken;

    // 생성자를 통해 AuthenticationManager와 JWTUtil의 인스턴스를 주입받습니다.
    public LoginFilter(MembersService membersService, TokenService tokenService, AuthenticationManager authenticationManager, JWTTOKEN jwttoken) {
        this.membersService = membersService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.jwttoken = jwttoken;
        refreshExpiredMs = 86400000L;
        accessExpiredMs = 600000L;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
            // 요청 본문에서 사용자의 로그인 데이터를 추출합니다.
        	String loginId = obtainUsername(request);
        	String password = obtainPassword(request);
           
            // 사용자 이름과 비밀번호를 기반으로 AuthenticationToken을 생성합니다. 이 토큰은 사용자가 제공한 이메일과 비밀번호를 담고 있으며, 이후 인증 과정에서 사용됩니다.
        	UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password, null);
            // AuthenticationManager를 사용하여 실제 인증을 수행합니다. 이 과정에서 사용자의 이메일과 비밀번호가 검증됩니다.
        	return authenticationManager.authenticate(authenticationToken);
    }

    // 로그인 성공 시 실행되는 메소드입니다. 인증된 사용자 정보를 바탕으로 JWT를 생성하고, 이를 응답 헤더에 추가합니다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        // 인증 객체에서 CustomUserDetails를 추출합니다.
        CustomOauthUserDetails customOauthUserDetails = (CustomOauthUserDetails) authentication.getPrincipal();

        // CustomUserDetails에서 사용자 이름(이메일)을 추출합니다.
        String username = customOauthUserDetails.getUsername();

        // 사용자 이름을 사용하여 JWT를 생성합니다.
        String access  = jwttoken.generateToken(username,"access",accessExpiredMs);
        String refresh  = jwttoken.generateToken(username,"refresh",refreshExpiredMs);
        MembersDto membersDto = membersService.searchByMemberEmail(username);
        if(membersDto == null){
            TokenDTO token = TokenDTO.builder()
                    .status("activated")
                    .userAgent(request.getHeader("User-Agent"))
                    .members(membersDto.toEntity())
                    .tokenValue(refresh)
                    .expiresIn(refreshExpiredMs)
                    .build();

            tokenService.save(token.toEntity());
        }

        // 응답 헤더에 JWT를 'Bearer' 토큰으로 추가합니다.
        response.addHeader("Authorization", "Bearer " + access);

        // 클라이언트가 Authorization 헤더를 읽을 수 있도록, 해당 헤더를 노출시킵니다.
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        // 여기서 부터 사용자 정보를 응답 바디에 추가하는 코드입니다.
        // 사용자의 권한이나 추가 정보를 JSON 형태로 변환하여 응답 바디에 포함시킬 수 있습니다.
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();
		String role = auth.getAuthority();
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("username", username);
        responseBody.put("role", role);
        responseBody.put("refresh",refresh);

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
