<template>
  <el-header class="navbar">
    <div class="navbar__content">
      <!-- Brand Logo / Name -->
      <div class="navbar__brand" @click="goHome">Recipe Vault</div>

      <!-- Navigation Links -->
      <div class="navbar__links">
        <el-button type="text" @click="goTo('/')">Dashboard</el-button>
        <el-button type="text" @click="goTo('/create')"
          >Create Recipe</el-button
        >
      </div>
    </div>
  </el-header>
</template>

<script setup>
import { useRouter } from "vue-router";
const router = useRouter();

const goTo = (path) => {
  router.push(path);
};

const goHome = () => {
  router.push("/");
};
</script>

<style scoped>
.navbar {
  background-color: #ffffff;
  border-bottom: 1px solid #ebeef5;
  padding: 0 24px;
  position: sticky;
  top: 0;
  z-index: 1000;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
}

.navbar__content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
}

.navbar__brand {
  font-weight: bold;
  font-size: 20px;
  cursor: pointer;
}

.navbar__links el-button {
  margin-left: 10px;
}
</style>
