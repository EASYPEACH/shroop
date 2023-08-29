<template>
  <div class="banner">
    <div class="top">
      <span>{{ product.createDate }}</span>
      <span>{{ product.category.name }}</span>
    </div>

    <div class="banner__content">
      <div
        @click="
          () => (isPurchase ? null : $router.push(`/detail/${product.id}`))
        "
      >
        <img
          :src="
            product.productImgList.filter((img) => !img.isDefect)[0]
              .productImgUrl
          "
          :alt="product.title"
        />
        <div>
          <h4>{{ product.title }}</h4>
          <p>{{ product.price.toLocaleString() }}원</p>
        </div>
      </div>
      <slot />
      <like-button
        v-if="isHeart"
        :product="product"
        @handle-click-like="$emit('handle-click-like')"
      />
    </div>
  </div>
</template>

<script setup>
import LikeButton from "../Button/LikeButton.vue";

defineProps({
  product: {
    type: Object,
    required: true,
  },
  isPurchase: Boolean,
  isHeart: Boolean,
});
defineEmits(["handle-click-like"]);
</script>

<style lang="scss" scoped>
.banner {
  display: flex;
  flex-direction: column;
  .top {
    span + span {
      margin-left: 20px;
    }
    span:nth-child(1) {
      color: gray;
    }
    span:nth-child(2) {
      color: rgba(var(--v-theme-subBlue));
    }
  }
  .banner__content {
    display: flex;
    align-items: center;
    justify-content: space-between;
    border-bottom: 1px solid rgba(var(--v-theme-mainGray), 0.4);
    > div {
      display: flex;
      align-items: center;
      padding: 20px 0;
      gap: 20px;
      flex: 1;
      cursor: pointer;
      img {
        flex-basis: 100px;
        width: 100px;
        height: 100px;
        aspect-ratio: 1 / 1;
        object-fit: cover;
        object-position: center;
        border-radius: 10px;
      }
      h4 {
        font-weight: 600;
        font-size: 18px;
      }
    }
  }
}
</style>
