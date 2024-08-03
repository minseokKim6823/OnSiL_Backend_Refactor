package likelion_backend.OnSiL.domain.healthnews.dto;

import likelion_backend.OnSiL.domain.healthnews.client.JsoupCrawling;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jsoup.select.Elements;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class NewsSummaryDto {
    private String title;
    private String link;
    private String description;

    public NewsSummaryDto(String title, String link, String description) {
        this.title = title;
        this.link = link;
        this.description = description;
    }

    public HealthNewsResponseDto toHealthNewsResponseDto(JsoupCrawling jsoupCrawling) {
        String query = "img"; // 예시로 이미지 태그를 선택하는 쿼리

        Optional<Elements> elements = jsoupCrawling.getJsoupElements(link, query);
        if (elements.isEmpty() || elements.get().isEmpty()) {
            return HealthNewsResponseDto.builder()
                    .title(title)
                    .link(link)
                    .description(description)
                    .build();
        }
        String imageLink = elements.get().get(0).attr("src");
        return HealthNewsResponseDto.builder()
                .title(title)
                .link(link)
                .description(description)
                .imageUrl(imageLink)
                .build();
    }
}
