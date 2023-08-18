<template>
  <div>
    <div class="text-subtitle-1 text-medium-emphasis">{{ label }}</div>
    <v-text-field
      density="compact"
      :placeholder="placeholderText"
      :value="modelValue"
      :prepend-inner-icon="icon"
      :rules="rules"
      @input="$emit('update:modelValue', $event.target.value)"
      variant="filled"
    ></v-text-field>
  </div>
</template>

<script setup>
defineProps({
  label: String,
  placeholderText: String,
  modelValue: String,
  icon: String,
  rules: Object,
});
defineEmits(["update:modelValue"]);
</script>

<style lang="scss" scoped></style>
