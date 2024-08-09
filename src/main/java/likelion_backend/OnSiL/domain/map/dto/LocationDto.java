package likelion_backend.OnSiL.domain.map.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDto{
    private Long id;
    private String title;
    private String writer;
    private String content;
    private long start_latitude;
    private long start_longitude;
    private long end_latitude;
    private long end_longitude;
    private int likes;
    private int replies;
}
