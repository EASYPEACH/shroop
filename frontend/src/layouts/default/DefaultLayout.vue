<template>
  <v-app>
    <default-bar v-if="!toShowMobileAppBar" />
    <mobile-app-bar v-else-if="isMobile && toShowMobileAppBar" />
    <default-view />
    <Footer />
    <notify-bar />
    <api-loader v-if="loadingStore.isLoading" />
    <hamburger-button
      v-if="isMobile"
      :toShowMobileAppBar="toShowMobileAppBar"
    />
    <side-navigation v-if="mobileNavStore.isMobileNav && isMobile" />
  </v-app>
</template>

<script setup>
import { ref, watch, watchEffect } from "vue";
import { useApiLoading, useMobileNav } from "@/store/modules";
import { useDisplay } from "vuetify";
import DefaultBar from "./AppBar.vue";
import DefaultView from "./MainView.vue";
import Footer from "./DefaultFooter.vue";
import { NotifyBar, SideNavigation } from "@/components/SideBar";
import ApiLoader from "@/components/ApiLoader.vue";
import { HamburgerButton } from "@/components/Button";
import MobileAppBar from "./MobileAppBar.vue";
import { useRoute } from "vue-router";

const route = useRoute();
const loadingStore = useApiLoading();
const mobileNavStore = useMobileNav();
const toShowMobileAppBar = ref(false);

const display = useDisplay();
const isMobile = ref(display.smAndDown);

watch(isMobile, (check) => {
  if (!check && mobileNavStore.isMobileNav) {
    mobileNavStore.setIsMobileNav(false);
  }
});

watchEffect(() => {
  if (isMobile.value) {
    if (
      route.path.split("/")[1] === "detail" ||
      route.path.split("/")[1] === "regist" ||
      route.path.split("/")[1] === "edit" ||
      route.path.split("/")[1] === "report" ||
      route.path.split("/")[1] === "return"
    ) {
      toShowMobileAppBar.value = true;
    } else {
      toShowMobileAppBar.value = false;
    }
  }
});
</script>
