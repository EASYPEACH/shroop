// Composables
import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/",
    component: () => import("@/layouts/default/Default.vue"),
    children: [
      {
        path: "",
        name: "Home",
        component: () =>
          import(/* webpackChunkName: "home" */ "@/views/Home.vue"),
      },
      {
        path: "/login",
        name: "Login",
        component: () =>
          import(/* webpackChunkName: "home" */ "@/views/Login.vue"),
      },
      {
        path: "/signup",
        name: "Signup",
        component: () =>
          import(/* webpackChunkName: "home" */ "@/views/Signup.vue"),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
