<template>
  <router-view />
</template>

<script setup>
import { useRouter } from "vue-router";
import { useApiLoading } from "@/store/modules";
import api from "./api";

const loadingStore = useApiLoading();
const router = useRouter();

api.interceptors.request.use(
  (config) => {
    if (
      (config.url.includes("products") && config.method === "post") ||
      (config.url.includes("products") && config.method === "patch") ||
      config.url.includes("reports") ||
      config.url.includes("return") ||
      config.url.includes("profile")
    ) {
      loadingStore.setIsLoading(true);
    }

    return config;
  },
  (err) => {
    loadingStore.setIsLoading(false);
    if (err.response.status === 403) {
      router.go(0);
      alert("로그아웃 되었습니다");
    }
    return Promise.reject(err);
  },
);
api.interceptors.response.use(
  (config) => {
    loadingStore.setIsLoading(false);
    return config;
  },
  (err) => {
    loadingStore.setIsLoading(false);
    if (err.response.status === 403) {
      router.go(0);
      alert("로그아웃 되었습니다");
    }
    return Promise.reject(err);
  },
);
</script>
