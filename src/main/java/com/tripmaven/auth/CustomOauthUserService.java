package com.tripmaven.auth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.members.service.MembersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




/*
Form로그인 방식은 기존의 CustomUserDetailsService를 통해서 진행됨.
첫 로그인이면 자동으로 회원가입 진행
*/
@Service
@RequiredArgsConstructor
@Slf4j //로깅용 어노테이션
public class CustomOauthUserService extends DefaultOAuth2UserService{
	
	private final MembersRepository membersRepository;

	@Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}", oAuth2User.getAttributes());
        

        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuthUserInfo oAuthUserInfo = null;

        if (provider.equals("google")) {
            log.info("구글 로그인");
            oAuthUserInfo = new GoogleUserDetails(oAuth2User.getAttributes());
        } else if (provider.equals("kakao")) {
            log.info("카카오 로그인");
            oAuthUserInfo = new KakaoUserDetails(oAuth2User.getAttributes());
        } else if (provider.equals("naver")) {
            log.info("네이버 로그인");
            oAuthUserInfo = new NaverUserDetails(oAuth2User.getAttributes());
        }
        
        if (oAuthUserInfo == null) {
            throw new OAuth2AuthenticationException("지원하지 않는 OAuth2 공급자입니다.");
        }
        
        String providerId = oAuthUserInfo.getProviderId();
        String email = oAuthUserInfo.getEmail();
        String name = oAuthUserInfo.getName();
        

        MembersEntity membersEntity = membersRepository.findByEmail(email)
                .orElseGet(() -> {
                    MembersEntity newMember = MembersEntity.builder()
                            .email(email)
                            .name(name)
                            .role("user")
                            .loginType(provider)
                            .password("OAUTH2_LOGIN")
                            .build();
                    return membersRepository.save(newMember);
                });

        return new CustomOauthUserDetails(membersEntity, oAuth2User.getAttributes());
    }
	
	
}
