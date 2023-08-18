<template>
  <div>
    <div class="titlebox">
      <h3>{{ title }}</h3>
      <span>*필수항목</span>
    </div>
    <v-divider
      :thickness="3"
      class="border-opacity-100"
      color="mainGray"
    ></v-divider>
  </div>
</template>

<script setup>
defineProps({
  title: String,
});
</script>

<style lang="scss" scoped>
.titlebox {
  display: flex;
  align-items: center;
  gap: 10px;
  h3 {
    padding: 20px 0;
    font-weight: 600;
    font-size: 18px;
  }
  span {
    font-size: 14px;
    color: red;
  }
}
</style>
