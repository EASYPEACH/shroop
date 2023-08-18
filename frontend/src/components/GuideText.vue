<template>
  <ul class="guide">
    <h5>[{{ title }}]</h5>
    <li v-for="(list, idx) in guideList" :key="idx">
      <v-icon icon="mdi-check"></v-icon> {{ list }}
    </li>
  </ul>
</template>

<script setup>
defineProps({
  title: String,
  guideList: Array,
});
</script>

<style lang="scss" scoped>
.guide {
  padding: 10px 0;
  color: gray;
  h5 {
    color: rgb(var(--v-theme-subBlue));
  }
}
</style>
