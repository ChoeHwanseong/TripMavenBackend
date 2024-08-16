package com.tripmaven.members.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripmaven.auth.model.JWTTOKEN;
import com.tripmaven.auth.model.JWTUtil;
import com.tripmaven.members.model.MembersDto;
import com.tripmaven.members.service.MembersService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt-login")
@Slf4j
public class JwtLoginController {
	private final MembersService membersService;
	private final JWTUtil jwtUtil;
	
	@GetMapping(value = {"", "/"})
    public String home(Model model) {

        model.addAttribute("loginType", "jwt-login");
        model.addAttribute("pageName", "스프링 시큐리티 JWT 로그인");

        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        MembersDto loginMember = membersService.searchByMemberEmail(loginId);

        if (loginMember != null) {
            model.addAttribute("name", loginMember.getName());
        }

        return "home";
    }

//    @PostMapping("/join")
//    public String join(@Validated @ModelAttribute JoinRequest joinRequest,
//                       BindingResult bindingResult, Model model) {
//
//        model.addAttribute("loginType", "jwt-login");
//        model.addAttribute("pageName", "스프링 시큐리티 JWT 로그인");
//
//        // ID 중복 여부 확인
//        if (memberService.checkLoginIdDuplicate(joinRequest.getLoginId())) {
//            return "ID가 존재합니다.";
//        }
//
//
//        // 비밀번호 = 비밀번호 체크 여부 확인
//        if (!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
//            return "비밀번호가 일치하지 않습니다.";
//        }
//
//        // 에러가 존재하지 않을 시 joinRequest 통해서 회원가입 완료
//        memberService.securityJoin(joinRequest);
//
//        // 회원가입 시 홈 화면으로 이동
//        return "redirect:/jwt-login";
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestBody Map map){
//
//        Member member = membersService.searchByMemberEmail(map.get("email").toString());
//
//
//        if(member==null){
//            return "ID 또는 비밀번호가 일치하지 않습니다!";
//        }
//
//        String token = jwtUtil.createJwt(member.getLoginId(), member.getRole(), 1000 * 60 * 60L);
//        return token;
//    }
//
//    @GetMapping("/info")
//    public String memberInfo(Authentication auth, Model model) {
//
//        Member loginMember = memberService.getLoginMemberByLoginId(auth.getName());
//
//        return "ID : " + loginMember.getLoginId() + "\n이름 : " + loginMember.getName() + "\nrole : " + loginMember.getRole();
//    }
//    
//    @GetMapping("/admin")
//    public String adminPage(Model model) {
//
//        return "인가 성공!";
//    }  response.sendRedirect(redirectUrl);
//	
}
