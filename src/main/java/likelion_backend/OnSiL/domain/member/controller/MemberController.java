package likelion_backend.OnSiL.domain.member.controller;

import jakarta.validation.Valid;
import likelion_backend.OnSiL.domain.member.dto.*;
import likelion_backend.OnSiL.domain.member.entity.Member;
import likelion_backend.OnSiL.domain.member.repository.MemberJpaRepository;
import likelion_backend.OnSiL.domain.member.service.MemberService;
import likelion_backend.OnSiL.global.jwt.dto.TokenDto;
import likelion_backend.OnSiL.global.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final MemberJpaRepository memberJpaRepository;
    private final TokenService tokenService;

    private static boolean emailauth = false;

    @GetMapping(value = "/")
    public String doGetHelloWorld() {
        return "Hello World";
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Boolean> signUp(@Valid @RequestBody SignUpDto signUpDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("아이디 혹은 비밀번호를 잘못입력했습니다.");
            return ResponseEntity.ok(false);
        }
        return memberService.signUp(signUpDto);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Member> MemberName = memberService.findByMemberId(loginDto.memberId());
        String memberName = MemberName.map(Member::getName).orElse("Name not found");
        System.out.println("회원 이름: " + memberName);
        return tokenService.makeToken(loginDto);
    }

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
    }

    @PutMapping("/members/{memberId}")
    public ResponseEntity<Boolean> updateMember(@PathVariable Long memberId, @RequestBody MemberUpdateDto memberUpdateDto) {
        return memberService.updateMember(memberId, memberUpdateDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (memberJpaRepository.findById(id).isPresent()) {
            memberService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        List<Member> members = memberService.findAll();
        List<MemberResponseDto> memberDtos = members.stream()
                .map(MemberResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(memberDtos);
    }

    @GetMapping("/mypage")
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
