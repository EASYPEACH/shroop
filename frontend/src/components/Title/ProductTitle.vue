<template>
  <div class="productTitle">
    <div class="titlebox">
      <h3 :class="{ bigTitle: bigTitle }">{{ title }}</h3>
      <span v-if="isRequired">*필수항목</span>
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
  isRequired: Boolean,
  bigTitle: Boolean,
});
</script>

<style lang="scss" scoped>
.productTitle {
  padding: 50px 0 20px;
  .titlebox {
    display: flex;
    align-items: center;
    gap: 10px;
    h3 {
      padding: 20px 0;
      font-weight: 600;
      font-size: 18px;
      &.bigTitle {
        font-size: 1.5rem;
      }
    }
    span {
      font-size: 14px;
      color: red;
    }

    @media (max-width: 750px) {
      h3 {
        &.bigTitle {
          font-size: 1.3rem;
        }
      }
    }
  }
}
</style>
