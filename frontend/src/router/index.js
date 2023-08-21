// Composables
import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/",
    component: () => import("@/layouts/default/DefaultLayout.vue"),
    children: [
      {
        path: "",
        name: "Home",
        component: () => import("@/views/HomeView.vue"),
      },
      {
        path: "/login",
        name: "Login",
        component: () => import("@/views/LoginView.vue"),
      },
      {
        path: "/signup",
        name: "Signup",
        component: () => import("@/views/SignupView.vue"),
      },
      {
        path: "/mypage",
        name: "Mypage",
        component: () => import("@/views/MypageView.vue"),
      },
      {
        path: "/regist",
        name: "Regist",
        component: () => import("@/views/RegistProductView.vue"),
      },
      {
        path: "/edit/:id(\\d+)",
        name: "Edit",
        component: () => import("@/views/RegistProductView.vue"),
      },
      {
        path: "/detail/:id(\\d+)",
        name: "Detail",
        component: () => import("@/views/ProductDetailsView.vue"),
      },
      {
        path: "/report/:id(\\d+)",
        name: "Report",
        component: () => import("@/views/ReportView.vue"),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  scrollBehavior() {
    return { top: 0 };
  },
});

export default router;
