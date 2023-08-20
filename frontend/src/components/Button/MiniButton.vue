<template>
  <v-btn variant="text" @click="handler"> {{ text }} </v-btn>
</template>

<script setup>
defineProps({
  text: String,
  handler: Function,
});
</script>

<style lang="scss" scoped>
.v-btn {
  color: rgb(var(--v-theme-subBlue));
  height: fit-content;
}
</style>
