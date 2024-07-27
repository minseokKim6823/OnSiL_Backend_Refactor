package likelion_backend.OnSiL.domain.diet.service;

import likelion_backend.OnSiL.domain.diet.entity.Diet;
import likelion_backend.OnSiL.domain.diet.Repository.DietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DietService {

    @Autowired
    private DietRepository dietRepository;

    // Create
    public Diet createDiet(Diet diet) {
        return dietRepository.save(diet);
    }

    // Read
    public List<Diet> getAllDiets() {
        return dietRepository.findAll();
    }

    public Diet getDietById(Long id) {
        return dietRepository.findById(id).orElseThrow(() -> new RuntimeException("Diet not found"));
    }

    // Update
    public Diet updateDiet(Long id, Diet dietDetails) {
        Diet diet = dietRepository.findById(id).orElseThrow(() -> new RuntimeException("Diet not found"));
        diet.setDietAmount(dietDetails.getDietAmount());
        diet.setDietType(dietDetails.getDietType());
        diet.setDietMaterial(dietDetails.getDietMaterial());
        diet.setDietRecipe(dietDetails.getDietRecipe());
        return dietRepository.save(diet);
    }

    // Delete
    public void deleteDiet(Long id) {
        Diet diet = dietRepository.findById(id).orElseThrow(() -> new RuntimeException("Diet not found"));
        dietRepository.delete(diet);
    }
}
