<template>
  <v-app>
    <default-bar />
    <default-view />
    <Footer />
    <notify-bar />
    <api-loader v-if="loadingStore.isLoading" />
    <hamburger-button v-if="isMobile" />
    <side-navigation v-if="mobileNavStore.isMobileNav && isMobile" />
  </v-app>
</template>

<script setup>
import { useApiLoading } from "@/store/useLoading";
import { useMobileNav } from "@/store/useMobileNav";
import { useDisplay } from "vuetify";
import DefaultBar from "./AppBar.vue";
import DefaultView from "./MainView.vue";
import Footer from "./DefaultFooter.vue";
import NotifyBar from "@/components/NotifyBar.vue";
import ApiLoader from "@/components/ApiLoader.vue";
import SideNavigation from "@/components/Navigation/SideNavigation.vue";
import HamburgerButton from "@/components/Button/HamburgerButton.vue";
import { ref, watch } from "vue";

const loadingStore = useApiLoading();
const mobileNavStore = useMobileNav();

const display = useDisplay();
const isMobile = ref(display.mdAndDown);

watch(isMobile, (check) => {
  if (!check && mobileNavStore.isMobileNav) {
    mobileNavStore.setIsMobileNav(false);
  }
});
</script>
