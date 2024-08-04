package likelion_backend.OnSiL.domain.map.repository;

import likelion_backend.OnSiL.domain.map.entity.LocationLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationLikeRepository extends JpaRepository<LocationLike, Long> {
    Optional<LocationLike> findByLocationIdAndMemberId(Long locationId, Long memberId);
}