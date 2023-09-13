<template>
  <swiper
    loop
    :modules="modules"
    :space-between="50"
    :pagination="{ clickable: true }"
    :navigation="{
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    }"
  >
    <swiper-slide v-for="productImg in productImgs" :key="productImg.id">
      <v-img
        cover
        :aspect-ratio="1"
        :src="productImg.productImgUrl"
        :alt="productImg.title"
        @click="$emit('show-image-modal', productImg.productImgUrl)"
      >
      </v-img>
    </swiper-slide>
    <div class="swiper-button-prev swiper-navigation">
      <v-icon icon="mdi-chevron-left" />
    </div>
    <div class="swiper-button-next swiper-navigation">
      <v-icon icon="mdi-chevron-right" />
    </div>
    fdsaf
  </swiper>
</template>

<script setup>
defineProps({
  productContent: Object,
  productImgs: Object,
});
defineEmits(["show-image-modal"]);
import { Swiper, SwiperSlide } from "swiper/vue";
import { Navigation, Pagination, Scrollbar, A11y } from "swiper/modules";

import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";

const modules = [Navigation, Pagination, Scrollbar, A11y];
</script>

<style lang="scss" scoped>
.swiper {
  flex-basis: 50%;
  height: 450px;
  position: relative;

  @media (max-width: 750px) {
    margin-top: 49px;
    width: 100%;
    height: 100%;
  }

  .swiper-slide {
    width: 450px;
    height: 450px;
    @media (max-width: 750px) {
      width: 100%;
      height: 100%;
    }
  }
  .swiper-navigation {
    color: #fff;
    font-size: 20px;
    text-shadow: 0px 3px 0px rgb(0, 0, 0);
    width: 50px;
    height: 50px;
    border-radius: 50%;
    box-shadow: 0px 3px 0px 1px rgba(0, 0, 0, 0.7);
    z-index: 20;
    cursor: pointer;
    &:hover {
      transform: scale(1.01);
    }
  }

  .swiper-button-prev::after,
  .swiper-button-next::after {
    display: none;
  }
}
</style>
