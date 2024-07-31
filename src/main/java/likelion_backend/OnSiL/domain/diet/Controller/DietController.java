package likelion_backend.OnSiL.domain.diet.Controller;

import likelion_backend.OnSiL.domain.diet.entity.Diet;
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
    @GetMapping
    public List<Diet> getAllDiets() {
        return dietService.getAllDiets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Diet> getDietById(@PathVariable Long id) {
        Diet diet = dietService.getDietById(id);
        return ResponseEntity.ok(diet);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Diet> updateDiet(@PathVariable Long id, @RequestBody Diet dietDetails) {
        Diet updatedDiet = dietService.updateDiet(id, dietDetails);
        return ResponseEntity.ok(updatedDiet);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiet(@PathVariable Long id) {
        dietService.deleteDiet(id);
        return ResponseEntity.noContent().build();
    }
}
