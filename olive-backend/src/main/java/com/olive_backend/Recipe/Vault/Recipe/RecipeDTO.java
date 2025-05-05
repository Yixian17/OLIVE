package com.olive_backend.Recipe.Vault.Recipe;

import com.olive_backend.Recipe.Vault.Difficulty;
import com.olive_backend.Recipe.Vault.Ingredient.IngredientDTO;

import java.time.LocalDateTime;
import java.util.List;

public class RecipeDTO {
    private Long id;
    private String title;
    private String instructions;
    private Difficulty difficulty;
    private String creatorName;
    private String imageUrl;
    private LocalDateTime createdDate;
    private List<IngredientDTO> ingredients;

    // Constructor
    public RecipeDTO() {
    }

    public RecipeDTO(Long id, String title, String instructions, Difficulty difficulty, String creatorName,
            LocalDateTime createdDate, String imageUrl, List<IngredientDTO> ingredients) {
        this.id = id;
        this.title = title;
        this.instructions = instructions;
        this.difficulty = difficulty;
        this.creatorName = creatorName;
        this.createdDate = createdDate;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getInstruction() {
        return instructions;
    }

    public void setInstructions(String description) {
        this.instructions = instructions;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

}
