package likelion_backend.OnSiL.domain.healthnews.service;

import likelion_backend.OnSiL.domain.healthnews.entity.HealthNews;
import likelion_backend.OnSiL.domain.healthnews.repository.HealthNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class HealthNewsService {

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    @Autowired
    private HealthNewsRepository healthNewsRepository;

    public String searchNews(String keyword) {
        try {
            String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8.toString());
            String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + encodedKeyword + "&display=3";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Naver-Client-Id", clientId);
            headers.set("X-Naver-Client-Secret", clientSecret);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(apiURL, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to search news", e);
        }
    }

    public void saveNews(HealthNews healthNews) {
        healthNewsRepository.save(healthNews);
    }
}
