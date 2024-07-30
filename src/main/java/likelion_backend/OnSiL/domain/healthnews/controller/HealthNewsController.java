package likelion_backend.OnSiL.domain.healthnews.controller;

import likelion_backend.OnSiL.domain.healthnews.entity.HealthNews;
import likelion_backend.OnSiL.domain.healthnews.service.HealthNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
public class HealthNewsController {

    @Autowired
    private HealthNewsService healthNewsService;

    @GetMapping("/{healthCondition}")
    public String getHealthNews(@PathVariable String healthCondition) {
        return healthNewsService.searchNews(healthCondition);
    }

    @PostMapping("/save")
    public void saveHealthNews(@RequestBody HealthNews healthNews) {
        healthNewsService.saveNews(healthNews);
    }
}
