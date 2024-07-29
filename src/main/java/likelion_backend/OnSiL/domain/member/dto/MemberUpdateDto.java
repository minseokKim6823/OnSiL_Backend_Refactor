package likelion_backend.OnSiL.domain.member.dto;

import lombok.Data;

@Data
public class MemberUpdateDto {
    private String name;
    private String nickname;
    private String profilePic;
    private String healthCon;
    private int textSize;
    private Boolean activate;
}