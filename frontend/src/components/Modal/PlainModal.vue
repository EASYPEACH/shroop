<!-- eslint-disable vue/no-v-text-v-html-on-component -->
<template>
  <v-dialog :v-model="dialog" width="auto">
    <v-btn
      variant="text"
      color="#fff"
      class="close-btn"
      @click="$emit('handleCancle')"
    >
      <v-icon icon="mdi-close" />
    </v-btn>
    <v-card>
      <!-- eslint-disable-next-line vue/no-v-text-v-html-on-component -->
      <v-card-text v-html="modalText" />
      <v-card-actions>
        <v-btn color="subGreen" block @click="$emit('handleConfirm')"
          >확인</v-btn
        >
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
defineProps({
  modalText: String,
  dialog: Boolean,
});
defineEmits(["handleConfirm", "handleCancle"]);
</script>

<style lang="scss" scoped>
.close-btn {
  font-size: 20px;
  place-self: flex-end;
  margin-right: -51px;
}

.v-card-text {
  text-align: center;
}
</style>
