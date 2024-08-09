package likelion_backend.OnSiL.domain.diet.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "diet")
public class Diet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dietId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Integer dietAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DietType dietType;

    @Column(length = 255, nullable = false)
    private String dietMaterial;

    @Column(nullable = false)
    private String dietRecipe;

    // Getters and Setters

    public Long getDietId() {
        return dietId;
    }

    public void setDietId(Long dietId) {
        this.dietId = dietId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}
