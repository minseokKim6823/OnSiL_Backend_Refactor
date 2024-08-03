package com.example.news.service;

import com.example.news.dto.NewsApiResponseDto;
import com.example.news.dto.NewsResponseDto;
import com.example.news.exception.NewsNullException;
import com.example.news.repository.NewsApiResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsService {
    private final NewsApiResponseRepository newsApiResponseRepository;

    public List<NewsResponseDto> getNaverNewsWithKeyword(String keyword) {
        NewsApiResponseDto newsApiResponseDto = newsApiResponseRepository.getNewsApiResponseDto(keyword)
                .orElseThrow(NewsNullException::new);
        return newsApiResponseDto.getNewsResponseDtoWithImages();
    }
}
