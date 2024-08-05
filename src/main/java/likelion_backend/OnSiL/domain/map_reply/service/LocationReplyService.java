package likelion_backend.OnSiL.domain.map_reply.service;


import likelion_backend.OnSiL.domain.map.entity.Location;
import likelion_backend.OnSiL.domain.map.repository.LocationJpaRepository;
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
    private final LocationJpaRepository locationRepository;
    private final MemberService memberService;

    public List<LocationReplyDto> findALl(){
        return locationJpaRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<LocationReplyDto> findById(Long id) {
        return locationJpaRepository.findById(id).map(this::convertToDTO);
    }

    public LocationReplyDto save(LocationReplyDto locationReplyDto) {
        LocationReply locationReply = convertToEntity(locationReplyDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Member> member = memberService.findByMemberId(authentication.getName());
        String memberNickname = member.map(Member::getNickname).orElse("anonymousWriter");
        locationReply.setWriter(memberNickname);


        Optional<Location> locationOpt = locationRepository.findById(locationReplyDto.getLocationId());
        if (locationOpt.isPresent()) {
            Location location = locationOpt.get();
            location.setReplies(location.getReplies() + 1);
            locationReply.setLocation(location);
            locationRepository.save(location); // 변경된 Location 객체 저장
        }
        return convertToDTO(locationJpaRepository.save(locationReply));
    }

    public void deleteById(Long id) {
        locationJpaRepository.deleteById(id);
    }

    private LocationReplyDto convertToDTO(LocationReply locationReply) {
        LocationReplyDto locationReplyDto = new LocationReplyDto();
        locationReplyDto.setId(locationReply.getId());
        locationReplyDto.setWriter(locationReply.getWriter());
        locationReplyDto.setContent(locationReply.getContent());
        locationReplyDto.setLocationId(locationReply.getLocation().getId());

        return locationReplyDto;
    }

    private LocationReply convertToEntity(LocationReplyDto locationReplyDto){
        LocationReply locationReply = new LocationReply();
        locationReply.setId(locationReplyDto.getId());
        locationReply.setWriter(locationReplyDto.getWriter());
        locationReply.setWriter(locationReplyDto.getWriter());
        locationReply.setContent(locationReplyDto.getContent());

        // Location 설정
        if (locationReplyDto.getLocationId() != null) {
            Optional<Location> location = locationRepository.findById(locationReplyDto.getLocationId());
            location.ifPresent(locationReply::setLocation);
        }

        return locationReply;
    }
}

