<!-- <template>
  <div>
    <div class="dashboard-container">
      <h1>Dashboard</h1>
      <div v-if="recipes.length">
        <RecipeList :recipes="recipes" />
      </div>
    </div>
  </div>
</template> -->

<template>
  <div class="dashboard-container">
    <el-card shadow="hover">
      <h2>Recipe Dashboard</h2>

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
            width="100%"
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
        <el-table-column prop="title" label="Title" />
        <el-table-column prop="difficulty" label="Difficulty" />
        <el-table-column
          prop="number_of_ingredients"
          label="Ingredient Count"
        />
        <el-table-column prop="creator_name" label="Creator" />
        <el-table-column prop="creation_date" label="Creation Date" />
        <el-table-column label="Actions" width="120">
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
    </el-card>
  </div>
</template>

<!-- <script>
import RecipeList from "../components/RecipeList.vue";
import getRecipes from "../composables/getRecipes";
export default {
  name: "Dashboard",
  components: { RecipeList },
  setup() {
    const { recipes, error, load } = getRecipes();
    load();
    return { recipes, error };
  },
};
</script> -->

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
    const matchesTitle = recipe.title
      .toLowerCase()
      .includes(searchTitle.value.toLowerCase());
    const matchesDifficulty = filterDifficulty.value
      ? recipe.difficulty === filterDifficulty.value
      : true;
    const matchesIngredientCount =
      filterIngredientCount.value !== null
        ? recipe.number_of_ingredients === filterIngredientCount.value
        : true;
    return matchesTitle && matchesDifficulty && matchesIngredientCount;
  });
});

const paginatedRecipes = computed(() => {
  const start = (currentPage.value - 1) * pageSize;
  return filteredRecipes.value.slice(start, start + pageSize);
});

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

<style></style>
