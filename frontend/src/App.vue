<template>
  <router-view />
</template>

<script setup>
import { onBeforeMount } from "vue";
import { useRouter } from "vue-router";
import { getApi } from "@/api/modules";
import { useCheckLogin } from "@/store/useCheckLogin";

const router = useRouter();
const loginCheckStore = useCheckLogin();

router.beforeEach(async () => handleGetLoginInfo());
onBeforeMount(async () => handleGetLoginInfo());

const handleGetLoginInfo = async () => {
  try {
    const response = await getApi({
      url: `/api/auth/`,
    });
    if (response.result === true) {
      loginCheckStore.setIsLogin(true);
    } else {
      loginCheckStore.setIsLogin(false);
    }
  } catch (error) {
    console.log(error);
  }
};
</script>
