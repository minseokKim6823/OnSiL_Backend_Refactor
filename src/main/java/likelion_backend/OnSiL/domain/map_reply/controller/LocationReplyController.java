package likelion_backend.OnSiL.domain.map_reply.controller;


import io.swagger.v3.oas.annotations.Operation;
import likelion_backend.OnSiL.domain.map_reply.dto.LocationReplyDto;
import likelion_backend.OnSiL.domain.map_reply.service.LocationReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/location-reply")
public class LocationReplyController {

    @Autowired
    private LocationReplyService locationService;

    @GetMapping
    @Operation(summary = "산책 코스 게시판 댓글 전체 조회 //민석")
    public List<LocationReplyDto> getAllLocations() {
        return locationService.findALl();
    }

    @GetMapping("/{id}")
    @Operation(summary = "산책 코스 게시판 댓글 조회 (삭제 예정) //민석")
    public ResponseEntity<LocationReplyDto> getLocationById(@PathVariable Long id) {
        Optional<LocationReplyDto> locationDto = locationService.findById(id);
        if (locationDto.isPresent()) {
            return ResponseEntity.ok(locationDto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{locationId}")
    @Operation(summary = "산책 코스 게시판 댓글 작성 (삭제 예정) //민석")
    public ResponseEntity<LocationReplyDto> createLocation(@PathVariable Long locationId,@RequestBody LocationReplyDto locationDto) {
        locationDto.setLocationId(locationId);
        LocationReplyDto savedLocation = locationService.save(locationDto);
        return ResponseEntity.ok(savedLocation);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "산책 코스 게시판 댓글 삭제  //민석")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        if (locationService.findById(id).isPresent()) {
            locationService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}