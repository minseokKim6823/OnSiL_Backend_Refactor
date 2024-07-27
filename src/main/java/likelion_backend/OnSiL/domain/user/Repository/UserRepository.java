package likelion_backend.OnSiL.domain.user.Repository;

import likelion_backend.OnSiL.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
