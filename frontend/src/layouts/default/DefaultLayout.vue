<template>
  <v-app>
    <default-bar />
    <default-view />
    <Footer />
    <notify-bar />
  </v-app>
</template>

<script setup>
import DefaultBar from "./AppBar.vue";
import DefaultView from "./MainView.vue";
import Footer from "./DefaultFooter.vue";
import NotifyBar from "@/components/NotifyBar.vue";
</script>
