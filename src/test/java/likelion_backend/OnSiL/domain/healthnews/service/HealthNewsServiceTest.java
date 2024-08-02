package likelion_backend.OnSiL.domain.healthnews.service;

import likelion_backend.OnSiL.domain.healthnews.entity.HealthNews;
import likelion_backend.OnSiL.domain.healthnews.repository.HealthNewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class HealthNewsServiceTest {

    @Autowired
    private HealthNewsService healthNewsService;

    @Autowired
    private HealthNewsRepository healthNewsRepository;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        healthNewsRepository.deleteAll();
    }

    @Test
    public void testGetHealthNewsByCondition() {
        HealthNews healthNews = new HealthNews();
        healthNews.setTitle("Test Title");
        healthNews.setContent("Test Content");
        healthNews.setHealthCon("Test Condition");
        healthNews.setImageUrl("http://example.com/image.jpg");
        healthNews.setDate(LocalDate.now());

        healthNewsRepository.save(healthNews);

        List<HealthNews> result = healthNewsService.getHealthNewsByCondition("Test Condition");
        assertEquals(1, result.size());
    }
}
