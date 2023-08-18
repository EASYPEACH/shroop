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
        path: "/regist",
        name: "Regist",
        component: () => import("@/views/RegistProduct.vue"),
      },
      {
        path: "/edit/:id(\\d+)",
        name: "Edit",
        component: () => import("@/views/RegistProduct.vue"),
      },
      {
        path: "/detail/:id(\\d+)",
        name: "Detail",
        component: () => import("@/views/ProductDetails.vue"),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return { top: 0 };
  },
});

export default router;
