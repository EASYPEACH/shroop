<template>
  <div class="wrapper">
    <v-card
      class="mx-auto rounded-0"
      min-width="250"
      @click="() => $router.push(`/detail/${productCardData.id}`)"
    >
      <v-img
        cover
        class="align-end text-white"
        height="200"
        :src="
          productCardData.productImgList !== undefined
            ? productCardData.productImgList.filter((img) => !img.isDefect)[0]
                .productImgUrl
            : productCardData.productImgUrl
        "
      >
      </v-img>
      <v-divider />
      <v-card-text class="pt-3">
        {{ formatDate(productCardData.createDate) }}
        <span
          >&nbsp;
          {{
            productCardData.categoryName !== undefined
              ? productCardData.categoryName
              : productCardData.category.name
          }}</span
        >
      </v-card-text>
      <v-card-subtitle>
        <p>{{ productCardData.title }}</p>
      </v-card-subtitle>
      <div class="product-status">
        <transaction-badge v-if="productCardData.transactionStatus !== null" />
        <p>{{ productCardData.price.toLocaleString() }} 원</p>
      </div>
    </v-card>
    <like-button
      class="like-btn"
      :product="productCardData"
      @handle-click-like="$emit('handle-click-like')"
    />
  </div>
</template>

<script setup>
import { formatDate } from "@/utils";
import { LikeButton } from "./Button";
import TransactionBadge from "./TransactionBadge.vue";
defineProps({
  productCardData: Object,
});
defineEmits(["handle-click-like"]);
</script>

<style lang="scss" scoped>
.wrapper {
  position: relative;
}
.like-btn {
  position: absolute;
  top: 0;
  color: #fff;
  right: 0;
}
.v-img {
  background-color: #fff;
}
.v-card {
  padding: 0 0 20px 0;
  cursor: pointer;
}
.v-card-subtitle {
  width: 250px;
  color: black;
  opacity: 1;
  font-size: 18px;
  padding: 10px 15px;
  font-weight: 600;
  p {
    padding: 5px 0;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}
.product-status {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
  align-items: center;
  padding: 0 15px;
  font-size: 20px;
}
.v-card-text {
  padding-bottom: 0;
  padding-left: 1rem;
  color: gray;
  span {
    color: rgb(var(--v-theme-subBlue));
  }
}

.v-card-actions {
  display: flex;
  justify-content: space-between;
}

.v-btn {
  font-size: medium;
}
</style>
