package likelion_backend.OnSiL.domain.healthnews.repository;

import likelion_backend.OnSiL.domain.healthnews.dto.NewsApiResponseDto;
import likelion_backend.OnSiL.domain.healthnews.exception.NaverApiCallException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NewsApiResponseRepository {
    @Value("${naver.client.secret}")
    private String newsApiKey;
    @Value("${naver.client.id}")
    private String newsApiClient;



    public Optional<NewsApiResponseDto> getNewsApiResponseDto(String keyword) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getNaverApiRequestHeaders();

        String url = "https://openapi.naver.com/v1/search/news.json?query=" + keyword + "&display=10&sort=date";
        ResponseEntity<NewsApiResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), NewsApiResponseDto.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return Optional.ofNullable(response.getBody());
        }
        throw new NaverApiCallException();
    }

    private HttpHeaders getNaverApiRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", newsApiClient);
        headers.set("X-Naver-Client-Secret", newsApiKey);
        return headers;
    }
}
