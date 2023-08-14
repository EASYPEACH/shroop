<template>
  <v-checkbox
    :modelValue="modelValue"
    :rules="rules"
    @change="$emit('change', $event.target.value)"
    hide-details
    color="black"
  >
    <template v-slot:label>
      {{ agreementStatement }}
      <v-btn @click="$emit('show-dialog')" variant="plain" color="subBlue"
        >약관확인</v-btn
      >
    </template>
  </v-checkbox>
</template>

<script setup>
defineProps({
  agreementStatement: String,
  modelValue: Boolean,
  rules: Object,
});
defineEmits(["show-dialog"]);
</script>

<style lang="scss" scoped>
.v-btn {
  opacity: 1;
  font-weight: 600;
}
</style>
