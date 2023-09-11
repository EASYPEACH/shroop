<template>
  <router-view />
</template>

<script setup>
import { useApiLoading } from "@/store/modules";

import api from "./api";

const loadingStore = useApiLoading();

api.interceptors.request.use(
  (config) => {
    if (
      (config.url.includes("products") && config.method === "post") ||
      (config.url.includes("products") && config.method === "patch") ||
      config.url.includes("reports") ||
      config.url.includes("return") ||
      (config.url.includes("profile") && config.method === "patch")
    ) {
      loadingStore.setIsLoading(true);
    }

    return config;
  },
  (err) => {
    loadingStore.setIsLoading(false);
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
    return Promise.reject(err);
  },
);
</script>
