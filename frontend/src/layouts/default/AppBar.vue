<template>
  <v-app-bar flat>
    <v-app-bar-title>
      <div class="logo">
        <img src="@/assets/image/logo.png" />
        <h2>shroop</h2>
      </div>
    </v-app-bar-title>
  </v-app-bar>
</template>

<script setup>
//
</script>

<style lang="scss" scoped>
.logo {
  width: 50px;
  aspect-ratio: 1 / 1;
  display: flex;
  align-items: center;
  gap: 5px;
  img {
    width: 30px;
  }
  h2 {
    color: rgb(var(--v-theme-mainGreen));
    font-weight: 600;
  }
}
</style>
