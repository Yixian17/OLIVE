<template>
  <div class="recipe">
    <router-link :to="{ name: 'Details', params: { id: recipe.id } }">
      <h3>Title: {{ recipe.title }}</h3>
    </router-link>
    <h3>Difficulty: {{ recipe.difficulty }}</h3>
    <h3>Number of Ingredients: {{ recipe.number_of_ingredients }}</h3>
    <h3>Creator Name: {{ recipe.creator_name }}</h3>
    <h3>Creation Date: {{ recipe.creation_date }}</h3>
    <!-- <p>{{ snippet }}</p> -->
    <!-- <span v-for="tag in recipe.tags" :key="tag"> #{{ tag }} </span> -->
  </div>
</template>

<script>
import { computed } from "vue";

export default {
  props: ["recipe"],
  setup(props) {
    //   const snippet = computed(() => {
    //     return props.post.body.substring(0, 100) + "...";
    //   });
    //   return { snippet };
  },
};
</script>

<style></style>
