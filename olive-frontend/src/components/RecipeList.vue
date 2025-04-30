<template>
  <div>
    <div class="recipe-list">
      <div v-for="recipe in recipes" :key="recipe.id">
        <!-- <h3>{{ post.title }}</h3> -->
        <SingleRecipe :recipe="recipe" />
      </div>
    </div>
  </div>
</template>

<script>
// component imports

import SingleRecipe from "./SingleRecipe.vue";
import { onMounted, onUnmounted, onUpdated } from "vue";

export default {
  props: ["recipes"],
  components: { SingleRecipe },
  setup(props) {
    onMounted(() => console.log("component mounted"));
    onUnmounted(() => console.log("component unmounted"));
    onUpdated(() => console.log("component updated"));
  },
};
</script>

<style></style>
