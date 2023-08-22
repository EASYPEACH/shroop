<template>
  <div class="caution__block">
    <div class="caution__block-text">
      <p>{{ cautionInfo.p1 }}</p>
      <p>{{ cautionInfo.p2 || "" }}</p>
    </div>
    <div>
      <v-checkbox
        :checked="checked"
        @change="$emit('update:checked', !checked)"
      />
    </div>
  </div>
</template>

<script setup>
defineProps({ cautionInfo: Object, checked: Boolean });
defineEmits(["update:checked"]);
</script>
₩
<style lang="scss" scoped>
.caution__block {
  display: flex;
  justify-content: space-between;
  .caution__block-text {
    display: flex;
    flex-direction: column;
    justify-content: center;
    p {
      &:nth-child(1) {
        font-weight: bold;
      }
      &:nth-child(2) {
        background-color: lightgray;
      }
    }
  }
}
</style>
