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
      <v-checkbox
        v-model="isSelling"
        @change="handleChangeCheckIsSelling"
        :checked="isSelling"
        label="판매중"
        color="indigo"
        hide-details
      ></v-checkbox>
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
import { useRouter } from "vue-router";
import { useDisplay } from "vuetify";
import { getApi } from "@/api/modules";
import { toggleLikesProduct } from "@/utils";
import ProductCard from "@/components/Card/ProductCard.vue";
import ProductBanner from "@/components/Banner/ProductBanner.vue";
import ContentLayout from "@/layouts/ContentLayout.vue";

const router = useRouter();
const display = useDisplay();

const isLaptop = ref(display.mdAndUp);
const categoryList = ref([]);
const productCardsOriginal = ref([]);
const productCards = ref([]);
const perPage = ref(9); // 페이지당 상품 수
const currentPage = ref(1); // 현재 페이지
const isSelling = ref(true);

// 페이지 수 계산
const pageCount = computed(() => {
  return Math.ceil(productCards.value.length / perPage.value);
});
const startIndex = ref(0); // 상품 시작 인덱스
const endIndex = ref(perPage.value); // 상품 마지막 인덱스
const currentCategory = ref(["전체"]);

// 페이지 전환 핸들러
const handleChangePage = () => {
  startIndex.value = (currentPage.value - 1) * perPage.value;
  endIndex.value = Math.min(
    startIndex.value + perPage.value,
    productCards.value.length,
  );
  window.scrollTo({ top: 0 });
};

// 카테고리별 필터 핸들러
const handleUpdateCategory = () => {
  productCards.value = [...productCardsOriginal.value].filter((product) => {
    if (currentCategory.value === "전체") {
      return true;
    } else {
      return product.category.name === currentCategory.value;
    }
  });
};

// 좋아요, 좋아요 취소 핸들러
const handleClickLike = async (product) => {
  try {
    toggleLikesProduct(product, productCards);
  } catch (err) {
    if (err.response.status === 403) {
      router.push("/login");
    }
  }
};

// 판매중 상품 필터링
const handleChangeCheckIsSelling = () => {
  if (isSelling.value) {
    const fileterSelling = productCardsOriginal.value.filter(
      (card) => card.transactionStatus === null,
    );
    productCards.value = fileterSelling;
  } else {
    productCards.value = productCardsOriginal.value;
  }
};

// 페이지별 보여줄 데이터 조절
watch(productCards, () => {
  currentPage.value = 1;
  startIndex.value = (currentPage.value - 1) * perPage.value;
  endIndex.value = Math.min(
    startIndex.value + perPage.value,
    productCards.value.length,
  );
});

onBeforeMount(async () => {
  const category = await getApi({
    url: "/api/categorys",
  });
  const productData = await getApi({
    url: "/api/products",
  });

  // 상품 등록일 순으로 정렬
  const sortData = productData.sort((a, b) => {
    if (a.createDate > b.createDate) {
      return -1;
    } else if (a.createDate < b.createDate) {
      return 1;
    } else {
      return 0;
    }
  });

  productCardsOriginal.value = sortData;

  // 판매중 체크박스 상태값에 따라, 판매중 상품 거래완료 상품 필터
  if (isSelling.value) {
    const fileterSelling = sortData.filter(
      (card) => card.transactionStatus === null,
    );
    productCards.value = fileterSelling;
  } else {
    productCards.value = sortData;
  }
  categoryList.value = ["전체", ...category.map((list) => list.name)];
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
