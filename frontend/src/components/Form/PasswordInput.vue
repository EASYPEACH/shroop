<template>
  <div>
    <div
      class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between"
    >
      비밀번호
    </div>
    <v-text-field
      :append-inner-icon="visible ? 'mdi-eye' : 'mdi-eye-off'"
      :type="visible ? 'text' : 'password'"
      :model-value="modelValue"
      :rules="rules"
      density="compact"
      placeholder="비밀번호를 입력해주세요"
      prepend-inner-icon="mdi-lock-outline"
      variant="filled"
      autocomplete="off"
      @click:append-inner="$emit('toggle-visible')"
      @input="$emit('update:modelValue', $event.target.value)"
    ></v-text-field>
  </div>
</template>
<script setup>
defineProps({
  modelValue: String,
  visible: Boolean,
  rules: Object,
});
defineEmits(["update:modelValue", "toggle-visible"]);
</script>

<style lang="scss" scoped>
.guide_button {
  color: rgb(var(--v-theme-subBlue));
}
</style>
