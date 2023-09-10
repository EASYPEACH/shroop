/**
 * main.js
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */

// Components
import { createApp } from "vue";
import App from "./App.vue";
import "aos/dist/aos.css";

// Composables

// Plugins
import { registerPlugins } from "@/plugins";
import AOS from "aos";

import "./styles/global.style.css";

if ("serviceWorker" in navigator) {
  navigator.serviceWorker.register(
    import.meta.env.MODE === "production"
      ? "/service-worker.js"
      : "/dev-sw.js?dev-sw",
    { type: import.meta.env.MODE === "production" ? "classic" : "module" },
  );
}

const app = createApp(App);

registerPlugins(app);
AOS.init();
app.mount("#app");
