package likelion_backend.OnSiL.domain.diet.controller;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "다이어트 생성 //준상")
    public Diet createDiet(@RequestBody Diet diet) {
        return dietService.createDiet(diet);
    }

    // Read
    @GetMapping("/user/{userId}")
    @Operation(summary = "사용자 ID로 다이어트 조회 //준상")
    public List<Diet> getDietsByUserId(@PathVariable Long userId) {
        return dietService.getDietsByUserId(userId);
    }

    @GetMapping("/type/{dietType}")
    @Operation(summary = "다이어트 타입으로 다이어트 조회 //준상")
    public List<Diet> getDietsByDietType(@PathVariable DietType dietType) {
        return dietService.getDietsByDietType(dietType);
    }

    // Update
    @PutMapping("/{dietId}")
    @Operation(summary = "다이어트 업데이트 //준상")
    public ResponseEntity<Diet> updateDiet(@PathVariable Long dietId, @RequestBody Diet dietDetails) {
        Diet updatedDiet = dietService.updateDiet(dietId, dietDetails);
        return ResponseEntity.ok(updatedDiet);
    }

    // Delete
    @DeleteMapping("/{dietId}")
    @Operation(summary = "다이어트 삭제 //준상")
    public ResponseEntity<Void> deleteDiet(@PathVariable Long dietId) {
        dietService.deleteDiet(dietId);
        return ResponseEntity.noContent().build();
    }
}
