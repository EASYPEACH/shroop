<template>
  <v-btn :variant="variant" @click="handler"> {{ text }} </v-btn>
</template>

<script setup>
defineProps({
  text: String,
  handler: Function,
  variant: { type: String, default: "text" },
});
</script>

<style lang="scss" scoped>
.v-btn {
  color: rgb(var(--v-theme-subBlue));
  height: fit-content;
}
</style>
