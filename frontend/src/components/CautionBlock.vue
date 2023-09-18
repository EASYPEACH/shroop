<template>
  <v-checkbox
    :rules="[defaultTextRule.required]"
    :id="`agree${cautionInfo.id}`"
    :checked="checked"
    class="purchase-agree"
    @change="$emit('update:checked', !checked)"
    style="
      .v-checkbox .v-label {
        width: auto;
      }
    "
  >
    <template v-slot:label>
      <div class="caution__block">
        <p>{{ cautionInfo.p1 }}</p>
        <p>{{ cautionInfo.p2 || "" }}</p>
      </div>
    </template>
  </v-checkbox>
</template>

<script setup>
import { defaultTextRule } from "./Form/data/formRules";
defineProps({ cautionInfo: Object, checked: Boolean });
defineEmits(["update:checked"]);
</script>
<style lang="scss" scoped>
.caution__block {
  display: flex;
  justify-content: space-between;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding-left: 10px;
  white-space: normal !important;
  p {
    word-break: keep-all;
    &:nth-child(1) {
      font-weight: bold;
    }
    &:nth-child(2) {
      color: rgb(var(--v-theme-subGreen));
    }
  }
}
</style>
