package likelion_backend.OnSiL.domain.diet.Repository;

import likelion_backend.OnSiL.domain.diet.entity.Diet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietRepository extends JpaRepository<Diet, Long> {
}
