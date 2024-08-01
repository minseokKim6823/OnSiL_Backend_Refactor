package likelion_backend.OnSiL.global.util;



import likelion_backend.OnSiL.domain.member.entity.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserValue {
    public static Member getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null; // 인증안되었을 때
        }
        return (Member) authentication.getPrincipal();
    }
}
