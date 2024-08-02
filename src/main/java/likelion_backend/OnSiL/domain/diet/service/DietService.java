package likelion_backend.OnSiL.domain.diet.service;

import likelion_backend.OnSiL.domain.diet.entity.Diet;
import likelion_backend.OnSiL.domain.diet.entity.DietType;
import likelion_backend.OnSiL.domain.diet.repository.DietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DietService {

    @Autowired
    private DietRepository dietRepository;

    public Diet createDiet(Diet diet) {
        return dietRepository.save(diet);
    }

    public List<Diet> getDietsByUserId(Long userId) {
        return dietRepository.findByUserId(userId);
    }

    public List<Diet> getDietsByDietType(DietType dietType) {
        return dietRepository.findByDietType(dietType);
    }

    public Diet updateDiet(Long dietId, Diet dietDetails) {
        Diet diet = dietRepository.findById(dietId).orElseThrow(() -> new RuntimeException("Diet not found"));
        diet.setDietAmount(dietDetails.getDietAmount());
        diet.setDietType(dietDetails.getDietType());
        diet.setDietMaterial(dietDetails.getDietMaterial());
        diet.setDietRecipe(dietDetails.getDietRecipe());
        return dietRepository.save(diet);
    }

    public void deleteDiet(Long dietId) {
        Diet diet = dietRepository.findById(dietId).orElseThrow(() -> new RuntimeException("Diet not found"));
        dietRepository.delete(diet);
    }
}
