import { defineStore } from "pinia";

export const useRecipeStore = defineStore("recipeStore", {
  state: () => ({
    recipes: [],
    error: null,
  }),

  actions: {
    async fetchRecipes() {
      try {
        const res = await fetch("http://localhost:8080/api/recipes");
        if (!res.ok) {
          throw new Error("no data available");
        }
        this.recipes = await res.json();
      } catch (err) {
        this.error = err.message;
        console.error(err.message);
      }
    },

    async fetchRecipeById(id) {
      try {
        const res = await fetch("http://localhost:8080/api/recipes/" + id);
        if (!res.ok) throw new Error("Recipe not found");
        return await res.json();
      } catch (err) {
        this.error = err.message;
        console.error(err.message);
      }
    },
  },
});
