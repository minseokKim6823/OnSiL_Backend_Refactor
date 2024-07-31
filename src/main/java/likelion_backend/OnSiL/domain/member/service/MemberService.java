package likelion_backend.OnSiL.domain.member.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import likelion_backend.OnSiL.domain.member.controller.S3FileUploadController;
import likelion_backend.OnSiL.domain.member.dto.MemberUpdateDto;
import likelion_backend.OnSiL.domain.member.dto.SignUpDto;
import likelion_backend.OnSiL.domain.member.entity.Member;
import likelion_backend.OnSiL.domain.member.repository.AuthorityJpaRepository;
import likelion_backend.OnSiL.domain.member.repository.MemberJpaRepository;
import likelion_backend.OnSiL.global.jwt.entity.Authority;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private static final String PHONE_PATTERN = "^(02|0[1-9][0-9]?)-[0-9]{3,4}-[0-9]{4}$";
    private static final Pattern phonePattern = Pattern.compile(PHONE_PATTERN);

    private final MemberJpaRepository memberJpaRepository;//멤버 저장소
    private final PasswordEncoder passwordEncoder;
    private final AuthorityJpaRepository authorityJpaRepository;
    private final S3FileUploadController s3FileUploadController;
    @Service
    @RequiredArgsConstructor
    public class S3FileUploadService {

        private final AmazonS3 amazonS3Client;

        @Value("${cloud.aws.s3.bucket}")
        private String bucket;

        public String uploadFile(MultipartFile file) throws IOException {
            String fileName = file.getOriginalFilename();
            String fileUrl = "https://" + bucket + ".s3." + amazonS3Client.getRegionName() + ".amazonaws.com/" + fileName;
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
            return fileUrl;
        }
    }


    @Transactional
    public ResponseEntity<Boolean> signUp(SignUpDto memberDto) {
        if (memberJpaRepository.findByMemberId(memberDto.memberId()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");

        }
        Authority authority = authorityJpaRepository.findByAuthority("ROLE_USER")
                .orElseGet(() -> Authority.builder()
                        .authority("ROLE_USER")
                        .build());



        Member member = Member.builder()
                .memberId(memberDto.memberId())
                .name(memberDto.name())
                .password(passwordEncoder.encode(memberDto.password()))
                .nickname(memberDto.nickname())
                .authority(authority)
                .status(memberDto.status())
                .profile_pic(s3FileUploadController.getName())
                .activate(true)

                .build();


        Member save = memberJpaRepository.save(member);
        log.info("멤버 저장 됨 {}", save.getId());

        //log.info(save.getPassword());
        return ResponseEntity.ok(true);
    }

    public Optional<Member> findByMemberId(String memberId) {
        return memberJpaRepository.findByMemberId(memberId);
    }
    @Transactional
    public ResponseEntity<Boolean> updateMember(Long memberId, MemberUpdateDto memberUpdateDto) {
        Optional<Member> optionalMember = memberJpaRepository.findById(memberId);

        if (optionalMember.isEmpty()) {
            throw new RuntimeException("Member not found");
        }

        Member member = optionalMember.get();

        // Update fields
        member.setName(memberUpdateDto.getName() != null ? memberUpdateDto.getName() : member.getName());
        member.setNickname(memberUpdateDto.getNickname() != null ? memberUpdateDto.getNickname() : member.getNickname());
        member.setProfile_pic(memberUpdateDto.getProfilePic() != null ? memberUpdateDto.getProfilePic() : member.getProfile_pic());
        member.setHealth_con(memberUpdateDto.getHealthCon() != null ? memberUpdateDto.getHealthCon() : member.getHealth_con());
        member.setText_size(memberUpdateDto.getTextSize() != 0 ? memberUpdateDto.getTextSize() : member.getText_size());
        member.setActivate(memberUpdateDto.getActivate() != null ? memberUpdateDto.getActivate() : member.getActivate());

        memberJpaRepository.save(member);
        return ResponseEntity.ok(true);
    }
    public void deleteById(Long id) {
        memberJpaRepository.deleteById(id);
    }
}