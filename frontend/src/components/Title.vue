<template>
  <h1>{{ props.title }}</h1>
</template>

<script setup>
  const props = defineProps({
    title: String,
  });
</script>

<style lang="scss" scoped>
  h1 {
    text-align: center;
    padding: 30px 0;
    font-size: 1.8rem;
  }
</style>
