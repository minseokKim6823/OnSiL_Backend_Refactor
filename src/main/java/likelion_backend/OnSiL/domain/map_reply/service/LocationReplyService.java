package likelion_backend.OnSiL.domain.map_reply.service;


import likelion_backend.OnSiL.domain.map_reply.dto.LocationReplyDto;
import likelion_backend.OnSiL.domain.map_reply.entity.LocationReply;
import likelion_backend.OnSiL.domain.map_reply.repository.LocationReplyJpaRepository;
import likelion_backend.OnSiL.domain.member.entity.Member;
import likelion_backend.OnSiL.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationReplyService {

    private final LocationReplyJpaRepository locationJpaRepository;

    private final MemberService memberService;

    public List<LocationReplyDto> findALl(){
        return locationJpaRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    public Optional<LocationReplyDto> findById(Long id) {
        return locationJpaRepository.findById(id).map(this::convertToDTO);
    }

    public LocationReplyDto save(LocationReplyDto locationReplyDto) {
        LocationReply location = convertToEntity(locationReplyDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Member> member = memberService.findByMemberId(authentication.getName());
        String memberNickname = member.map(Member::getNickname).orElse("anonymousWriter");
        location.setWriter(memberNickname);
        return convertToDTO(locationJpaRepository.save(location));
    }

    public void deleteById(Long id) {
        locationJpaRepository.deleteById(id);
    }

    private LocationReplyDto convertToDTO(LocationReply location) {
        LocationReplyDto locationDto = new LocationReplyDto();
        locationDto.setWriter(location.getWriter());
        locationDto.setContent(location.getContent());
        return locationDto;
    }
    private LocationReply convertToEntity(LocationReplyDto locationDto){
        LocationReply location =new LocationReply();
        location.setWriter(locationDto.getWriter());
        location.setContent(locationDto.getContent());
        return location;
    }

}
