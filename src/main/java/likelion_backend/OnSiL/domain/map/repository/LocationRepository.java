package likelion_backend.OnSiL.domain.map.repository;

import likelion_backend.OnSiL.domain.map.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {
    List<Location> findByWriter(String writer);
}
