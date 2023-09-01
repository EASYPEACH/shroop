<template>
  <router-view />
</template>

<script setup>
import { useApiLoading } from "@/store/useLoading";
import api from "./api";

const loadingStore = useApiLoading();

api.interceptors.request.use(
  (config) => {
    if (
      !(
        config.url.includes("likes") ||
        config.url.includes("check") ||
        config.url.includes("phone")
      )
    ) {
      loadingStore.setIsLoading(true);
    }

    return config;
  },
  (err) => {
    loadingStore.setIsLoading(false);
    if (err.response.status === 500) {
      alert(
        "서버에 문제가 생겼습니다. 새로고침을 하거나 관리자에게 문의해주세요.",
      );
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
    if (err.response.status === 500) {
      alert(
        "서버에 문제가 생겼습니다. 새로고침을 하거나 관리자에게 문의해주세요.",
      );
    }
    return Promise.reject(err);
  },
);
</script>
