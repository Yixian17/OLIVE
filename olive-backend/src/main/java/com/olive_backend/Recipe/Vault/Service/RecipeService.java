package com.olive_backend.Recipe.Vault.Service;

import com.olive_backend.Recipe.Vault.Ingredient.Ingredient;
import com.olive_backend.Recipe.Vault.Ingredient.IngredientDTO;
import com.olive_backend.Recipe.Vault.Recipe.Recipe;
import com.olive_backend.Recipe.Vault.Recipe.RecipeDTO;
import com.olive_backend.Recipe.Vault.Recipe.RecipeRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<RecipeDTO> findAll() {
        List<Recipe> recipes = recipeRepository.findAll();

        return recipes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RecipeDTO findById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found"));
        return convertToDTO(recipe);
    }


    // This method converts a Recipe entity to a RecipeDTO
    @Transactional
    public RecipeDTO convertToDTO(Recipe recipe) {
        List<IngredientDTO> ingredientDTOs = recipe.getIngredients().stream()
                .map(ingredient -> new IngredientDTO(
                        ingredient.getId(),
                        ingredient.getIngredientName()
                ))
                .toList();

        return new RecipeDTO(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getInstructions(),
                recipe.getDifficulty(),
                recipe.getCreatorName(),
                recipe.getCreatedDate(),
                recipe.getImageUrl(),
                ingredientDTOs
        );
    }


    @Transactional
    public void create(Recipe recipe) {
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredient.setRecipe(recipe);
        }
        recipeRepository.save(recipe);
    }

    @Transactional
    public void update(Long id, Recipe updatedRecipe) {
        Recipe existing = recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found"));

        existing.setTitle(updatedRecipe.getTitle());
        existing.setDifficulty(updatedRecipe.getDifficulty());
        existing.setInstructions(updatedRecipe.getInstructions());
        existing.setImageUrl(updatedRecipe.getImageUrl());
        existing.setCreatorName(updatedRecipe.getCreatorName());
        existing.setCreatedDate(updatedRecipe.getCreatedDate());

        existing.getIngredients().clear();
        for (Ingredient ingredient : updatedRecipe.getIngredients()) {
            ingredient.setRecipe(existing);
            existing.getIngredients().add(ingredient);
        }

        recipeRepository.save(existing);
    }

    @Transactional
    public void uploadImage(Long recipeId, MultipartFile file) throws IOException {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found"));

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String uploadDir = "uploads/";
        Path filePath = Paths.get(uploadDir, filename);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        recipe.setImageUrl("/uploads/" + filename);
        recipeRepository.save(recipe);
    }

    @Transactional
    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }
}
