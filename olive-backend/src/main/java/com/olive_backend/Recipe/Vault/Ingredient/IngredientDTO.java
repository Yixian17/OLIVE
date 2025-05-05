package com.olive_backend.Recipe.Vault.Ingredient;

public class IngredientDTO {
    private Long id;
    private String IngredientName;

    // Constructors
    public IngredientDTO() {}

    public IngredientDTO(Long id, String IngredientName) {
        this.id = id;
        this.IngredientName = IngredientName;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return IngredientName; }
    public void setName(String name) { this.IngredientName = IngredientName; }
}
