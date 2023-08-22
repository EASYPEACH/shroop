<template>
  <content-layout>
    <div class="filter">
      <div class="filter__catetory">
        <v-select
          v-model="currentCategory"
          @update:modelValue="handleUpdateCategory"
          label="카테고리"
          :items="categoryList"
          variant="solo"
        ></v-select>
      </div>
    </div>
    <div class="products">
      <ul class="products__list">
        <li
          v-for="productCardData in productCardsRef.slice(startIndex, endIndex)"
          :key="productCardData.id"
        >
          <product-card
            v-if="isLaptop"
            :productCardData="productCardData"
            @handle-click-like="productCardData.like = !productCardData.like"
          />
          <product-banner
            v-else
            :product="productCardData"
            @handle-click-like="productCardData.like = !productCardData.like"
            isHeart
          />
        </li>
      </ul>
    </div>
    <div class="pagination">
      <v-pagination
        v-model="currentPage"
        :length="pageCount"
        @click="handleChangePage"
      ></v-pagination>
    </div>
  </content-layout>
</template>

<script setup>
import { ref, watch, computed } from "vue";
import { useDisplay } from "vuetify";
import DUMMY from "@/consts/dummy";
import ProductCard from "@/components/Card/ProductCard.vue";
import ProductBanner from "@/components/Banner/ProductBanner.vue";
import ContentLayout from "@/layouts/ContentLayout.vue";

const display = useDisplay();
const isLaptop = ref(display.mdAndUp);

const categoryList = ref([
  `전체`,
  `가전제품`,
  `전자제품`,
  `옷`,
  `가구`,
  `신발`,
  `생필품`,
]);

const productCards = ref(DUMMY);
const productCardsRef = ref(productCards.value);
const perPage = ref(9); // 페이지당 상품 수
const currentPage = ref(1); // 현재 페이지
const productCount = computed(() => {
  return productCardsRef.value.length;
});
// 페이지 수
const pageCount = computed(() => {
  return Math.ceil(productCount.value / perPage.value);
});
const startIndex = ref(0); // 상품 시작 인덱스
const endIndex = ref(perPage.value); // 상품 마지막 인덱스
const handleChangePage = () => {
  startIndex.value = (currentPage.value - 1) * perPage.value;
  endIndex.value = Math.min(
    startIndex.value + perPage.value,
    productCount.value,
  );
  window.scrollTo({ top: 0 });
};
const currentCategory = ref(`전체`);
const handleUpdateCategory = () => {
  productCardsRef.value = productCards.value.filter((product) => {
    if (currentCategory.value === `전체`) {
      return true;
    } else {
      return product.category === currentCategory.value;
    }
  });
};

watch(productCardsRef, () => {
  currentPage.value = 1;
  handleChangePage();
});
</script>

<style lang="scss" scoped>
section {
  margin-top: 50px;
}

.filter {
  display: flex;
  flex-direction: row-reverse;
  margin-right: 30px;
  margin-left: 30px;

  .filter__catetory {
    width: 150px;
  }
}

.products {
  display: flex;
  justify-content: center;
  .products__list {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
  }
  @media (max-width: 960px) {
    .products__list {
      display: flex;
      flex-direction: column;
      width: 100%;
    }
  }
}

.pagination {
  margin-top: 20px;
}
</style>
