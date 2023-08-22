<template>
  <v-card class="mx-auto" min-width="250">
    <router-link :to="`/detail/${productCardData.id}`">
      <v-img
        class="align-end text-white"
        height="200"
        src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
        cover
      >
      </v-img>
    </router-link>
    <v-card-text class="pt-3"> {{ productCardData.createDate }} </v-card-text>
    <v-card-subtitle class="pt-4 text-h5">
      {{ productCardData.title }}
    </v-card-subtitle>
    <p class="price">{{ productCardData.price.toLocaleString() }} 원</p>
    <v-card-actions>
      <like-button
        :product="productCardData"
        @handle-click-like="$emit('handle-click-like')"
      />
      <v-btn color="subBlue">
        <router-link :to="`/detail/${productCardData.id}`">
          상세보기
        </router-link>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script setup>
import LikeButton from "../Button/LikeButton.vue";
defineProps({
  productCardData: Object,
});
defineEmits("handle-click-like");
</script>

<style lang="scss" scoped>
.v-card-subtitle {
  color: black;
  opacity: 1;
  font-size: 20px;
  font-weight: 600;
}
.price {
  padding: 0 20px;
  text-align: right;
}
.v-card-text {
  padding-bottom: 0;
  padding-left: 1rem;
  color: gray;
}

.v-card-actions {
  display: flex;
  justify-content: space-between;
}

.v-btn {
  font-size: medium;
}
</style>
