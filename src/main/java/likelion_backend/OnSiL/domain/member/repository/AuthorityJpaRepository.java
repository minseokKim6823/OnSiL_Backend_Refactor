package likelion_backend.OnSiL.domain.member.repository;

import likelion_backend.OnSiL.global.jwt.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AuthorityJpaRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByAuthority(String auth);
}
