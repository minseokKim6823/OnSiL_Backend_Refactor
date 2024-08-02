package likelion_backend.OnSiL.domain.map.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LocationDto{
    private String title;
    private String writer;
    private String content;
    private long start_latitude;
    private long start_longitude;
    private long end_latitude;
    private long end_longitude;
}
