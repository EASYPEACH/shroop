<template>
  <v-btn variant="text" @click="$emit('handle-click-like', product.id)">
    <v-icon :icon="product.like ? 'mdi-heart' : 'mdi-heart-outline'" />
  </v-btn>
</template>

<script setup>
defineProps({
  product: {
    type: Number,
    required: true,
  },
});
defineEmits(["handle-click-like"]);
</script>

<style lang="scss" scoped>
.v-icon {
  color: rgb(var(--v-theme-heartRed));
}
</style>
