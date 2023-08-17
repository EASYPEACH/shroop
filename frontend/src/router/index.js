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
        component: () => import("@/views/Home.vue"),
      },
      {
        path: "/login",
        name: "Login",
        component: () => import("@/views/Login.vue"),
      },
      {
        path: "/signup",
        name: "Signup",
        component: () => import("@/views/Signup.vue"),
      },
      {
        path: "/mypage",
        name: "Mypage",
        // component: () => import("@/views/Mypage.vue"),
      },
      {
        path: "/register",
        name: "Register",
        component: () => import("@/views/RegisterProduct.vue"),
      },
      {
        path: "/edit:id",
        name: "Edit",
        component: () => import("@/views/RegisterProduct.vue"),
      },
      {
        path: "/detail/:id",
        name: "Detail",
        // component: () => import("@/views/ProductDetails.vue"),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
