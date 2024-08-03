package likelion_backend.OnSiL.domain.map.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="walk_route")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

}

