<template>
  <div class="dashboard-container">
    <el-card shadow="hover">
      <h2>Dashboard</h2>

      <!-- FILTER BAR -->
      <el-row :gutter="10" align="middle" style="margin-bottom: 20px">
        <el-col :span="6">
          <el-input
            v-model="searchTitle"
            placeholder="Search by title"
            clearable
            size="small"
          />
        </el-col>

        <el-col :span="6">
          <el-select
            v-model="filterDifficulty"
            placeholder="Filter by Difficulty"
            clearable
            size="small"
          >
            <el-option label="Easy" value="Easy" />
            <el-option label="Medium" value="Medium" />
            <el-option label="Hard" value="Hard" />
          </el-select>
        </el-col>

        <el-col :span="6">
          <el-input-number
            v-model="filterIngredientCount"
            :min="1"
            style="width: 100%; max-width: 180px"
            placeholder="Ingredient count"
            controls-position="right"
            clearable
            size="small"
          />
        </el-col>

        <el-col :span="6" style="text-align: right">
          <el-button @click="resetFilters" type="default" plain size="small">
            Reset
          </el-button>
        </el-col>
      </el-row>

      <!-- RECIPE TABLE -->
      <el-table
        v-loading="loading"
        :data="paginatedRecipes"
        style="width: 100%"
        border
      >
        <el-table-column label="Image">
          <template #default="scope">
            <img
              :src="getImageUrl(scope.row.imageUrl)"
              alt="recipe image"
              class="recipe-thumb"
              loading="lazy"
            />
          </template>
        </el-table-column>

        <el-table-column prop="title" label="Title" />
        <el-table-column label="Difficulty">
          <template #default="scope">
            <el-tag
              :type="
                {
                  Easy: 'success',
                  Medium: 'warning',
                  Hard: 'danger',
                }[scope.row.difficulty]
              "
            >
              {{ scope.row.difficulty }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="creatorName" label="Creator Name" />

        <el-table-column prop="createdDate" label="Creation Date">
          <template #default="scope">
            {{ formatDate(scope.row.createdDate) }}
          </template>
        </el-table-column>

        <el-table-column label="Actions" width="120" align="center">
          <template #default="scope">
            <el-button
              size="small"
              type="primary"
              plain
              @click="goToDetails(scope.row.id)"
            >
              View
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- PAGINATION -->
    <el-pagination
      style="margin-top: 20px; text-align: right"
      background
      layout="prev, pager, next"
      :total="filteredRecipes.length"
      :page-size="pageSize"
      :current-page="currentPage"
      @current-change="currentPage = $event"
    />
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRecipeStore } from "../stores/recipeStore";
import { useRouter } from "vue-router";

const recipeStore = useRecipeStore();
const router = useRouter();

const loading = ref(false);

const searchTitle = ref("");
const filterDifficulty = ref("");
const filterIngredientCount = ref(null);

const previewUrl = ref(null);

const currentPage = ref(1);
const pageSize = 5;

onMounted(async () => {
  loading.value = true;
  await recipeStore.fetchRecipes();
  console.log("Recipes fetched:", recipeStore.recipes);
  loading.value = false;
});

const filteredRecipes = computed(() => {
  return recipeStore.recipes.filter((recipe) => {
    // filter by title
    let matchTitle = true;
    if (searchTitle.value) {
      matchTitle = recipe.title
        .toLowerCase()
        .includes(searchTitle.value.toLowerCase());
    }

    // filter by difficulty
    let matchDifficulty;
    if (filterDifficulty.value) {
      matchDifficulty = recipe.difficulty === filterDifficulty.value;
    } else {
      matchDifficulty = true;
    }

    // filter by ingredient count
    let matchIngredientCount;
    if (filterIngredientCount.value) {
      matchIngredientCount =
        recipe.ingredients.length === filterIngredientCount.value;
    } else {
      matchIngredientCount = true;
    }
    return matchTitle && matchDifficulty && matchIngredientCount;
  });
});

const paginatedRecipes = computed(() => {
  const start = (currentPage.value - 1) * pageSize;
  return filteredRecipes.value.slice(start, start + pageSize);
});

const getImageUrl = (relativePath) => {
  return relativePath && relativePath !== "/uploads/null" && relativePath !== ""
    ? `https://back-end-oo5f.onrender.com${relativePath}`
    : "images/placeholder-image.jpg"; // fallback image
};

const formatDate = (datetime) => {
  if (!datetime) return "";
  return datetime.split("T")[0]; // Extracts only 'YYYY-MM-DD'
};

const resetFilters = () => {
  searchTitle.value = "";
  filterDifficulty.value = "";
  filterIngredientCount.value = null;
  currentPage.value = 1;
};

const goToDetails = (id) => {
  router.push(`/recipes/${id}`);
};
</script>

<style scoped>
.recipe-thumb {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 10px;
  border: 1px solid #eee;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease;
}
.recipe-thumb:hover {
  transform: scale(1.05);
}
</style>
