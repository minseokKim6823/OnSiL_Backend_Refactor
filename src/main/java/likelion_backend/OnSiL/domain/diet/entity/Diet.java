package likelion_backend.OnSiL.domain.diet.entity;

import jakarta.persistence.*;

@Entity
public class Diet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dietId;

    private Integer dietAmount;

    @Enumerated(EnumType.STRING)
    private DietType dietType;

    private String dietMaterial;

    @Lob
    private String dietRecipe;

    public Long getDietId() {
        return dietId;
    }

    public void setDietId(Long dietId) {
        this.dietId = dietId;
    }

    public Integer getDietAmount() {
        return dietAmount;
    }

    public void setDietAmount(Integer dietAmount) {
        this.dietAmount = dietAmount;
    }

    public DietType getDietType() {
        return dietType;
    }

    public void setDietType(DietType dietType) {
        this.dietType = dietType;
    }

    public String getDietMaterial() {
        return dietMaterial;
    }

    public void setDietMaterial(String dietMaterial) {
        this.dietMaterial = dietMaterial;
    }

    public String getDietRecipe() {
        return dietRecipe;
    }

    public void setDietRecipe(String dietRecipe) {
        this.dietRecipe = dietRecipe;
    }
// Getters and Setters
}
