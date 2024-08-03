package likelion_backend.OnSiL.domain.member.service;



import jakarta.transaction.Transactional;
import likelion_backend.OnSiL.domain.member.entity.Member;
import likelion_backend.OnSiL.domain.member.repository.MemberJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LoginService implements UserDetailsService {
    private final MemberJpaRepository memberJpaRepository;

    @Autowired
    public LoginService(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return memberJpaRepository.findOneWithAuthorityByMemberId(memberId)
                .map(member -> createMember(memberId, member))
                .orElseThrow(() -> new UsernameNotFoundException(memberId + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private User createMember(String memberName, Member member) {
        if (!member.isActivated()) {
            throw new RuntimeException(memberName + " -> 활성화되어 있지 않습니다.");
        }
        List<GrantedAuthority> grantedAuthorities =
                Collections.singletonList(new SimpleGrantedAuthority(member.getAuthority().getAuthority()));
        return new User(member.getMemberId(), member.getPassword(), grantedAuthorities);
    }

//    @Transactional
//    public Optional<Member> getMemberWithAuthorities(String memberId) {
//        return memberJpaRepository.findOneWithAuthorityByMemberId(memberId);
//    }

}
