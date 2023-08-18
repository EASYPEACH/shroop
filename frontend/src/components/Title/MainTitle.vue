<template>
  <h2>{{ props.title }}</h2>
</template>

<script setup>
const props = defineProps({
  title: String,
});
</script>

<style lang="scss" scoped>
h2 {
  text-align: center;
  padding: 30px 0;
  font-size: 1.8rem;
}
</style>
