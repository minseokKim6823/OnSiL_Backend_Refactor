package likelion_backend.OnSiL.domain.member.entity;


import jakarta.persistence.*;
import likelion_backend.OnSiL.global.jwt.entity.Authority;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name="user")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id",length = 11,nullable = false)
    private Long id;
    @Column(length = 30, nullable = false, unique= true)
    private String memberId;
    @Column(length=50, nullable = false)
    private String name;
    @Column(nullable = false, length = 50,unique = true)
    private String nickname;
    @Column(nullable = false)
    private String password;
    private String profile_pic;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String health_con;

    private int text_size;

    @Enumerated(EnumType.STRING)
    @Column(name="user_status")
    private Status status;
    @ManyToOne(cascade = CascadeType.ALL)
    private Authority authority;
    private Boolean activate;
    public boolean isActivated() {
        return activate;
    }
}
