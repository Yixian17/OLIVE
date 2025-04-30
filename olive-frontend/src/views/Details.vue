<!-- <template>
  <div>
    <div v-if="error" class="error">{{ error }}</div>
    <div v-if="recipe" class="recipe">
      <h3>{{ recipe.title }}</h3>
      <p class="pre">{{ recipe.creation_date }}</p>
    </div>
  </div>
</template>

<script>
import getRecipe from "../composables/getRecipe";

export default {
  props: ["id"],
  setup(props) {
    const { recipe, error, load } = getRecipe(props.id);

    load();
    return { recipe, error };
  },
};
</script>
<style scoped>
.tags a {
  margin-right: 10px;
}
.recipe {
  max-width: 1200px;
  margin: 0 auto;
}
.recipe p {
  color: #444;
  line-height: 1.5em;
  margin-top: 40px;
}
.pre {
  white-space: pre-wrap;
}
</style> -->

<template>
  <el-card v-if="recipe" class="details-container">
    <el-button type="text" @click="goBack" style="margin-bottom: 20px">
      ← Back to Dashboard
    </el-button>

    <h2>{{ recipe.title }}</h2>

    <el-descriptions :column="1" border>
      <el-descriptions-item label="Difficulty">
        {{ recipe.difficulty }}
      </el-descriptions-item>

      <el-descriptions-item label="Creator">
        {{ recipe.creator_name }}
      </el-descriptions-item>

      <el-descriptions-item label="Created On">
        {{ recipe.creation_date }}
      </el-descriptions-item>

      <el-descriptions-item label="Instructions">
        <div style="white-space: pre-wrap">
          {{ recipe.instructions || "No instructions provided." }}
        </div>
      </el-descriptions-item>

      <el-descriptions-item label="Ingredients">
        <el-tag
          v-for="(ingredient, index) in recipe.ingredients"
          :key="index"
          type="success"
          class="ingredient-tag"
        >
          {{ ingredient }}
        </el-tag>
      </el-descriptions-item>

      <el-descriptions-item label="Image" v-if="recipe.image_url">
        <img :src="recipe.image_url" alt="Recipe Image" class="recipe-image" />
      </el-descriptions-item>
    </el-descriptions>
  </el-card>

  <div v-else class="empty-message">
    <el-empty description="Recipe not found or still loading..." />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useRecipeStore } from "../stores/recipeStore";

const route = useRoute();
const router = useRouter();
const recipeStore = useRecipeStore();

const recipe = ref(null);

const loadRecipe = async () => {
  const id = route.params.id;
  const result = await recipeStore.fetchRecipeById(id);
  recipe.value = result;
};

const goBack = () => {
  router.push("/");
};

onMounted(loadRecipe);
</script>

<style scoped>
.details-container {
  padding: 24px;
  margin: 24px;
}

.ingredient-tag {
  margin-right: 8px;
  margin-top: 5px;
}

.recipe-image {
  max-width: 300px;
  border-radius: 8px;
  margin-top: 10px;
}

.empty-message {
  margin: 40px auto;
  text-align: center;
}
</style>
