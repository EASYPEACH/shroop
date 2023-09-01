<template>
  <v-app>
    <default-bar />
    <default-view />
    <Footer />
    <notify-bar />
    <api-loader v-if="loadingStore.isLoading" />
  </v-app>
</template>

<script setup>
import { useApiLoading } from "@/store/useLoading";
import DefaultBar from "./AppBar.vue";
import DefaultView from "./MainView.vue";
import Footer from "./DefaultFooter.vue";
import NotifyBar from "@/components/NotifyBar.vue";
import ApiLoader from "@/components/ApiLoader.vue";

const loadingStore = useApiLoading();
</script>
