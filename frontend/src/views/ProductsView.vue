<template>
  <section>
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
          v-for="(productCardData, idx) in productCardsRef.slice(
            startIndex,
            endIndex,
          )"
          :key="idx"
        >
          <product-card-main :id="idx" :ProductCardData="productCardData" />
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
  </section>
</template>

<script setup>
import { ref, watch } from "vue";
import ProductCardMain from "@/components/Card/ProductCardMain.vue";

const categoryList = ref([
  `전체`,
  `가전제품`,
  `전자제품`,
  `옷`,
  `가구`,
  `신발`,
  `생필품`,
]);

const productCards = ref([
  {
    title: `제목1`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `가전제품`,
  },
  {
    title: `제목2`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `가전제품`,
  },
  {
    title: `제목3`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `가전제품`,
  },
  {
    title: `제목4`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `가구`,
  },
  {
    title: `제목5`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `가전제품`,
  },
  {
    title: `제목6`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `가구`,
  },
  {
    title: `제목7`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `가구`,
  },
  {
    title: `제목8`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `신발`,
  },
  {
    title: `제목9`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `전자제품`,
  },
  {
    title: `제목10`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `신발`,
  },
  {
    title: `제목11`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `전자제품`,
  },
  {
    title: `제목12`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `전자제품`,
  },
  {
    title: `제목13`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `전자제품`,
  },
  {
    title: `제목14`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `전자제품`,
  },
  {
    title: `제목15`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `생필품`,
  },
  {
    title: `제목16`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `신발`,
  },
  {
    title: `제목17`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `전자제품`,
  },
  {
    title: `제목18`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `전자제품`,
  },
  {
    title: `제목19`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `전자제품`,
  },
  {
    title: `제목20`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `옷`,
  },
  {
    title: `제목21`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `전자제품`,
  },
  {
    title: `제목22`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `전자제품`,
  },
  {
    title: `제목23`,
    price: 1000,
    content: `내용1`,
    createDate: `2023-08-09`,
    category: `생필품`,
  },
]);

const productCardsRef = ref(productCards.value);

const perPage = ref(9); // 페이지당 상품 수
const productCount = ref(productCardsRef.value.length);
const currentPage = ref(1); // 현재 페이지
const pageCount = ref(Math.ceil(productCount.value / perPage.value)); // 페이지 수
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

watch(productCardsRef, (newProductCardsRef) => {
  productCount.value = newProductCardsRef.length;
  pageCount.value = Math.ceil(productCount.value / perPage.value);
  currentPage.value = 1;
  handleChangePage();
});
</script>

<style lang="scss" scoped>
section {
  width: 1000px;
  margin: 0 auto;
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
    grid-template-columns: repeat(3, 300px);
    gap: 20px;
  }
}

.pagination {
  margin-top: 20px;
}
</style>
