<template>
  <v-checkbox
    :id="`agree${cautionInfo.id}`"
    :checked="checked"
    @change="$emit('update:checked', !checked)"
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
defineProps({ cautionInfo: Object, checked: Boolean });
defineEmits(["update:checked"]);
</script>
<style lang="scss" scoped>
.caution__block {
  display: flex;
  justify-content: space-between;
  display: flex;
  flex-basis: 85%;
  flex-direction: column;
  justify-content: center;
  padding-left: 10px;
  p {
    word-break: keep-all;
    &:nth-child(1) {
      font-weight: bold;
    }
    &:nth-child(2) {
      color: rgb(var(--v-theme-subBlue));
    }
  }
}
</style>
