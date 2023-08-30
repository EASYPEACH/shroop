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
          v-for="productCardData in productCards.slice(startIndex, endIndex)"
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
import { ref, watch, computed, onBeforeMount } from "vue";
import { useDisplay } from "vuetify";
import { getApi } from "@/api/modules";
import ProductCard from "@/components/Card/ProductCard.vue";
import ProductBanner from "@/components/Banner/ProductBanner.vue";
import ContentLayout from "@/layouts/ContentLayout.vue";

const display = useDisplay();
const isLaptop = ref(display.mdAndUp);

const categoryList = ref([]);

const productCardsOriginal = ref([]);
const productCards = ref([]);
const perPage = ref(9); // 페이지당 상품 수
const currentPage = ref(1); // 현재 페이지

const productCount = computed(() => {
  return productCards.value.length;
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
const currentCategory = ref(["전체"]);

const handleUpdateCategory = () => {
  productCards.value = [...productCardsOriginal.value].filter((product) => {
    if (currentCategory.value === "전체") {
      return true;
    } else {
      return product.category.name === currentCategory.value;
    }
  });
};

watch(productCards, () => {
  currentPage.value = 1;
  handleChangePage();
});

onBeforeMount(async () => {
  const category = await getApi({
    url: "/api/categorys",
  });
  const productData = await getApi({
    url: "/api/products",
  });
  const sortData = productData.sort((a, b) => {
    if (a.createDate > b.createDate) {
      return -1;
    } else if (a.createDate < b.createDate) {
      return 1;
    } else {
      return 0;
    }
  });
  productCards.value = productCardsOriginal.value = sortData;

  categoryList.value = ["전체", ...category.map((list) => list.name)];
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
