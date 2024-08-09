package likelion_backend.OnSiL.domain.map.entity;

import jakarta.persistence.*;
import likelion_backend.OnSiL.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LocationLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
