package likelion_backend.OnSiL.domain.map.service;

import likelion_backend.OnSiL.domain.map.dto.LocationDto;
import likelion_backend.OnSiL.domain.map.entity.Location;
import likelion_backend.OnSiL.domain.map.repository.LocationJpaRepository;
import likelion_backend.OnSiL.domain.member.entity.Member;
import likelion_backend.OnSiL.domain.member.service.MemberService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationJpaRepository locationJpaRepository;

    private final MemberService memberService;

    public List<LocationDto> findALl(){
        return locationJpaRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    public Optional<LocationDto> findById(Long id) {
        return locationJpaRepository.findById(id).map(this::convertToDTO);
    }

    public LocationDto save(LocationDto locationDto) {
        Location location = convertToEntity(locationDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Member> member = memberService.findByMemberId(authentication.getName());
        String memberNickname = member.map(Member::getNickname).orElse("anonymousWriter");
        location.setWriter(memberNickname);
        return convertToDTO(locationJpaRepository.save(location));
    }

    public void deleteById(Long id) {
        locationJpaRepository.deleteById(id);
    }

    private LocationDto convertToDTO(Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setWriter(location.getWriter());
        locationDto.setTitle(location.getTitle());
        locationDto.setStart_latitude(location.getStart_latitude());
        locationDto.setEnd_latitude(location.getEnd_latitude());
        locationDto.setStart_longitude(location.getStart_longitude());
        locationDto.setEnd_longitude(location.getEnd_longitude());
        return locationDto;
    }
    private Location convertToEntity(LocationDto locationDto){
        Location location =new Location();
        location.setWriter(locationDto.getWriter());
        location.setTitle(locationDto.getTitle());
        location.setStart_latitude(locationDto.getStart_latitude());
        location.setEnd_latitude(locationDto.getEnd_latitude());
        location.setStart_longitude(locationDto.getStart_longitude());
        location.setEnd_longitude(locationDto.getEnd_longitude());
        return location;
    }

}
