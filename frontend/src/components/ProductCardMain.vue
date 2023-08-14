<template>
  <v-card class="mx-auto" max-width="400">
    <v-img
      class="align-end text-white"
      height="200"
      src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
      cover
    >
      <v-card-title>Top 10 Australian beaches</v-card-title>
    </v-img>

    <v-card-subtitle class="pt-4"> 게시물 제목 </v-card-subtitle>
    <v-card-subtitle class="pt-3"> 1000원 </v-card-subtitle>
    <v-card-text class="pt-3"> 2023-08-12 </v-card-text>
    <v-card-actions class="">
      <v-btn-toggle multiple variant="outlined" divided>
        <v-btn @click="HandleChangeHeart()">
          <v-icon v-if="!alignment" icon="mdi-cards-heart-outline"></v-icon>
          <v-icon v-else icon="mdi-cards-heart"></v-icon>
        </v-btn>
      </v-btn-toggle>
      <v-btn color="orange">
        <router-link :to="`/detail/${id}`"> 상세보기 </router-link>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script setup>
import { ref } from "vue";
defineProps({
  id: Number,
});

const alignment = ref(false);

const HandleChangeHeart = () => {
  alignment.value = !alignment.value;
};
</script>

<style lang="scss" scoped>
.v-card-subtitle {
  color: black;
  opacity: 1;
  font-size: large;
}

.v-card-text {
  padding-bottom: 0;
  padding-left: 1rem;
}

.v-card-actions {
  display: flex;
  justify-content: space-between;
}

.v-btn {
  font-size: medium;
}
</style>
