package likelion_backend.OnSiL.domain.member.dto;

import likelion_backend.OnSiL.domain.member.entity.Status;

public record SignUpDto(String memberId, String name, String password, String profile_pic, String health_con, int text_size, String nickname, Status status) {

    public SignUpDto(String memberId, String name, String password, String profile_pic, String health_con, int text_size, String nickname) {
        this(memberId, name, password, profile_pic, health_con, text_size, nickname, Status.ACTIVE);
    }
}
