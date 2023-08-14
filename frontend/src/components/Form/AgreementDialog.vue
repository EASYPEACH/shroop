<template>
  <v-dialog
    :modelValue="dialog"
    @change="$emit('change', $event.target.value)"
    max-width="400"
    persistent
  >
    <v-card>
      <v-card-title class="text-h5 text-sm bg-grey-lighten-3">
        {{ title }}
      </v-card-title>
      <v-card-text>
        {{ contentText }}
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-btn variant="text" @click="$emit('change-to-disagree')">
          아니오
        </v-btn>
        <v-spacer></v-spacer>
        <v-btn
          color="subBlue"
          variant="tonal"
          @click="$emit('change-to-agree')"
        >
          예, 동의합니다.
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
defineProps({
  dialog: Boolean,
  title: String,
  contentText: String,
});
defineEmits(["change-to-agree", "change-to-disagree"]);
</script>

<style lang="scss" scoped></style>
