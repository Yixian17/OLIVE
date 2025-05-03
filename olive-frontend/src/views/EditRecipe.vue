<template>
  <div class="edit">
    <el-card>
      <h2>Edit Recipe</h2>
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-position="top"
        label-width="auto"
        @submit.prevent
      >
        <!-- Title -->
        <el-form-item label="Title" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>

        <!-- Ingredients -->
        <el-form-item
          label="Ingredients (press Enter to add)"
          prop="ingredients"
        >
          <el-input
            v-model="ingredients"
            @keydown.enter.prevent="handleKeydown"
            placeholder="e.g. Chicken"
          />
          <el-text v-if="ingredientError" type="danger">{{
            ingredientError
          }}</el-text>

          <div class="pill" v-for="(ing, index) in form.ingredients" :key="ing">
            {{ ing.name }}
            <span class="delete-x" @click="removeIngredient(index)">×</span>
          </div>
        </el-form-item>

        <!-- Instructions -->
        <el-form-item label="Instructions" prop="instructions">
          <el-input
            type="textarea"
            v-model="form.instruction"
            :autosize="{ minRows: 4, maxRows: 6 }"
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
        <el-form-item label="Creator" prop="creatorName">
          <el-input v-model="form.creatorName" placeholder="Enter your name" />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleUpdate"
            >Save Changes</el-button
          >
          <el-button @click="backToView" plain>Cancel</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useRecipeStore } from "../stores/recipeStore";
import { ElMessage } from "element-plus";

const router = useRouter();
const route = useRoute();
const formRef = ref(null);
const ingredients = ref("");
const ingredientError = ref("");
const recipeStore = useRecipeStore();

const form = ref({
  title: "",
  ingredients: [],
  instructions: "",
  difficulty: "",
  creatorName: "",
});

const rules = {
  title: [
    { required: true, message: "Title is required", trigger: "blur" },
    { min: 3, message: "Title must be at least 3 characters", trigger: "blur" },
  ],
  instruction: [
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
};

const loadRecipe = async () => {
  const id = route.params.id;
  const res = await recipeStore.fetchRecipeById(id);
  form.value = res;
};

const removeIngredient = (index) => {
  form.value.ingredients.splice(index, 1);
};

const handleKeydown = () => {
  const trimmed = ingredients.value.trim();
  if (!trimmed) {
    ingredientError.value = "Ingredient cannot be empty.";
  } else if (form.value.ingredients.includes(trimmed)) {
    ingredientError.value = "Ingredient already added.";
  } else {
    form.value.ingredients.push({ name: trimmed });
    ingredientError.value = "";
  }
  ingredients.value = "";
};

const handleUpdate = () => {
  formRef.value.validate(async (valid) => {
    if (!valid) return;

    const updatedRecipe = {
      ...form.value,
      ingredients: form.value.ingredients.map((ing) => ({
        ingredientName: ing.name || ing.ingredientName || ing,
      })),
      instructions: form.value.instruction,
      number_of_ingredients: form.value.ingredients.length,
      createdDate: new Date().toISOString(),
    };
    console.log(JSON.stringify(updatedRecipe, null, 2));
    await fetch(`http://localhost:8080/api/recipes/${route.params.id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(updatedRecipe),
    })
      .then((response) => {
        if (!response.ok) throw new Error("Network response was not ok");
      })
      .catch((error) => {
        console.error("Error:", error);
      });

    ElMessage.success("Recipe updated successfully!");
    router.push("/recipes/" + route.params.id);
  });
};

const backToView = () => {
  router.push("/recipes/" + route.params.id);
};

onMounted(loadRecipe);
</script>

<style scoped>
.edit {
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
  top: 2px;
  right: 6px;
  color: #999;
  font-weight: bold;
  cursor: pointer;
}

.image-preview {
  width: 100%;
  height: 250px;
  object-fit: cover;
  object-position: center;
  border-radius: 8px;
  background-color: #f4f4f4;
}
</style>
