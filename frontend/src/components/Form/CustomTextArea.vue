<template>
  <v-textarea
    :rules="[productDetailRule.required, productDetailRule.min]"
    :model-value="text"
    @input="updateText"
    variant="filled"
    auto-grow
    :label="label"
    rows="10"
    row-height="100"
  ></v-textarea>
</template>

<script setup>
import { productDetailRule } from "@/components/Form/data/formRules.js";
import { onBeforeMount, ref } from "vue";
const props = defineProps({
  label: String,
  modelValues: String,
});
const emit = defineEmits(["update:modelValues", "update-value"]);
const text = ref(props.modelValues);

onBeforeMount(() => {
  text.value = props.modelValues;
});

const updateText = (event) => {
  text.value = event.target.value;
  if (event.target.value.length > 255) {
    // event.target.value = event.target.value.substring(0, 255);
    event.target.value = text.value = event.target.value.substring(0, 255);
    emit("update:modelValues", event.target.value);
    emit("update-value", text.value);
    return;
  }
  emit("update:modelValues", event.target.value);
  emit("update-value", text.value);
};
</script>

<style lang="scss" scoped></style>
