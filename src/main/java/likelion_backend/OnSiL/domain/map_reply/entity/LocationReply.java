package likelion_backend.OnSiL.domain.map_reply.entity;

import jakarta.persistence.*;
import likelion_backend.OnSiL.domain.map.entity.Location;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter

public class LocationReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private Long id;
    @Column(length = 30)
    private String content;
    @Column(length = 50 )
    private String writer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="location_id")
    private Location location;
}

