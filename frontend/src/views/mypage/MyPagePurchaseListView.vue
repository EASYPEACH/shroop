<template>
  <mypage-layout>
    <ul>
      <info-alert
        v-if="purchaseList.length === 0"
        title="구매내역이 없습니다"
      />
      <li v-for="product in purchaseList" :key="product.id">
        <mypage-product-banner
          :product="product"
          isStatus
          @handle-get-purchaseHistory="handleGetPurchaseHistory"
        />
      </li>
    </ul>
    <v-pagination
      v-model="purchasePage"
      :length="purchasePageCount"
      :total-visible="isTablet ? 3 : 5"
      @click="handleGetPurchaseHistory"
    >
    </v-pagination>
  </mypage-layout>
</template>

<script setup>
import { ref, onBeforeMount } from "vue";
import { useDisplay } from "vuetify";
import { getApi } from "@/api/modules";
import { InfoAlert } from "@/components/Alert";
import { MypageProductBanner } from "@/components/Banner";
import MypageLayout from "@/layouts/MypageLayout.vue";

const display = useDisplay();
const isTablet = ref(display.smAndDown);

const purchaseList = ref([]);
const purchasePageCount = ref(0);
const purchasePage = ref(1);

onBeforeMount(async () => {
  await handleGetPurchaseHistory();
});

// 구매내역
const handleGetPurchaseHistory = async () => {
  try {
    const purchaseData = await getApi({
      url: `/api/history/buying?page=${
        purchasePage.value - 1
      }&size=5&sort=createDate,desc`,
    });
    if (purchaseData !== null) {
      purchaseList.value = purchaseData.historyResponseList;
      purchasePageCount.value = purchaseData.pageCount;
    }
  } catch (error) {
    console.error(error);
  }
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
