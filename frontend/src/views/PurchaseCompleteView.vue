<template>
  <content-layout>
    <main-title title="구매내역 상세" />
    <product-banner :product="product" />
    <product-title title="결제내역" />
    <div class="infobox">
      <h4>총 결제금액</h4>
      <p>{{ product.price.toLocaleString() }}</p>
    </div>
    <product-title title="배송정보" />
    <ul>
      <li class="infobox">
        <h4>받는사람</h4>
        <p>{{ buyeInfo.name }}</p>
      </li>
      <li class="infobox">
        <h4>휴대폰번호</h4>
        <p>{{ buyeInfo.phoneNumber }}</p>
      </li>
      <li class="infobox">
        <h4>배송주소</h4>
        <p>{{ buyeInfo.address }}</p>
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
import { ref } from "vue";
import ContentLayout from "../layouts/ContentLayout.vue";
import MainTitle from "@/components/Title/MainTitle.vue";
import ProductTitle from "@/components/Title/ProductTitle.vue";

const product = ref({
  title: "아이패드 프로 10.5",
  price: 700000,
  thumb: "https://cdn.vuetifyjs.com/images/john.jpg",
});
const buyeInfo = ref({
  name: "김뿅뿅",
  address: "경기도 고양시 덕양구 신원3로 20",
  phoneNumber: "01012341234",
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
