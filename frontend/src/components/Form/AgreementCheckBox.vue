<template>
  <v-checkbox
    :checked="checked"
    :rules="rules"
    @change="$emit('update:checked', !checked)"
    hide-details
    color="black"
  >
    <template v-slot:label>
      {{ agreementStatement }}
      <v-btn @click="$emit('show-dialog')" variant="plain" color="subGreen"
        >약관확인</v-btn
      >
    </template>
  </v-checkbox>
</template>

<script setup>
defineProps({
  agreementStatement: String,
  checked: Boolean,
  rules: Object,
});
defineEmits(["show-dialog", "update:checked"]);
</script>

<style lang="scss" scoped>
.v-btn {
  opacity: 1;
  font-weight: 600;
}
</style>
