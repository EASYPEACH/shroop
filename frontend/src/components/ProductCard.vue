<template>
  <v-card class="mx-auto rounded-0" min-width="250">
    <v-img
      class="align-end text-white"
      height="200"
      :src="
        productCardData.productImgList !== undefined
          ? productCardData.productImgList.filter((img) => !img.isDefect)[0]
              .productImgUrl
          : productCardData.productImgUrl
      "
      @click="() => $router.push(`/detail/${productCardData.id}`)"
      contain
    >
    </v-img>

    <v-divider />
    <v-card-text class="pt-3">
      상품등록일<br />
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
    <v-card-actions>
      <div>
        <like-button
          :product="productCardData"
          @handle-click-like="$emit('handle-click-like')"
        />
      </div>
      <v-btn
        color="subBlue"
        @click="() => $router.push(`/detail/${productCardData.id}`)"
      >
        상세보기
      </v-btn>
    </v-card-actions>
  </v-card>
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
.v-img {
  background-color: #fff;
}
.v-card-subtitle {
  width: 180px;
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
  text-align: right;
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
