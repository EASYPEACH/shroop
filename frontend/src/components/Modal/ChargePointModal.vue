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
      <v-card-text>
        <v-form @submit.prevent="handleConfirm">
          <v-text-field label="충전할 방울" />
          <v-btn type="submit" color="subBlue" block>확인</v-btn>
        </v-form>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script setup>
defineProps({
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

.v-card {
  width: 370px;
}
</style>
