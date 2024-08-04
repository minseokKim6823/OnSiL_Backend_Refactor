package likelion_backend.OnSiL.domain.map.service;

import jakarta.transaction.Transactional;
import likelion_backend.OnSiL.domain.map.dto.LocationDto;
import likelion_backend.OnSiL.domain.map.entity.Location;
import likelion_backend.OnSiL.domain.map.entity.LocationLike;
import likelion_backend.OnSiL.domain.map.repository.LocationJpaRepository;
import likelion_backend.OnSiL.domain.map.repository.LocationLikeRepository;
import likelion_backend.OnSiL.domain.map.repository.LocationRepository;
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
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationJpaRepository locationJpaRepository;
    private final LocationLikeRepository locationLikeRepository;
    private final MemberService memberService;

    public List<LocationDto> findALl(){
        return locationJpaRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    public Optional<LocationDto> findById(Long id) {
        return locationJpaRepository.findById(id).map(this::convertToDTO);
    }

    public List<Location> getTop5PostsByLikes() {
        return locationJpaRepository.findTop5ByOrderByLikesDesc();
    }

    public LocationDto save(LocationDto locationDto) {
        Location location = convertToEntity(locationDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Member> member = memberService.findByMemberId(authentication.getName());
        String memberNickname = member.map(Member::getNickname).orElse("anonymousWriter");
        location.setWriter(memberNickname);
        System.out.println(memberNickname);
        return convertToDTO(locationJpaRepository.save(location));
    }

    public void deleteById(Long id) {
        locationJpaRepository.deleteById(id);
    }
    @Transactional
    public void likeLocation(Long locationId, Long memberId) {
        Optional<LocationLike> existingLike = locationLikeRepository.findByLocationIdAndMemberId(locationId, memberId);
        if (existingLike.isEmpty()) {
            Location location = locationJpaRepository.findById(locationId)
                    .orElseThrow(() -> new RuntimeException("Location not found"));
            Member member = memberService.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("Member not found"));

            LocationLike locationLike = new LocationLike();
            locationLike.setLocation(location);
            locationLike.setMember(member);
            locationLikeRepository.save(locationLike);

            location.setLikes(location.getLikes() + 1);
            locationJpaRepository.save(location);
        }
    }

    @Transactional
    public void unlikeLocation(Long locationId, Long memberId) {
        Optional<LocationLike> locationLike = locationLikeRepository.findByLocationIdAndMemberId(locationId, memberId);
        if (locationLike.isPresent()) {
            locationLikeRepository.delete(locationLike.get());

            Location location = locationJpaRepository.findById(locationId)
                    .orElseThrow(() -> new RuntimeException("Location not found"));
            location.setLikes(location.getLikes() - 1);
            locationJpaRepository.save(location);
        }
    }

    private LocationDto convertToDTO(Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setWriter(location.getWriter());
        locationDto.setContent(location.getContent());
        locationDto.setTitle(location.getTitle());
        locationDto.setStart_latitude(location.getStart_latitude());
        locationDto.setEnd_latitude(location.getEnd_latitude());
        locationDto.setStart_longitude(location.getStart_longitude());
        locationDto.setEnd_longitude(location.getEnd_longitude());
        locationDto.setLikes(location.getLikes());
        locationDto.setReplies(location.getReplies());
        return locationDto;
    }
    private Location convertToEntity(LocationDto locationDto){
        Location location =new Location();
        location.setWriter(locationDto.getWriter());
        location.setTitle(locationDto.getTitle());
        location.setContent(locationDto.getContent());
        location.setStart_latitude(locationDto.getStart_latitude());
        location.setEnd_latitude(locationDto.getEnd_latitude());
        location.setStart_longitude(locationDto.getStart_longitude());
        location.setEnd_longitude(locationDto.getEnd_longitude());
        location.setLikes(location.getLikes());
        location.setReplies(location.getReplies());
        return location;
    }

    // 사용자별로 게시물 조회하는 메서드 추가
    public List<LocationDto> findLocationsByWriter(String writer) {
        return locationRepository.findByWriter(writer).stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
