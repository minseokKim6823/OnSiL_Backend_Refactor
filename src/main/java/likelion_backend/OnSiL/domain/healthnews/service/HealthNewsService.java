package likelion_backend.OnSiL.domain.healthnews.service;

import likelion_backend.OnSiL.domain.healthnews.client.JsoupCrawling;
import likelion_backend.OnSiL.domain.healthnews.dto.HealthNewsResponseDto;
import likelion_backend.OnSiL.domain.healthnews.dto.NewsApiResponseDto;
import likelion_backend.OnSiL.domain.healthnews.dto.NewsSummaryDto;
import likelion_backend.OnSiL.domain.healthnews.entity.HealthNews;
import likelion_backend.OnSiL.domain.healthnews.repository.HealthNewsRepository;
import likelion_backend.OnSiL.domain.healthnews.repository.NewsApiResponseRepository;
import likelion_backend.OnSiL.domain.healthnews.exception.NewsNullException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HealthNewsService {

    private static final Logger logger = LoggerFactory.getLogger(HealthNewsService.class);

    private final NewsApiResponseRepository newsApiResponseRepository;
    private final HealthNewsRepository healthNewsRepository;
    private final JsoupCrawling jsoupCrawling;

    public List<HealthNewsResponseDto> getNaverHealthNewsWithKeyword(String keyword) {
        List<NewsSummaryDto> allNews = new ArrayList<>();
        int display = 100;
        int start = 1;

        while (start <= 2000) {  // 최대 1300개의 데이터를 가져오도록 설정
            NewsApiResponseDto newsApiResponse = newsApiResponseRepository.getNewsApiResponseDto(keyword, display, start)
                    .orElseThrow(() -> new NewsNullException("No news found for keyword: " + keyword));

            List<NewsSummaryDto> filteredNewsList = newsApiResponse.getItems().stream()
                    .filter(news -> news.getTitle().contains(keyword))
                    .map(news -> new NewsSummaryDto(news.getTitle(), news.getLink(), news.getDescription()))
                    .collect(Collectors.toList());

            allNews.addAll(filteredNewsList);
            start += display;

            if (filteredNewsList.size() < display) {
                break; // 더 이상 뉴스 항목이 없으면 종료
            }
        }

        // 필터링된 뉴스 리스트 중에서 7개 미만의 뉴스만 가져오도록 설정
        List<HealthNewsResponseDto> responseDtos = allNews.stream()
                .map(news -> {
                    HealthNewsResponseDto responseDto = news.toHealthNewsResponseDto(jsoupCrawling);
                    logger.info("Processed news item: {}", responseDto);
                    return responseDto;
                })
                .filter(HealthNewsResponseDto::validateImageLink)
                .limit(4)  // 최대 4개의 뉴스만 가져오도록 제한
                .collect(Collectors.toList());

        saveHealthNewsList(responseDtos, keyword);

        return responseDtos;
    }

    private void saveHealthNewsList(List<HealthNewsResponseDto> healthNewsResponseDtos, String healthCon) {
        List<HealthNews> healthNewsList = healthNewsResponseDtos.stream()
                .map(dto -> HealthNews.builder()
                        .title(dto.getTitle())
                        .content(dto.getDescription())
                        .imageUrl(dto.getImageUrl())
                        .date(LocalDate.now())
                        .healthCon(healthCon)
                        .build())
                .collect(Collectors.toList());
        healthNewsRepository.saveAll(healthNewsList);
    }
}
