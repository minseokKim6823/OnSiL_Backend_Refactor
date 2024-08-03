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
        NewsApiResponseDto newsApiResponse = newsApiResponseRepository.getNewsApiResponseDto(keyword)
                .orElseThrow(() -> new NewsNullException("No news found for keyword: " + keyword));

        List<NewsSummaryDto> filteredNewsList = newsApiResponse.getItems().stream()
                .filter(news -> news.getTitle().contains(keyword))
                .map(news -> new NewsSummaryDto(news.getTitle(), news.getLink(), news.getDescription()))
                .collect(Collectors.toList());

        List<HealthNewsResponseDto> responseDtos = filteredNewsList.stream()
                .map(news -> {
                    HealthNewsResponseDto responseDto = news.toHealthNewsResponseDto(jsoupCrawling);
                    logger.info("Processed news item: {}", responseDto);
                    return responseDto;
                })
                .filter(HealthNewsResponseDto::validateImageLink)
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
