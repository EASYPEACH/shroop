<template>
  <v-btn
    class="form__button-submit"
    height="auto"
    variant="tonal"
    type="submit"
    :disabled="disabled"
    >{{ text }}</v-btn
  >
</template>

<script setup>
defineProps({
  text: String,
  disabled: Boolean,
});
</script>

<style lang="scss" scoped>
.v-btn {
  width: 100%;
  padding: 15px 0;
  margin: 40px 0;
  font-weight: 600;
  background: rgb(var(--v-theme-mainGray));
  opacity: 1;
  color: #fff;
  font-size: 1.1rem;

  &:disabled {
    opacity: 0.5;
  }
}
</style>
