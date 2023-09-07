<template>
  <content-layout>
    <div class="filter">
      <div class="filter__catetory">
        <v-select
          v-model="currentCategory"
          label="카테고리"
          :items="categoryList"
          item-title="name"
          item-value="id"
          variant="solo"
        ></v-select>
      </div>
      <v-checkbox
        v-model="isSelling"
        :checked="isSelling"
        label="판매중"
        color="indigo"
        hide-details
      ></v-checkbox>
    </div>
    <div class="products">
      <info-alert v-if="productCards.length === 0" title="상품이 없습니다" />
      <ul v-else class="products__list">
        <li
          v-for="productCardData in productCards"
          :key="productCardData.id"
          data-aos="fade-up"
        >
          <product-card
            v-if="isLaptop"
            :productCardData="productCardData"
            @handle-click-like="() => handleClickLike(productCardData)"
          />
          <product-banner
            v-else
            :product="productCardData"
            @handle-click-like="() => handleClickLike(productCardData)"
            isHeart
          />
        </li>
      </ul>
    </div>
    <div class="pagination" v-if="productCards.length !== 0">
      <v-pagination v-model="currentPage" :length="pageCount"></v-pagination>
    </div>
  </content-layout>
</template>
<script setup>
import { ref, watch, onBeforeMount } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useDisplay } from "vuetify";
import { getApi } from "@/api/modules";
import { toggleLikesProduct } from "@/utils";
import { useSearchProduct } from "@/store/modules";
import ProductCard from "@/components/ProductCard.vue";
import { ProductBanner } from "@/components/Banner";
import ContentLayout from "@/layouts/ContentLayout.vue";
import { InfoAlert } from "@/components/Alert";

const router = useRouter();
const route = useRoute();
const display = useDisplay();
const searchProductStore = useSearchProduct();

const isLaptop = ref(display.mdAndUp);
const categoryList = ref([]);
const productCards = ref([]);
const currentPage = ref(1); // 현재 페이지
const isSelling = ref(true);
const pageCount = ref();
const currentCategory = ref(0);

const handelChangeSearchData = () => {
  router.push({
    name: "Products",
    query: {
      title: searchProductStore.searchTitle,
      categoryId: currentCategory.value,
      isSelling: isSelling.value,
      page: currentPage.value,
    },
  });
};

const handelGetProductData = async () => {
  try {
    const response = await getApi({
      url: `/api/products/search?size=9&page=${
        currentPage.value - 1
      }&hasNotTransaction=${isSelling.value}&title=${
        searchProductStore.searchTitle === undefined
          ? ""
          : searchProductStore.searchTitle
      }&categoryId=${currentCategory.value}`,
    });
    productCards.value = response.productList;
    pageCount.value = response.pageCount;
  } catch (error) {
    console.error(error);
  }
};

watch([isSelling, currentCategory, currentPage], () => {
  handelChangeSearchData();
});

watch([isSelling, currentCategory], async () => {
  currentPage.value = 1;
});

// 좋아요, 좋아요 취소 핸들러
const handleClickLike = async (product) => {
  try {
    await toggleLikesProduct(product, productCards);
  } catch (err) {
    if (err.response.status === 403) {
      router.push("/login");
    }
  }
};

onBeforeMount(async () => {
  try {
    const category = await getApi({
      url: "/api/categorys",
    });
    categoryList.value = [{ id: 0, name: "전체" }, ...category];

    await handelGetProductData();
  } catch (error) {
    console.error(error);
  }
});

router.beforeEach(() => {
  if (route.path === "/products") {
    handelGetProductData();
  }
});
</script>

<style lang="scss" scoped>
section {
  margin-top: 50px;
}

.filter {
  display: flex;
  margin-bottom: 30px;
  gap: 20px;

  .filter__catetory {
    width: 150px;
  }
}

.products {
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
