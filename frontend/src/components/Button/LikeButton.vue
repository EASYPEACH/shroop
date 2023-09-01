<template>
  <v-btn :variant="variant" @click="$emit('handle-click-like')">
    <v-icon :icon="product.isLike ? 'mdi-heart' : 'mdi-heart-outline'" />
    <span>{{ product.likesCount }}</span>
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
    withDefaults: "text",
  },
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
