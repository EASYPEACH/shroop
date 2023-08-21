<template>
  <v-navigation-drawer
    v-model="notifyStore.isShowNotify"
    location="right"
    temporary
  >
    <v-list density="compact" nav v-for="i in 10" :key="i">
      <div class="listitem">
        <v-icon icon="mdi-bell" />
        <p>알림 {{ i }}</p>
      </div>
      <v-divider></v-divider>
    </v-list>
  </v-navigation-drawer>
</template>

<script setup>
import { useShowNotify } from "@/store/useShowNotify";

const notifyStore = useShowNotify();
</script>

<style lang="scss" scoped>
.v-navigation-drawer {
  .listitem {
    display: flex;
    align-items: center;
    padding: 10px 0;
    gap: 20px;
    .v-icon {
      color: rgb(var(--v-theme-mainGray));
    }
  }
}
</style>
