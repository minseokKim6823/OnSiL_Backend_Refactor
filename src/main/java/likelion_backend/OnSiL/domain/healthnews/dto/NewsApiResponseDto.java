package likelion_backend.OnSiL.domain.healthnews.dto;

import likelion_backend.OnSiL.domain.healthnews.client.JsoupCrawling;
import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class NewsApiResponseDto {
    @JsonSetter("items")
    private List<NewsSummaryDto> items;

    public List<NewsSummaryDto> getOnlyNaverNews() {
        return items.stream()
                .filter(item -> item.getLink().startsWith("https://n.news"))
                .collect(Collectors.toList());
    }

    public List<HealthNewsResponseDto> getHealthNewsResponseDtoWithImages() {
        return getOnlyNaverNews().stream()
                .map(item -> item.toHealthNewsResponseDto(new JsoupCrawling()))
                .filter(HealthNewsResponseDto::validateImageLink)
                .limit(10)
                .collect(Collectors.toList());
    }
}
