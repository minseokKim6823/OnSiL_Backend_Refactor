package likelion_backend.OnSiL.domain.member.repository;



import likelion_backend.OnSiL.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(String memberId);
    Optional<Member> findOneWithAuthorityByMemberId(String memberId);
}
