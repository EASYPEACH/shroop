<template>
  <router-view />
</template>

<script setup>
import { useApiLoading } from "@/store/useLoading";
import api from "./api";

api.interceptors.request.use(
  (config) => {
    if (
      !(
        config.url.includes("likes") ||
        config.url.includes("check") ||
        config.url.includes("phone")
      )
    ) {
      const loadingStore = useApiLoading();
      loadingStore.setIsLoading(true);
    }

    return config;
  },
  (err) => {
    return Promise.reject(err);
  },
);
api.interceptors.response.use(
  (config) => {
    const loadingStore = useApiLoading();
    loadingStore.setIsLoading(false);
    return config;
  },
  (err) => {
    return Promise.reject(err);
  },
);
</script>
