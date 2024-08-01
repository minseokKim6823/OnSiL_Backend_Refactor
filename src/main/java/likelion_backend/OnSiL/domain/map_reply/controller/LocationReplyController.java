package likelion_backend.OnSiL.domain.map_reply.controller;


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
    public List<LocationReplyDto> getAllLocations() {
        return locationService.findALl();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationReplyDto> getLocationById(@PathVariable Long id) {
        Optional<LocationReplyDto> locationDto = locationService.findById(id);
        if (locationDto.isPresent()) {
            return ResponseEntity.ok(locationDto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<LocationReplyDto> createLocation(@RequestBody LocationReplyDto locationDto) {
        LocationReplyDto savedLocation = locationService.save(locationDto);
        return ResponseEntity.ok(savedLocation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationReplyDto> updateLocation(@PathVariable Long id, @RequestBody LocationReplyDto locationDto) {
        Optional<LocationReplyDto> locationOptional = locationService.findById(id);
        if (locationOptional.isPresent()) {
            LocationReplyDto updatedLocation = locationService.save(locationDto);
            return ResponseEntity.ok(updatedLocation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        if (locationService.findById(id).isPresent()) {
            locationService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}