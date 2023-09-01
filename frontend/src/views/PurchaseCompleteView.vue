<template>
  <content-layout>
    <main-title title="구매내역 상세" />
    <product-banner :product="product" />
    <product-title title="결제내역" />
    <div class="infobox">
      <h4>총 결제금액</h4>
      <p>{{ product.price?.toLocaleString() }}원</p>
    </div>
    <product-title title="배송정보" />
    <ul>
      <li class="infobox">
        <h4>받는사람</h4>
        <p>{{ product.buyerName }}</p>
      </li>
      <li class="infobox">
        <h4>휴대폰번호</h4>
        <p>{{ product.buyerPhoneNumber }}</p>
      </li>
      <li class="infobox">
        <h4>배송주소</h4>
        <p>{{ product.address }}</p>
      </li>
    </ul>
    <v-btn
      variant="outlined"
      height="auto"
      @click="() => $router.push('/mypage/1')"
      >확인</v-btn
    >
  </content-layout>
</template>

<script setup>
import { ref, onBeforeMount } from "vue";
import { useRoute } from "vue-router";
import { getApi } from "@/api/modules/getApi";
import ContentLayout from "../layouts/ContentLayout.vue";
import MainTitle from "@/components/Title/MainTitle.vue";
import ProductTitle from "@/components/Title/ProductTitle.vue";
import ProductBanner from "@/components/Banner/ProductBanner.vue";

const route = useRoute();
const product = ref({});

onBeforeMount(async () => {
  try {
    const response = await getApi({
      url: `/api/buying/completed/${route.params.id}`,
    });
    product.value = response;
    product.value.title = response.productTitle;
    product.value.price = response.productPrice;
  } catch (error) {
    console.error(error);
  }
});
</script>

<style lang="scss" scoped>
.infobox {
  display: flex;
  padding: 10px 0;
  h4 {
    flex-basis: 20%;
    font-weight: 600;
  }
}
.v-btn {
  margin: 60px 0;
  width: 100%;
  padding: 10px 0;
  font-weight: 600;
  font-size: 17px;
  color: rgb(var(--v-theme-subBlue));
}
</style>
