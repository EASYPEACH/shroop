<template>
  <div>
    <section class="main">
      <div>
        <div>
          <h2>
            <span class="color-text service-title">슈룹</span>: <br />중고거래의
            안심우산
          </h2>
          <br />
          <p><span class="color-text">슈룹</span>은 우산의 순 우리말 입니다</p>
          <p>중고거래가 불안한 여러분께</p>
          <p>안전한 우산이 되어드리겠습니다</p>
          <v-btn @click="() => $router.push('/products')"
            >매물 보러가기 <v-icon icon="mdi-chevron-right"
          /></v-btn>
        </div>
      </div>
      <div v-if="isLaptop">
        <img src="@/assets/image/logo-white.png" alt="슈룹 로고" />
      </div>
    </section>
    <section class="subContent">
      <div
        class="subContent-text left"
        :data-aos="isLaptop ? 'fade-right' : 'fade-up'"
        data-aos-easing="linear"
        data-aos-duration="500"
      >
        <p>온라인에서 결제하는</p>
        <p>안전한 중고거래</p>
      </div>
      <img
        :data-aos="isLaptop ? 'fade-left' : 'fade-up'"
        :data-aos-delay="isLaptop ? 500 : 300"
        data-aos-easing="linear"
        data-aos-duration="500"
        :src="isLaptop ? purchaseImg : purchaseMobileImg"
        alt="purchase"
      />
    </section>
    <section class="subContent">
      <div
        v-if="!isLaptop"
        class="subContent-text right"
        data-aos="fade-up"
        data-aos-easing="linear"
        data-aos-duration="500"
      >
        <p>믿고 거래 할 수 있는</p>
        <p>상품 상세 정보</p>
      </div>
      <img
        :data-aos="isLaptop ? 'fade-right' : 'fade-up'"
        :data-aos-delay="isLaptop ? 500 : 300"
        data-aos-easing="linear"
        data-aos-duration="500"
        :src="isLaptop ? productDetailImg : productDetailMobileImg"
        alt="purchase"
      />
      <div
        v-if="isLaptop"
        class="subContent-text right"
        data-aos="fade-up"
        data-aos-easing="linear"
        data-aos-duration="500"
      >
        <p>믿고 거래 할 수 있는</p>
        <p>상품 상세 정보</p>
      </div>
    </section>

    <content-layout>
      <main-title title="최근 판매" data-aos="fade-up" data-aos-offset="100" />
      <div class="products">
        <ul class="products__list">
          <li
            v-for="product in productCardData"
            :key="product.id"
            data-aos="fade-up"
            data-aos-offset="100"
          >
            <product-card
              v-if="isLaptop"
              :productCardData="product"
              @handle-click-like="() => handleClickLike(product)"
            />
            <product-banner
              v-else
              :product="product"
              @handle-click-like="() => handleClickLike(product)"
              isHeart
            />
          </li>
        </ul>
        <v-btn
          class="plusbtn"
          variant="outlined"
          color="mainGreen"
          @click="() => $router.push('/products')"
        >
          더보기 <v-icon icon="mdi-chevron-right"></v-icon>
        </v-btn>
      </div>
    </content-layout>
  </div>
</template>

<script setup>
import { onBeforeMount, ref } from "vue";
import { useDisplay } from "vuetify";
import { getApi } from "@/api/modules";
import { toggleLikesProduct } from "@/utils";
import router from "@/router";

import { MainTitle } from "@/components/Title";
import ProductCard from "@/components/ProductCard.vue";
import ContentLayout from "@/layouts/ContentLayout.vue";
import { ProductBanner } from "@/components/Banner";

import purchaseImg from "@/assets/image/purchase.png";
import purchaseMobileImg from "@/assets/image/purchase-mobile.png";
import productDetailImg from "@/assets/image/product-detail.png";
import productDetailMobileImg from "@/assets/image/product-detail-mobile.png";

const productCardData = ref([]);
const display = useDisplay();
const isLaptop = ref(display.mdAndUp);

onBeforeMount(async () => {
  try {
    const response = await getApi({
      url: `/api/products/search?size=8&page=0&hasNotTransaction=true`,
    });
    productCardData.value = response.productList;
  } catch (error) {
    console.error(error);
  }
});

// 상품 좋아요 버튼 클릭 이벤트
const handleClickLike = async (product) => {
  try {
    await toggleLikesProduct(product, productCardData);
  } catch (err) {
    if (err.response.status === 403) {
      router.push("/login");
    }
  }
};
</script>

<style lang="scss" scoped>
h2 {
  margin-top: 50px;
}

section {
  &:nth-child(1),
  &:nth-child(3) {
    background: #000;
  }

  &:nth-child(2) {
    background: #151515;
  }
  &.main {
    width: 100%;
    height: 100vh;
    display: flex;
    gap: 200px;
    align-items: center;
    color: rgb(var(--v-theme-background));
    padding: 100px;
    background: #000;
    h2 {
      font-weight: 600;
      font-size: 36px;
    }
    > div:nth-child(1) {
      flex-basis: 50%;
      display: flex;
      justify-content: end;
      > div {
        width: fit-content;
      }
      .v-btn {
        margin-top: 30px;
        font-weight: 600;
      }
    }
    > div:nth-child(2) {
      flex-basis: 50%;
      display: flex;
      align-items: center;
      justify-content: flex-start;
      img {
        animation: bounce 1.5s linear infinite;
      }
      @keyframes bounce {
        0% {
          transform: translateY(0) rotate(20deg);
        }
        50% {
          transform: translateY(-10px) rotate(20deg);
        }
        100% {
          transform: translateY(0) rotate(20deg);
        }
      }
    }
    .color-text {
      color: rgb(var(--v-theme-subGreen));
      &.service-title {
        font-size: 55px;
      }
    }
    @media (max-width: 960px) {
      padding: 50px 30px;
      h2 {
        word-break: keep-all;
        margin: 0;
        margin-bottom: 30px;
        font-size: 40px;
      }
      > div:nth-child(1) {
        flex-basis: 100%;
        justify-content: center;
      }
    }
  }

  &.subContent {
    width: 100%;
    height: 100vh;
    display: flex;
    align-items: center;
    gap: 100px;
    justify-content: center;

    img {
      width: 40%;
    }

    &:nth-child(1) {
      background: #151515;
    }

    &:last-of-type {
      background: #151515;
    }

    .subContent-text {
      width: fit-content;
      color: #fff;
      font-weight: 600;
      font-size: 50px;
      z-index: 20;

      span {
        color: rgb(var(--v-theme-subGreen));
      }
      &.right {
        text-align: right;
      }
      p {
        width: 100%;
        white-space: nowrap;
      }
    }

    @media (max-width: 1300px) {
      .subContent-text {
        font-size: 35px;
      }
    }

    @media (max-width: 960px) {
      padding: 30px 0;
      flex-direction: column;
      img {
        width: 50%;
      }

      .subContent-text {
        text-align: center;
        &.right {
          text-align: center;
        }
      }
    }
  }
}

.products {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 50px;
  .products__list {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    gap: 50px;
    li {
      flex: 1;
    }
  }
  .plusbtn {
    color: #fff;
    place-self: flex-center;
    margin-top: 30px;
    padding: 0 20px;
    font-weight: 600;
  }

  @media (max-width: 960px) {
    .products__list {
      display: flex;
      flex-direction: column;
      width: 100%;
    }
  }
}
</style>
