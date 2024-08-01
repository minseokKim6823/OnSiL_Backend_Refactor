package likelion_backend.OnSiL.domain.member.dto;

import likelion_backend.OnSiL.domain.member.entity.Member;

public record MemberResponseDto(Long id, String name, String nickname,String memberId, String profile_pic, String health_con, int text_size) {
    public static MemberResponseDto fromEntity(Member member) {
        return new MemberResponseDto(
                member.getId(),
                member.getName(),
                member.getNickname(),
                member.getMemberId(),
                member.getProfile_pic(),
                member.getHealth_con(),
                member.getText_size()
        );
    }
}