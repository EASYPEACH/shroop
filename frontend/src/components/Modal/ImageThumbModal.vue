<template>
  <v-dialog :v-model="dialog" width="auto">
    <v-btn variant="text" @click="$emit('handle-close-modal')">
      <v-icon icon="mdi-close" />
    </v-btn>
    <v-card>
      <img :src="imgSrc" width="100%" />
    </v-card>
  </v-dialog>
</template>
<script setup>
defineProps({
  dialog: Boolean,
  imgSrc: String,
});
defineEmits(["handle-close-modal"]);
</script>

<style lang="scss" scoped>
.v-btn {
  color: #fff;
  font-size: 20px;
  width: 80px;
  height: 80px;
  padding: 0;
  border-radius: 50%;
  margin: 5px 0;
  place-self: center;
}
.v-card {
  width: 100%;
  img {
    width: 100%;
  }
}
</style>
