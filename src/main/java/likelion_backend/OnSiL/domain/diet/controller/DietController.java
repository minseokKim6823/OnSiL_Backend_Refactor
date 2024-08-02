package likelion_backend.OnSiL.domain.diet.controller;

import likelion_backend.OnSiL.domain.diet.entity.Diet;
import likelion_backend.OnSiL.domain.diet.entity.DietType;
import likelion_backend.OnSiL.domain.diet.service.DietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diets")
public class DietController {

    @Autowired
    private DietService dietService;

    // Create
    @PostMapping
    public Diet createDiet(@RequestBody Diet diet) {
        return dietService.createDiet(diet);
    }

    // Read
    @GetMapping("/user/{userId}")
    public List<Diet> getDietsByUserId(@PathVariable Long userId) {
        return dietService.getDietsByUserId(userId);
    }

    @GetMapping("/type/{dietType}")
    public List<Diet> getDietsByDietType(@PathVariable DietType dietType) {
        return dietService.getDietsByDietType(dietType);
    }

    // Update
    @PutMapping("/{dietId}")
    public ResponseEntity<Diet> updateDiet(@PathVariable Long dietId, @RequestBody Diet dietDetails) {
        Diet updatedDiet = dietService.updateDiet(dietId, dietDetails);
        return ResponseEntity.ok(updatedDiet);
    }

    // Delete
    @DeleteMapping("/{dietId}")
    public ResponseEntity<Void> deleteDiet(@PathVariable Long dietId) {
        dietService.deleteDiet(dietId);
        return ResponseEntity.noContent().build();
    }
}
