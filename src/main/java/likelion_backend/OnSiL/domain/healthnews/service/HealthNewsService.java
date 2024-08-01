
package likelion_backend.OnSiL.domain.healthnews.service;

import likelion_backend.OnSiL.domain.healthnews.entity.HealthNews;
import likelion_backend.OnSiL.domain.healthnews.repository.HealthNewsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class HealthNewsService {

    private final HealthNewsRepository healthNewsRepository;

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    private final String apiUrl = "https://openapi.naver.com/v1/search/news.json";

    public HealthNewsService(HealthNewsRepository healthNewsRepository) {
        this.healthNewsRepository = healthNewsRepository;
    }

    public List<HealthNews> getHealthNewsByCondition(String healthCon) {
        // 네이버 뉴스 API 호출하여 뉴스 가져오기
        String query = URLEncoder.encode(healthCon, StandardCharsets.UTF_8);
        String requestUrl = apiUrl + "?query=" + query + "&display=5&sort=date";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, Map.class);

        List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");

        for (Map<String, Object> item : items) {
            String title = (String) item.get("title");
            String content = (String) item.get("description");
            String imageUrl = (String) item.get("link");
            LocalDate date = LocalDate.now(); // 현재 날짜 사용

            HealthNews healthNews = HealthNews.builder()
                    .title(title)
                    .content(content)
                    .imageUrl(imageUrl)
                    .healthCon(healthCon)
                    .date(date)
                    .build();

            healthNewsRepository.save(healthNews);
        }

        return healthNewsRepository.findByHealthCon(healthCon);
    }

    public HealthNews createHealthNews(HealthNews healthNews) {
        return healthNewsRepository.save(healthNews);
    }

    public HealthNews getHealthNewsById(Long id) {
        return healthNewsRepository.findById(id).orElseThrow(() -> new RuntimeException("Health news not found"));
    }

    public HealthNews updateHealthNews(Long id, HealthNews healthNewsDetails) {
        HealthNews healthNews = healthNewsRepository.findById(id).orElseThrow(() -> new RuntimeException("Health news not found"));
        healthNews.setTitle(healthNewsDetails.getTitle());
        healthNews.setContent(healthNewsDetails.getContent());
        healthNews.setImageUrl(healthNewsDetails.getImageUrl());
        healthNews.setHealthCon(healthNewsDetails.getHealthCon());
        healthNews.setDate(healthNewsDetails.getDate());
        return healthNewsRepository.save(healthNews);
    }

    public void deleteHealthNews(Long id) {
        HealthNews healthNews = healthNewsRepository.findById(id).orElseThrow(() -> new RuntimeException("Health news not found"));
        healthNewsRepository.delete(healthNews);
    }
}
