package likelion_backend.OnSiL.domain.like.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeRequestDTO {
    @NotNull
    private Long postId;
}
