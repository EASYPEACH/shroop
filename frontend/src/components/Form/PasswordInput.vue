<template>
  <div>
    <div
      class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between"
    >
      비밀번호
      <router-link
        v-if="isNeedSearchState"
        class="text-caption guide_button"
        to="#"
        rel="noopener noreferrer"
      >
        비밀번호가 기억나지 않으시나요?</router-link
      >
    </div>
    <v-text-field
      :append-inner-icon="visible ? 'mdi-eye' : 'mdi-eye-off'"
      :type="visible ? 'text' : 'password'"
      :value="modelValue"
      :rules="rules"
      density="compact"
      placeholder="비밀번호를 입력해주세요"
      prepend-inner-icon="mdi-lock-outline"
      variant="filled"
      @click:append-inner="visible = !visible"
      @input="$emit('update:modelValue', $event.target.value)"
    ></v-text-field>
  </div>
</template>
<script setup>
defineProps({
  modelValue: String,
  visible: Boolean,
  rules: Object,
  isNeedSearchState: Boolean,
});
defineEmits(["update:modelValue"]);
</script>

<style lang="scss" scoped>
.guide_button {
  color: rgb(var(--v-theme-subBlue));
}
</style>
