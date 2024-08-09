package likelion_backend.OnSiL.domain.map_reply.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LocationReplyDto {
    private Long id;
    private String writer;
    private String content;
    private Long locationId;
}
