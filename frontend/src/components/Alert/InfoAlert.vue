<template>
  <v-alert
    density="compact"
    border="start"
    border-color="subBlue"
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
