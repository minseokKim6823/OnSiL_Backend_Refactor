package likelion_backend.OnSiL.domain.map.entity;

import jakarta.persistence.*;
import likelion_backend.OnSiL.domain.map_reply.entity.LocationReply;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name="walk_route")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private Long id;
    @Column(length = 30)
    private String title;
    private String content;
    @Column(length=50, nullable = false)
    private String writer;
    @Column(nullable = false, length = 50)
    private long start_latitude;
    @Column(nullable = false, length = 50)
    private long start_longitude;
    @Column(nullable = false, length = 50)
    private long end_latitude;
    @Column(nullable = false, length = 50)
    private long end_longitude;
    @OneToMany (mappedBy = "location",fetch = FetchType.LAZY)
    @OrderBy("id asc")
    private List<LocationReply> replyList;

    @Column(nullable = false)
    private int likes;
    private int replies;

}

