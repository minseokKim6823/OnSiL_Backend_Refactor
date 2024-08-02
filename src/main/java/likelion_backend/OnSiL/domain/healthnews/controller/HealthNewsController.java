package likelion_backend.OnSiL.domain.healthnews.controller;

import likelion_backend.OnSiL.domain.healthnews.entity.HealthNews;
import likelion_backend.OnSiL.domain.healthnews.service.HealthNewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/healthnews")
public class HealthNewsController {

    private final HealthNewsService healthNewsService;

    // Constructor injection is preferred
    public HealthNewsController(HealthNewsService healthNewsService) {
        this.healthNewsService = healthNewsService;
    }

    // 네이버 API를 통한 건강 뉴스 조회 및 저장
    @GetMapping("/condition/{healthCon}")
    public List<HealthNews> getHealthNewsByCondition(@PathVariable String healthCon) {
        return healthNewsService.getHealthNewsByCondition(healthCon);
    }

    // Create
    @PostMapping
    public HealthNews createHealthNews(@RequestBody HealthNews healthNews) {
        return healthNewsService.createHealthNews(healthNews);
    }

    // Read
    @GetMapping("/{id}")
    public ResponseEntity<HealthNews> getHealthNewsById(@PathVariable Long id) {
        HealthNews healthNews = healthNewsService.getHealthNewsById(id);
        return ResponseEntity.ok(healthNews);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<HealthNews> updateHealthNews(@PathVariable Long id, @RequestBody HealthNews healthNewsDetails) {
        HealthNews updatedHealthNews = healthNewsService.updateHealthNews(id, healthNewsDetails);
        return ResponseEntity.ok(updatedHealthNews);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHealthNews(@PathVariable Long id) {
        healthNewsService.deleteHealthNews(id);
        return ResponseEntity.noContent().build();
    }
}
