<template>
  <header>
    <v-btn
      class="back-btn"
      variant="text"
      icon="mdi-chevron-left"
      @click="() => $router.go(-1)"
    ></v-btn>
  </header>
</template>

<script setup></script>

<style lang="scss" scoped>
header {
  position: fixed;
  width: 100%;
  top: 0;
  left: 0;
  z-index: 20;
  color: #000;
  background: rgba(255, 255, 255, 1);
}
</style>
