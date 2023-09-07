<template>
  <content-layout>
    <ul>
      <info-alert
        v-if="purchaseList.length === 0"
        title="구매내역이 없습니다"
      />
      <li
        v-for="product in purchaseList.slice(startIndex, endIndex)"
        :key="product.id"
      >
        <mypage-product-banner :product="product" isStatus />
      </li>
    </ul>
    <v-pagination
      v-model="currentPage"
      :length="pageCount"
      :total-visible="isTablet ? 3 : 5"
      @click="handleChangePurchasePage"
    >
    </v-pagination>
  </content-layout>
</template>

<script setup>
import { ref, onBeforeMount, computed, watch } from "vue";
import { useDisplay } from "vuetify";
import { getApi } from "@/api/modules";
import { InfoAlert } from "@/components/Alert";
import { MypageProductBanner } from "@/components/Banner";
import ContentLayout from "@/layouts/ContentLayout.vue";

const display = useDisplay();
const isTablet = ref(display.smAndDown);

const perPage = ref(5); // 페이지당 상품 수
const currentPage = ref(1); // 현재 페이지
const startIndex = ref(0); // 상품 시작 인덱스
const endIndex = ref(perPage.value); // 상품 마지막 인덱스

// 구매내역
const purchaseList = ref([]);

onBeforeMount(async () => {
  handleGetPurchaseHistory();
});

// 상품 수 계산
const productCount = computed(() => {
  return purchaseList.value.length;
});

// 페이지 수 계산
const pageCount = computed(() => {
  return Math.ceil(productCount.value / perPage.value);
});

watch(purchaseList, () => {
  currentPage.value = 1;
  handleChangePurchasePage();
});

// 구매내역
const handleGetPurchaseHistory = async () => {
  try {
    const purchaseData = await getApi({ url: "/api/buying/history" });
    if (purchaseData !== null) {
      purchaseList.value = purchaseData;
    }
  } catch (error) {
    console.error(error);
  }
};

// 페이징 변경 핸들러
const handleChangePurchasePage = () => {
  startIndex.value = (currentPage.value - 1) * perPage.value;
  endIndex.value = Math.min(
    startIndex.value + perPage.value,
    productCount.value,
  );
  window.scrollTo({ top: 0 });
};
</script>

<style lang="scss" scoped>
.v-pagination {
  margin: 0 auto;
}

.mypage__personal {
  margin: 10px 50px;
  max-width: 500px;
  .delete {
    margin-top: 80px;
  }
}
</style>
