package likelion_backend.OnSiL.domain.member.dto;

import likelion_backend.OnSiL.domain.member.entity.Member;
import lombok.Data;

@Data
public class MemberResponseDto {
    private Long id;
    private String name;
    private String nickname;
    private String memberId;
    private String profile_pic;
    private String health_con;
    private int text_size;

    public static MemberResponseDto fromEntity(Member member) {
        MemberResponseDto dto = new MemberResponseDto();
        dto.setId(member.getId());
        dto.setNickname(member.getNickname());
        dto.setName(member.getName());
        dto.setProfile_pic(member.getProfile_pic());
        dto.setMemberId(member.getMemberId());
        dto.setHealth_con(member.getHealth_con());
        dto.setText_size(member.getText_size());
        return dto;
    }
}
