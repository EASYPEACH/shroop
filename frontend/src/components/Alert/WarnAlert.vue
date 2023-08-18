<template>
  <v-alert
    border="bottom"
    border-color="warning"
    elevation="2"
    icon="mdi-alert-circle"
    :text="title"
  ></v-alert>
</template>

<script setup>
defineProps({
  title: String,
});
</script>
