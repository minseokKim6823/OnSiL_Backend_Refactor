package likelion_backend.OnSiL.domain.healthnews.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HealthNewsResponseDto {
    private String title;
    private String link;
    private String description;
    private String imageUrl;

    public boolean validateImageLink() {
        return imageUrl != null && !imageUrl.isEmpty();
    }
}
