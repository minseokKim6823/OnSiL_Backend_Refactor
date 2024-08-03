package com.example.news.controller;

import com.example.news.dto.NewsResponseDto;
import com.example.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<List<NewsResponseDto>> getNewsWithKeyword(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(newsService.getNaverNewsWithKeyword(keyword));
    }
}
