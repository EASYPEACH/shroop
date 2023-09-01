<template>
  <section>
    <v-progress-circular
      :size="50"
      color="white"
      indeterminate
    ></v-progress-circular>
  </section>
</template>

<script setup></script>

<style lang="scss" scoped>
section {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  position: fixed;
  top: 0;
  left: 0;
  z-index: 2000;
}
</style>
