package likelion_backend.OnSiL.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import likelion_backend.OnSiL.domain.member.dto.*;
import likelion_backend.OnSiL.domain.member.entity.Member;
import likelion_backend.OnSiL.domain.member.repository.MemberJpaRepository;
import likelion_backend.OnSiL.domain.member.service.MailSendService;
import likelion_backend.OnSiL.domain.member.service.MemberService;
import likelion_backend.OnSiL.global.jwt.dto.TokenDto;
import likelion_backend.OnSiL.global.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final TokenService tokenService;
    private final MailSendService mailSendService;
    private static boolean emailauth = false;

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입 (메일 인증 후 사용가능) //민석")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpDto signUpDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("아이디 혹은 비밀번호를 잘못입력했습니다.");
            return ResponseEntity.ok("false");
        }
        else{
            if(emailauth==true) {
                memberService.signUp(signUpDto);
                return ResponseEntity.ok("회원가입 성공!!");
            }
            else{
                return ResponseEntity.ok("이메일 인증을 해주세요!");
            }
        }
    }
    @PostMapping("/mailSend")
    @Operation(summary = "메일 전송" + "{ \"email\": \"메일 보낼 이메일\"} //민석")
    public String mailSend(@RequestBody @Valid EmailRequestDto emailDto) {
        System.out.println("이메일 인증 요청이 들어옴");
        System.out.println("이메일 인증 이메일 :" + emailDto.email());
        return mailSendService.joinEmail(emailDto.email());
    }

    @PostMapping("/mailauthCheck")
    @Operation(summary = "메일 인증 번호 확인 //민석")
    public String AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto) {
        Boolean Checked = mailSendService.CheckAuthNum(emailCheckDto.authNum());
        if (Checked) {
            emailauth=true;
            return "ok";
        } else {
            throw new NullPointerException("뭔가 잘못!");
        }
    }
//    @PostMapping("email/check")
//    public ResponseEntity<String> emailCheck(@RequestBody EmailCheckDto emailCheckDto) {
//        if(mailSendService.getAuthNumber()==emailCheckDto.email() && )
//    }
    @PostMapping("/login")
    @Operation(summary = "로그인 //민석")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Member> MemberName = memberService.findByMemberId(loginDto.memberId());
        String memberName = MemberName.map(Member::getName).orElse("Name not found");
        System.out.println("회원 이름: " + memberName);
        return tokenService.makeToken(loginDto);
    }

//    @PostMapping("/memberId/check")
//    public ResponseEntity<Boolean> checkDuplicate(@RequestBody HashMap<String, String> member) {
//        String memberId = member.get("memberId");
//        log.info(memberId);
//
//        Optional<Member> byMember = memberService.findByMemberId(memberId);
//        if (byMember.isEmpty()) {
//            return ResponseEntity.ok(true);
//        } else {
//            return ResponseEntity.ok(false);
//        }
//    }

    @PutMapping("/mypage/update")
    @Operation(summary = "회원 정보 수정 //민석")
    public ResponseEntity<String> updateMember(@Valid @RequestBody MemberUpdateDto memberUpdateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = authentication.getPrincipal();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        System.out.println(username);
        ResponseEntity<Boolean> response = memberService.updateMember(username, memberUpdateDto);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("업데이트 성공!!");
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("업데이트 실패!!");
        }
    }

//    @Transactional
//    @DeleteMapping("/delete")
//    @Operation(summary = "회원 탈퇴 //민석")
//    public ResponseEntity<String> delMemberById() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
//        System.out.println(username);
//        Optional<Member> member = memberService.findByMemberId(username);
//        if (member.isPresent()) {
//            memberService.deleteByMemberId(username);
//            return ResponseEntity.ok("삭제 성공");
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping
    @Operation(summary = "전체 회원 정보 조회 (테스트 용) //민석")
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        List<Member> members = memberService.findAll();
        List<MemberResponseDto> memberDtos = members.stream()
                .map(MemberResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(memberDtos);
    }

    @GetMapping("/mypage")
    @Operation(summary = "회원 정보 조회 //민석")
    public ResponseEntity<MemberResponseDto> getMemberById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        System.out.println(username);
        Optional<Member> member = memberService.findByMemberId(username);
        if (member.isPresent()) {
            MemberResponseDto memberDto = MemberResponseDto.fromEntity(member.get());
            return ResponseEntity.ok(memberDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
