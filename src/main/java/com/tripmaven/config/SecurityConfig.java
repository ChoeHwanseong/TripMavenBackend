package com.tripmaven.config;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tripmaven.auth.CustomUserDetails;
import com.tripmaven.auth.CustomUserDetailsService;
import com.tripmaven.auth.model.JWTTOKEN;
import com.tripmaven.auth.model.JWTUtil;
import com.tripmaven.auth.service.TokenService;
import com.tripmaven.filter.JWTFilter;
import com.tripmaven.filter.LoginFilter;
import com.tripmaven.members.service.MembersService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

	private final AuthenticationConfiguration configuration;
	private final JWTUtil jwtUtil;
    private final TokenService tokenService;
    private final CustomUserDetailsService customUserDetailsService;
    private final MembersService membersService;
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
		return configuration.getAuthenticationManager();
	};
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		
		http.authorizeHttpRequests(req-> req
			.requestMatchers("/admin").hasRole("ADMIN") // /admin으로 시작하는 페이지는 "ADMIN" ROLE을 가진 사람만
			.requestMatchers("/guide").hasAnyRole("ADMIN","GUIDE") // /guide으로 시작하는 페이지는 "ADMIN","GUIDE" ROLE을 가진 사람 들만
			//.anyRequest().authenticated()//위의 경로를 제외한 모든 요청에 대해서는 인증된 사용자만 접근
			.requestMatchers("/login").permitAll()
			.anyRequest().permitAll() //위의 경로를 제외한 모든 요청에 대해서는 접근허용
				);
		
		//로그인 설정
		http.formLogin(login->login
				.disable()
//				.loginPage("/login") //로그인 페이지 설정
//				.usernameParameter("email")
//				.passwordParameter("password")
//				.loginProcessingUrl("/loginProcess")//로그인 처리 URL(기본값:/login). 시큐리티가 로그인처리
//				.successHandler(new AuthenticationSuccessHandler() {
//					//로그인 성공시 처리할 작업 작성-defaultSuccessUrl는 무시된다
//					@Override
//					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//							Authentication authentication) throws IOException, ServletException {
//						CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//						String email = customUserDetails.getUsername();
//						
//						//role 추출
//						Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//						Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//						GrantedAuthority auth = iterator.next();
//						String role = auth.getAuthority();
//						
//						//JWT에 토큰 생성 요청 1시간짜리
//						String token = jwtUtil.createJwt(email, role, 60*60*1000L);
//						
//						//JWT를 response에 담아서 응답(header 부분에)
//						// key : "Authorization"
//				        // value : "Bearer " (인증방식) + token
//						response.addHeader("Authorization", "Bearer " + token);					
//					}
//				})
//				.permitAll()
//				
		);
		
		http.oauth2Login(auth-> auth
				//.loginPage("/login")
				.defaultSuccessUrl("http://localhost:58337/home", true)
				//.failureUrl("/login?error=true") //에러나면 갈 페이지 어케하까
				.permitAll()
		);
		
		//로그아웃 설정
		http.logout(logout->logout
			.logoutUrl("/logout")//기본값 /logout		
			.permitAll()
		);
		
		/*csrf 비활성화
		rest api를 이용한 서버라면, session 기반 인증과는 다르게 stateless하기 때문에 서버에 인증정보를 보관하지 않는다.
		rest api에서 client는 권한이 필요한 요청을 하기 위해서는 요청에 필요한 인증 정보를(OAuth2, jwt토큰 등)을 포함시켜야 한다.
		따라서 서버에 인증정보를 저장하지 않기 때문에 굳이 불필요한 csrf 코드들을 작성할 필요가 없다.*/
		http.csrf(csrf->csrf.disable()); 
	
		http.sessionManagement(session->session
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //JWT를 통한 인증, 인가 작업을 위해서는 세션을 무상태 (STATELESS) 로 설정하는 것이 중요!
		);
		
		// http basic 인증 방식 disable 설정 JWT, OAuth2 등 복잡한 인증 로직을 구현하려면 HTTP Basic 인증을 비활성화하는 것이 좋습니다.
		http.httpBasic(basic-> basic.disable());
		
//		http.addFilterAt(new LoginFilter(authenticationManager(configuration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
//		http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
		http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
		http.addFilterAt(new LoginFilter(membersService, tokenService, authenticationManager(configuration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
		
		http.userDetailsService(customUserDetailsService);
	
		
		return http.build();
	};
	
	
	
	
}

