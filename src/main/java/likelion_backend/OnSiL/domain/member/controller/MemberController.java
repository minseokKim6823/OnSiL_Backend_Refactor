package likelion_backend.OnSiL.domain.member.controller;


import jakarta.validation.Valid;
import likelion_backend.OnSiL.domain.member.dto.LoginDto;
import likelion_backend.OnSiL.domain.member.dto.SignUpDto;
import likelion_backend.OnSiL.domain.member.entity.Member;
import likelion_backend.OnSiL.domain.member.repository.MemberJpaRepository;
import likelion_backend.OnSiL.domain.member.service.MemberService;
import likelion_backend.OnSiL.global.jwt.dto.TokenDto;
import likelion_backend.OnSiL.global.jwt.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;


@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;
    private final MemberJpaRepository memberJpaRepository;
    private final TokenService tokenService;

    @Autowired
    public MemberController(MemberService memberService, MemberJpaRepository memberJpaRepository, TokenService tokenService) {
        this.memberService = memberService;
        this.memberJpaRepository = memberJpaRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Boolean> signUp(@Valid @RequestBody SignUpDto signUpDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("아이디 혹은 비밀번호를 잘못입력했습니다.");

            return ResponseEntity.ok(false);
        }
        return memberService.signUp(signUpDto);
    }   // 회원가입

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Member> MemberName = memberService.findByMemberId(loginDto.memberId());
        String memberName = MemberName.map(Member::getName).orElse("Name not found");
        System.out.println("회원 이름: " + memberName);
        return tokenService.makeToken(loginDto);
    } // 회원 로그인


    @PostMapping("/memberId/check")
    public ResponseEntity<Boolean> checkDuplicate(@RequestBody HashMap<String, String> member) {
        String memberId = member.get("memberId");
        log.info(memberId);

        Optional<Member> byMember = memberService.findByMemberId(memberId);
        if (byMember.isEmpty()) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }   // 회원 아이디 중복 검사
}
