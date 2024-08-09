package likelion_backend.OnSiL.domain.diet.repository;

import likelion_backend.OnSiL.domain.diet.entity.Diet;
import likelion_backend.OnSiL.domain.diet.entity.DietType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DietRepository extends JpaRepository<Diet, Long> {
    List<Diet> findByUserId(Long userId);
    List<Diet> findByDietType(DietType dietType);
}
