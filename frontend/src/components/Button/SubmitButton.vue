<template>
  <v-btn class="form__button-submit" variant="tonal" type="submit">{{
    text
  }}</v-btn>
</template>

<script setup>
defineProps({
  text: String,
});
</script>

<style lang="scss" scoped>
.form__button-submit {
  width: 100%;
  padding: 15px 0;
  margin: 40px 0;
  height: auto;
  font-weight: 600;
  background: rgb(var(--v-theme-mainGray));
  opacity: 1;
  color: #fff;
  font-size: 1.1rem;
}
</style>
