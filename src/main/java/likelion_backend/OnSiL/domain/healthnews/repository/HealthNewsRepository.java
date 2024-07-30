package likelion_backend.OnSiL.domain.healthnews.repository;

import likelion_backend.OnSiL.domain.healthnews.entity.HealthNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthNewsRepository extends JpaRepository<HealthNews, Long> {
}
