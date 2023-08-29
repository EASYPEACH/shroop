<template>
  <div>
    <section class="main">
      <div>
        <div>
          <h2>
            <span class="color-text">슈룹</span>: <br />중고거래의 안심우산
          </h2>
          <br />
          <p><span class="color-text">슈룹</span>은 우산의 순 우리말 입니다</p>
          <p>중고거래가 불안한 여러분께</p>
          <p>안전한 우산이 되어드리겠습니다</p>
        </div>
      </div>
      <div v-if="isLaptop">
        <img src="@/assets/image/white_logo.png" alt="슈룹 로고" />
      </div>
    </section>
    <content-layout>
      <Title title="최근 판매" />
      <div class="products">
        <ul class="products__list">
          <li v-for="product in productCardData" :key="product.id">
            <product-card
              v-if="isLaptop"
              :productCardData="product"
              @handle-click-like="product.like = !product.like"
            />
            <product-banner
              v-else
              :product="product"
              @handle-click-like="product.like = !product.like"
              isHeart
            />
          </li>
          <v-btn class="plusbtn" variant="text">
            <router-link to="/products"
              >더보기 <v-icon icon="mdi-chevron-right"></v-icon>
            </router-link>
          </v-btn>
        </ul>
      </div>
    </content-layout>
  </div>
</template>

<script setup>
import { onBeforeMount, ref } from "vue";
import { useDisplay } from "vuetify";
import Title from "@/components/Title/MainTitle.vue";
import ProductCard from "@/components/Card/ProductCard.vue";
import ContentLayout from "@/layouts/ContentLayout.vue";
import ProductBanner from "@/components/Banner/ProductBanner.vue";
import { getApi } from "@/api/modules";

const productCardData = ref([]);
const display = useDisplay();
const isLaptop = ref(display.mdAndUp);

onBeforeMount(async () => {
  const data = await getApi({
    url: "/api/products",
  });
  productCardData.value = data.slice(0, 6);
});
</script>

<style lang="scss" scoped>
h2 {
  margin-top: 50px;
}
.main {
  width: 100%;
  height: 100%;
  display: flex;
  gap: 50px;
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
        transform: translateY(0);
      }
      50% {
        transform: translateY(-10px);
      }
      100% {
        transform: translateY(0);
      }
    }
  }
  .color-text {
    color: rgb(var(--v-theme-info));
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
.products {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  .products__list {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
  }
  .plusbtn {
    color: rgb(var(--v-theme-subBlue));
    place-self: flex-end;
    margin-top: 11px;
    grid-column: 1 / -1;
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
