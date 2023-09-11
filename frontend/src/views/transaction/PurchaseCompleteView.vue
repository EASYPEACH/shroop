<template>
  <content-layout>
    <main-title title="구매내역 상세" />
    <product-banner :product="product" />
    <product-title title="결제내역" />
    <div class="payment">
      <div class="payment-text">
        <p>상품 금액:</p>
        <p>안전 결제 수수료:</p>
        <p>총 결제금액:</p>
      </div>
      <div class="payment-price">
        <p>{{ product.price }} 원</p>
        <p>
          {{ fee }}
          원
        </p>
        <p>
          {{ product.price + fee }}
          원
        </p>
      </div>
    </div>
    <product-title title="배송정보" />
    <ul>
      <li class="infobox">
        <h4>받는사람</h4>
        <p>{{ buyer.name }}</p>
      </li>
      <li class="infobox">
        <h4>휴대폰번호</h4>
        <p>{{ buyer.phoneNumber }}</p>
      </li>
      <li class="infobox">
        <h4>배송주소</h4>
        <p>
          {{ `(${buyer.postcode}) ${buyer.location} ${buyer.detailLocation}` }}
        </p>
      </li>
    </ul>
    <v-btn
      variant="outlined"
      height="auto"
      @click="() => $router.push('/mypage/purchaseList')"
      >확인</v-btn
    >
  </content-layout>
</template>

<script setup>
import { ref, onBeforeMount } from "vue";
import { useRoute } from "vue-router";
import { getApi } from "@/api/modules/getApi";

import ContentLayout from "@/layouts/ContentLayout.vue";
import { MainTitle, ProductTitle } from "@/components/Title";
import { ProductBanner } from "@/components/Banner";
import { computed } from "vue";

const route = useRoute();
const product = ref({});
const buyer = ref({
  name: "",
  phoneNumber: "",
  postcode: "",
  location: "",
  detailLocation: "",
});

const fee = computed(() => {
  return Math.round(product.value.price * 0.035);
});

onBeforeMount(async () => {
  try {
    const response = await getApi({
      url: `/api/buying/completed/${route.params.id}`,
    });
    console.log(response);
    product.value = response;
    product.value.id = response.productId;
    product.value.title = response.productTitle;
    product.value.price = response.productPrice;
    buyer.value.name = response.buyerName;
    buyer.value.phoneNumber = response.buyerPhoneNumber;
    buyer.value.postcode = response.buyerPostcode;
    buyer.value.location = response.buyerLocation;
    buyer.value.detailLocation = response.buyerDetailLocation;
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
.payment {
  display: flex;
  height: 100px;
  gap: 90px;
  .payment-text {
    display: flex;
    flex-direction: column;
    align-items: start;
    gap: 20px;
  }
  .payment-price {
    display: flex;
    flex-direction: column;
    align-items: end;
    gap: 20px;

    p:nth-child(3) {
      font-weight: bold;
      color: tomato;
    }
  }
}
</style>
