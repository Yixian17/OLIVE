import { createRouter, createWebHistory } from "vue-router";
import Details from "../views/Details.vue";
import Dashboard from "../views/Dashboard.vue";

const routes = [
  {
    path: "/",
    name: "Dashboard",
    component: Dashboard,
  },
  {
    path: "/recipes/:id",
    name: "Details",
    component: Details,
    props: true,
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes, // short for `routes: routes`
});

export default router;
