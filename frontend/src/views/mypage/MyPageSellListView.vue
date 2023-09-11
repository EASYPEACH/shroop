<template>
  <content-layout>
    <ul>
      <info-alert v-if="sellList.length === 0" title="판매내역이 없습니다" />
      <li v-for="product in sellList" :key="product.id">
        <mypage-product-banner :product="product" isStatus isSeller />
      </li>
    </ul>
    <v-pagination
      v-model="sellingPage"
      :length="sellPageCount"
      :total-visible="isTablet ? 3 : 5"
      @click="handleGetSellHistory"
    >
    </v-pagination>
  </content-layout>
</template>

<script setup>
import { ref, onBeforeMount } from "vue";
import { useDisplay } from "vuetify";
import { getApi } from "@/api/modules";
import ContentLayout from "@/layouts/ContentLayout.vue";
import { InfoAlert } from "@/components/Alert";
import { MypageProductBanner } from "@/components/Banner";

const display = useDisplay();
const isTablet = ref(display.smAndDown);

// 판매내역
const sellingPage = ref(1);
const sellList = ref([]);
const sellPageCount = ref(0);

onBeforeMount(async () => {
  await handleGetSellHistory();
});

// 판매내역
const handleGetSellHistory = async () => {
  try {
    const sellData = await getApi({
      url: `/api/history/selling?page=${
        sellingPage.value - 1
      }&size=5&sort=transactionCreateDate,desc`,
    });
    if (sellData !== null) {
      sellList.value = sellData.historyResponseList;
      sellPageCount.value = sellData.pageCount;
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
</style>
