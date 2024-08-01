package likelion_backend.OnSiL.domain.member.dto;

import lombok.Data;

//@Data
public record MemberUpdateDto(String name,String nickname,String profilePic,String healthCon,int textSize,Boolean activate) {
//    private String name;
//    private String nickname;
//    private String profilePic;
//    private String healthCon;
//    private int textSize;
//    private Boolean activate;
}