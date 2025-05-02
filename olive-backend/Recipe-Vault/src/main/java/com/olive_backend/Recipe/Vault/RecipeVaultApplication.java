package com.olive_backend.Recipe.Vault;

import com.olive_backend.Recipe.Vault.Ingredient.Ingredient;
import com.olive_backend.Recipe.Vault.Recipe.Recipe;
import com.olive_backend.Recipe.Vault.Recipe.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class RecipeVaultApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeVaultApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(RecipeRepository recipeRepository) {
//		return args -> {
//			Recipe recipe = new Recipe();
//			recipe.setTitle("Test Spaghetti");
//			recipe.setDifficulty(Difficulty.Medium);
//			recipe.setInstructions("1. Boil pasta\n2. Add sauce\n3. Serve");
//			recipe.setCreatorName("Tester");
//			recipe.setCreatedDate(LocalDateTime.now());
//			recipe.setImageUrl("/images/test.png");
//
//			Ingredient ing1 = new Ingredient();
//			ing1.setIngredientName("Spaghetti");
//			ing1.setRecipe(recipe);
//
//			Ingredient ing2 = new Ingredient();
//			ing2.setIngredientName("Tomato Sauce");
//			ing2.setRecipe(recipe);
//
//			recipe.setIngredients(Arrays.asList(ing1, ing2));
//
//			recipeRepository.save(recipe);
//
//			System.out.println("✅ Test recipe saved!");
//		};
//	}

}
