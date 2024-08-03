package likelion_backend.OnSiL.domain.healthnews.controller;

import io.swagger.v3.oas.annotations.Operation;
import likelion_backend.OnSiL.domain.healthnews.dto.HealthNewsResponseDto;
import likelion_backend.OnSiL.domain.healthnews.service.HealthNewsService;
import likelion_backend.OnSiL.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/healthnews")
@RequiredArgsConstructor
public class HealthNewsController {
    private final HealthNewsService healthNewsService;
    private final MemberService memberService;

    @GetMapping
    @Operation(summary = "키워드로 건강 뉴스 검색 //준상")
    public ResponseEntity<List<HealthNewsResponseDto>> getHealthNewsWithKeyword(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(healthNewsService.getNaverHealthNewsWithKeyword(keyword));
    }

    @GetMapping("/user")
    @Operation(summary = "유저의 건강 상태로 건강 뉴스 검색 //준상")
    public ResponseEntity<List<HealthNewsResponseDto>> getHealthNewsForUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        List<HealthNewsResponseDto> newsList = memberService.getHealthNewsByHealthCon(username);
        return ResponseEntity.ok(newsList);
    }
}
