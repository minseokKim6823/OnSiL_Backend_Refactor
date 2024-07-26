package likelion_backend.OnSiL.domain.member.dto;


import com.nimbusds.openid.connect.sdk.claims.Gender;
import likelion_backend.OnSiL.domain.member.entity.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDto {
    private String memberId;
    private String name;
    private String password;
    private String profile_pic;
    private String health_con;
    private int text_size;
    private String nickname;

    @Builder.Default
    private Status status = Status.ACTIVE;
}
