<template>
  <v-textarea
    :rules="[productDetailRule.required, productDetailRule.min]"
    :model-value="modelValue"
    :value="modelValue"
    @input="$emit('update:modelValue', $event.target.value)"
    variant="filled"
    auto-grow
    :label="label"
    rows="10"
    row-height="100"
  ></v-textarea>
</template>

<script setup>
import { productDetailRule } from "@/components/Form/data/formRules.js";
defineProps({
  label: String,
  modelValue: String,
});
defineEmits(["update:modelValue"]);
</script>

<style lang="scss" scoped></style>
