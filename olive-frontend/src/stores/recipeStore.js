import { defineStore } from "pinia";

export const useRecipeStore = defineStore("recipeStore", {
  state: () => ({
    recipes: [],
    error: null,
  }),

  actions: {
    async fetchRecipes() {
      try {
        const res = await fetch(
          "https://back-end-oo5f.onrender.com/api/recipes"
        );
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
        const res = await fetch(
          "https://back-end-oo5f.onrender.com/api/recipes/" + id
        );
        if (!res.ok) throw new Error("Recipe not found");
        return await res.json();
      } catch (err) {
        this.error = err.message;
        console.error(err.message);
      }
    },

    async deleteRecipe(id) {
      const res = await fetch(
        `https://back-end-oo5f.onrender.com/api/recipes/${id}`,
        {
          method: "DELETE",
        }
      );

      if (!res.ok) {
        throw new Error("Failed to delete recipe");
      }

      // Optional: remove it from local store if needed
      this.recipes = this.recipes.filter((r) => r.id !== id);
    },
  },
});
