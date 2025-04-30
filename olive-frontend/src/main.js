import { createApp } from "vue";
import App from "./App.vue";
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import { createPinia } from "pinia";
import router from "./router";

createApp(App).use(createPinia()).use(router).use(ElementPlus).mount("#app");
