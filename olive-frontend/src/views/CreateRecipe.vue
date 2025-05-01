<template>
  <div class="create">
    <!-- Success Message -->
    <el-alert
      v-if="showSuccess"
      title="Recipe added successfully!"
      type="success"
      show-icon
      closable
      @close="showSuccess = false"
      style="margin-bottom: 20px"
    />

    <!-- Recipe Creation Form -->
    <el-form
      ref="formRef"
      :model="form"
      :rules="rules"
      label-position="top"
      label-width="auto"
      @submit.prevent="handleSubmit"
    >
      <!-- Title -->
      <el-form-item label="Title" prop="title">
        <el-input v-model="form.title" placeholder="Enter recipe title" />
      </el-form-item>

      <!-- Ingredients -->
      <el-form-item label="Ingredients (press Enter to add)" prop="ingredients">
        <el-input
          v-model="ingredient"
          @keydown.enter.prevent="handleKeydown"
          placeholder="e.g. Chicken"
        />
        <el-text v-if="ingredientError" type="danger">{{
          ingredientError
        }}</el-text>

        <div class="pill" v-for="(ing, index) in form.ingredients" :key="ing">
          {{ ing }}
          <span class="delete-x" @click="removeIngredient(index)">×</span>
        </div>
      </el-form-item>

      <!-- Instructions -->
      <el-form-item label="Instructions" prop="instructions">
        <el-input
          type="textarea"
          v-model="form.instructions"
          :autosize="{ minRows: 4, maxRows: 6 }"
          placeholder="Step-by-step instructions"
        />
      </el-form-item>

      <!-- Difficulty -->
      <el-form-item label="Difficulty" prop="difficulty">
        <el-select v-model="form.difficulty" placeholder="Select difficulty">
          <el-option label="Easy" value="Easy" />
          <el-option label="Medium" value="Medium" />
          <el-option label="Hard" value="Hard" />
        </el-select>
      </el-form-item>

      <!-- Creator -->
      <el-form-item label="Creator" prop="creator_name">
        <el-input v-model="form.creator_name" placeholder="Enter your name" />
      </el-form-item>

      <!-- Submit Button -->
      <el-form-item>
        <div class="submit-button">
          <el-button type="primary" native-type="submit">Add Recipe</el-button>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus"; // optional toast for error
import { useRecipeStore } from "../stores/recipeStore";

const router = useRouter();
const recipeStore = useRecipeStore();

const form = ref({
  title: "",
  ingredients: [],
  instructions: "",
  difficulty: "",
  creator_name: "",
});

const ingredient = ref("");
const ingredientError = ref("");
const showSuccess = ref(false);
const formRef = ref(null);

const rules = {
  title: [
    { required: true, message: "Title is required", trigger: "blur" },
    { min: 3, message: "Title must be at least 3 characters", trigger: "blur" },
  ],
  instructions: [
    { required: true, message: "Instructions are required", trigger: "blur" },
  ],
  difficulty: [
    {
      required: true,
      message: "Please select a difficulty",
      trigger: "change",
    },
  ],
  ingredients: [
    {
      validator: (rule, value, callback) => {
        if (!value || value.length < 1) {
          callback(new Error("At least one ingredient is required"));
        } else {
          callback();
        }
      },
      trigger: "change",
    },
  ],
  creator_name: [
    { required: true, message: "Creator name is required", trigger: "blur" },
  ],
};

const handleKeydown = () => {
  const trimmed = ingredient.value.trim().toLowerCase();

  if (!trimmed) {
    ingredientError.value = "Ingredient cannot be empty.";
  } else if (form.value.ingredients.includes(trimmed)) {
    ingredientError.value = "Ingredient already added.";
  } else {
    form.value.ingredients.push(trimmed);
    ingredientError.value = "";
  }

  ingredient.value = "";
};

const removeIngredient = (index) => {
  form.value.ingredients.splice(index, 1);
};

const handleSubmit = async () => {
  formRef.value.validate(async (valid) => {
    if (!valid) return;
    const recipe = {
      ...form.value,
      number_of_ingredients: form.value.ingredients.length,
      creation_date: new Date().toISOString().split("T")[0],
    };

    await recipeStore.fetchRecipes();

    // Check for duplicate recipe title
    const duplicate = recipeStore.recipes.find(
      (r) => r.title.toLowerCase() === recipe.title.toLowerCase()
    );

    if (duplicate) {
      ElMessage.error("A recipe with this title already exists!");
      return;
    }

    await fetch("http://localhost:3000/recipes", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(recipe),
    });

    showSuccess.value = true;

    // Clear form
    form.value = {
      title: "",
      ingredients: [],
      instructions: "",
      difficulty: "",
    };

    // redirect after a short delay
    setTimeout(() => {
      router.push("/");
    }, 1500);
  });
};
</script>

<style scoped>
.create {
  max-width: 600px;
  margin: 40px auto;
}

.pill {
  display: inline-block;
  margin: 8px 8px 0 0;
  color: #444;
  background: #ddd;
  padding: 8px 24px 8px 12px;
  border-radius: 20px;
  font-size: 14px;
  position: relative;
}

.delete-x {
  position: absolute;
  top: 0px;
  right: 6px;
  color: #999;
  font-weight: bold;
  cursor: pointer;
}
.submit-button {
  text-align: right;
  width: 100%;
}
</style>
