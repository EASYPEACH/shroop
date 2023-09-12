<template>
  <v-alert
    density="compact"
    border="start"
    border-color="subGreen"
    elevation="2"
    icon="mdi-check-decagram-outline"
    :text="title"
  ></v-alert>
</template>

<script setup>
defineProps({
  title: String,
});
</script>
