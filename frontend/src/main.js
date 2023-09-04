/**
 * main.js
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */

// Components
import { createApp } from "vue";
import App from "./App.vue";
import AOS from "aos";
import "aos/dist/aos.css";

// Composables

// Plugins
import { registerPlugins } from "@/plugins";

import "./styles/global.style.css";

const app = createApp(App);

registerPlugins(app);

app.use(AOS.init()).mount("#app");
