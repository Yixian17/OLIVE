package com.olive_backend.Recipe.Vault.Recipe;

import com.olive_backend.Recipe.Vault.Service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<RecipeDTO> findAll() {
        return recipeService.findAll();
    }

    @GetMapping("/{id}")
    public RecipeDTO findById(@PathVariable Long id) {
        return recipeService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Recipe recipe) {
        recipeService.create(recipe);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Recipe created");
        response.put("id", recipe.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody Recipe updatedRecipe) {
        System.out.println("Updated Recipe" + updatedRecipe);
        recipeService.update(id, updatedRecipe);
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            recipeService.uploadImage(id, file);
            return ResponseEntity.ok("Image uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        recipeService.delete(id);
    }
}
