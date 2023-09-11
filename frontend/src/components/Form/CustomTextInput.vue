<template>
  <div>
    <div class="text-subtitle-1 text-medium-emphasis">{{ label }}</div>
    <v-text-field
      density="compact"
      :placeholder="placeholderText"
      :model-value="modelValue"
      :prepend-inner-icon="preppendIcon"
      :append-inner-icon="appendIcon"
      :rules="rules"
      :type="type"
      :disabled="disabled"
      :hide-details="hideDetails"
      @input="$emit('update:modelValue', $event.target.value)"
      variant="filled"
      @blur="blur"
    ></v-text-field>
  </div>
</template>

<script setup>
defineProps({
  label: String,
  placeholderText: String,
  modelValue: String,
  preppendIcon: String,
  appendIcon: String,
  rules: Object,
  hideDetails: Boolean,
  disabled: Boolean,
  blur: Function,
  type: {
    type: String,
    default: "text",
  },
});
defineEmits(["update:modelValue"]);
</script>

<style lang="scss" scoped></style>
