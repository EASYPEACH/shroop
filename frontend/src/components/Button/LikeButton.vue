<template>
  <v-btn :variant="variant" @click="$emit('handle-click-like')">
    <v-icon :icon="product.isLike ? 'mdi-heart' : 'mdi-heart-outline'" />
    <span v-if="!noCountText">{{ product.likesCount }}</span>
  </v-btn>
</template>

<script setup>
defineProps({
  product: {
    type: Object,
    required: true,
  },
  variant: {
    type: String,
    default: "text",
  },
  noCountText: Boolean,
});
defineEmits(["handle-click-like"]);
</script>

<style lang="scss" scoped>
.v-btn {
  display: flex;
  align-items: center;
}
.v-icon {
  color: rgb(var(--v-theme-heartRed));
}
</style>
