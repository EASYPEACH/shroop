<template>
  <v-text-field
    density="compact"
    placeholder="휴대전화번호를 입력해주세요"
    :model-value="modelValue"
    :rules="rules"
    @input="$emit('update:modelValue', $event.target.value)"
    variant="filled"
    class="phoneNumber-input"
    @blur="blur"
  ></v-text-field>
</template>

<script setup>
defineProps({
  mobileCarrier: String,
  modelValue: String,
  rules: Object,
  blur: Function,
});
defineEmits(["update:modelValue"]);
</script>
