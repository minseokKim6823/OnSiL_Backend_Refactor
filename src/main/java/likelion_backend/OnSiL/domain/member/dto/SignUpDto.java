package likelion_backend.OnSiL.domain.member.dto;

import likelion_backend.OnSiL.domain.member.entity.Status;

public record SignUpDto(String memberId, String name, String password, String profile_pic, String health_con, int text_size, String nickname, Status status) {

}
