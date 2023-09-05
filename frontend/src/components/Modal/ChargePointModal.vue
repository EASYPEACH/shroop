<template>
  <v-dialog :v-model="dialog" width="auto">
    <v-btn
      variant="text"
      color="#fff"
      class="close-btn"
      @click="$emit('handleCancel')"
    >
      <v-icon icon="mdi-close" />
    </v-btn>
    <v-card>
      <v-card-text>
        <v-form @submit.prevent="handleConfirm">
          <v-text-field :label="label" v-model="point" />
          <v-btn type="submit" color="subBlue" block>확인</v-btn>
        </v-form>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { patchApi } from "@/api/modules";
import { ref } from "vue";
const point = ref("");
const props = defineProps({
  dialog: Boolean,
  label: String,
  isCharged: Boolean,
});
const emits = defineEmits([
  "handleConfirm",
  "handleCancel",
  "handleReturnPointResult",
]);

const handleConfirm = async () => {
  try {
    const response = await patchApi({
      url: `/api/point/${props.isCharged ? "charging" : "exchanging"}`,
      data: Number(point.value),
    });
    emits("handleReturnPointResult", response);
    emits("handleCancel");
    point.value = "";
  } catch (error) {
    alert(error.response.data.message);
  }
};
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
