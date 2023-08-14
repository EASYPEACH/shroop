<template>
  <v-text-field
    density="compact"
    placeholder="핸드폰 번호를 입력해주세요"
    :value="modelValue"
    :rules="rules"
    @input="$emit('update:modelValue', $event.target.value)"
    variant="filled"
    class="phoneNumber-input"
  ></v-text-field>
</template>

<script setup>
defineProps({
  mobileCarrier: String,
  modelValue: String,
  rules: Object,
});
defineEmits(["update:modelValue"]);
</script>
