import { createRouter, createWebHistory } from "vue-router";
import { useCheckLogin } from "@/store/useCheckLogin";
import { getApi } from "@/api/modules";
import DefaultLayoutView from "@/layouts/default/DefaultLayout.vue";
import HomeView from "@/views/HomeView.vue";
import LoginView from "@/views/LoginView.vue";
import SignupView from "@/views/SignupView.vue";
import MypageView from "@/views/MypageView.vue";
import PhoneAuthView from "@/views/PhoneAuthView.vue";
import RegistProductView from "@/views/RegistProductView.vue";
import ProductDetailsView from "@/views/ProductDetailsView.vue";
import ReportView from "@/views/ReportView.vue";
import ReturnRequestView from "@/views/ReturnRequestView.vue";
import EditProileView from "@/views/EditProileView.vue";
import ProductsView from "@/views/ProductsView.vue";
import PurchaseRequestView from "@/views/PurchaseRequestView.vue";
import DeliveryRegistView from "@/views/DeliveryRegistView.vue";
import PurchaseCompleteView from "@/views/PurchaseCompleteView.vue";
import NotFound from "@/views/NotFound.vue";

const routes = [
  {
    path: "/",
    component: DefaultLayoutView,
    children: [
      {
        path: "",
        name: "Home",
        component: HomeView,
      },
      {
        path: "/login",
        name: "Login",
        component: LoginView,
      },
      {
        path: "/signup",
        name: "Signup",
        component: SignupView,
      },
      {
        path: "/mypage/:index",
        props: (route) => {
          /^[0-2]$/.test(route.params.param) ? route.params.param : "0";
        },
        name: "Mypage",
        component: MypageView,
        meta: { requiresAuth: true },
      },
      {
        path: "/regist",
        name: "Regist",
        component: RegistProductView,
        meta: { requiresAuth: true },
      },
      {
        path: "/edit/:id(\\d+)",
        name: "Edit",
        component: RegistProductView,
        meta: { requiresAuth: true },
      },
      {
        path: "/detail/:id(\\d+)",
        name: "Detail",
        component: ProductDetailsView,
      },
      {
        path: "/report/:id(\\d+)",
        name: "Report",
        component: ReportView,
        meta: { requiresAuth: true },
      },
      {
        path: "/return/:id(\\d+)",
        name: "Return",
        component: ReturnRequestView,
        meta: { requiresAuth: true },
      },
      {
        path: "/profileEdit/:id(\\d+)",
        name: "ProfileEdit",
        component: EditProileView,
        meta: { requiresAuth: true },
      },
      {
        path: "/products",
        name: "Products",
        component: ProductsView,
      },
      {
        path: "/purchase/:id(\\d+)",
        name: "Purchase",
        component: PurchaseRequestView,
        meta: { requiresAuth: true },
      },
      {
        path: "/purchaseComplete/:id(\\d+)",
        name: "PurchaseComplete",
        component: PurchaseCompleteView,
        meta: { requiresAuth: true },
      },
      {
        path: "/deliveryRegist/:id(\\d+)",
        name: "DeliveryRegist",
        component: DeliveryRegistView,
        meta: { requiresAuth: true },
      },
      {
        path: "/phone",
        name: "PhoneAuth",
        component: PhoneAuthView,
      },
      {
        path: "/notFound",
        name: "notFound",
        component: NotFound,
      },
      {
        path: "/:pathMatch(.*)*",
        redirect: "/notFound",
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

// 라우터가 이동하기 전에 로그인체크
// 로그인이 안되어 있으면 로그인페이지로 리다이렉트
router.beforeEach(async (to) => {
  const loginCheckStore = useCheckLogin();
  try {
    const response = await getApi({
      url: `/api/auth/`,
    });
    loginCheckStore.setIsLogin(response);
  } catch (error) {
    console.error(error);
  }
  if (to.meta.requiresAuth && !loginCheckStore.isLogin) return "/login";
});

export default router;
