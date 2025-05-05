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

}
