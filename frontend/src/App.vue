<template>
  <router-view />
</template>

<script setup>
import { useRouter } from "vue-router";
import { getApi } from "@/api/modules";
import { useCheckLogin } from "@/store/useCheckLogin";

const router = useRouter();
const loginCheckStore = useCheckLogin();

router.beforeEach(async (to, _, next) => {
  await handleGetLoginInfo();
  if (
    !(
      to.name === "Home" ||
      to.name === "Detail" ||
      to.name === "Login" ||
      to.name === "Signup" ||
      to.name === "Products" ||
      to.name === "PhoneAuth"
    ) &&
    !loginCheckStore.isLogin
  ) {
    next({ name: "Login" });
  } else {
    next();
  }
});

const handleGetLoginInfo = async () => {
  try {
    const response = await getApi({
      url: `/api/auth/`,
    });
    loginCheckStore.setIsLogin(response);
  } catch (error) {
    console.log(error);
  }
};
</script>
