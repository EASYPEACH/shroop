<template>
  <content-layout>
    <main-title title="배송등록" />
    <product-title title="상품ID" />
    <p>Product #{{ productId }}</p>
    <product-title title="판매상품" />
    <p>{{ productName }}</p>
    <product-title title="받는사람" />
    <ul class="buyer-info">
      <li>
        <h5>이름</h5>
        <p>{{ buyerInfo.name }}</p>
      </li>
      <li>
        <h5>배송지</h5>
        <p>
          {{ buyerInfo.address }}
        </p>
      </li>
      <li>
        <h5>전화번호</h5>
        <p>{{ buyerInfo.phoneNumber }}</p>
      </li>
    </ul>
    <v-form @submit.prevent="handleSubmitDelivery">
      <product-title title="운송장번호 등록" />
      <ul class="deliveryInfo">
        <li>
          <h4>운송장번호</h4>
          <custom-text-input
            placeholder-text="운송장번호"
            type="number"
            v-model="deliveryNumber"
            :rules="[defaultTextRule.required]"
            class="deliveryInfo__form"
          />
        </li>
        <li>
          <h4>택배사</h4>
          <custom-text-input
            placeholder-text="택배사"
            v-model="deliveryCompany"
            :rules="[defaultTextRule.required, defaultTextRule.min]"
            class="deliveryInfo__form"
          />
        </li>
      </ul>
      <submit-button text="배송 등록" />
    </v-form>
  </content-layout>
</template>

<script setup>
import { ref } from "vue";
import { defaultTextRule, selectRule } from "@/components/Form/data/formRules";
import { useRoute } from "vue-router";
import ContentLayout from "@/layouts/ContentLayout.vue";
import MainTitle from "@/components/Title/MainTitle.vue";
import ProductTitle from "@/components/Title/ProductTitle.vue";
import CustomTextInput from "@/components/Form/CustomTextInput.vue";
import SubmitButton from "@/components/Button/SubmitButton.vue";

const router = useRoute();
const productId = ref(router.params.id);
const productName = ref("아이패드 프로 10.5");
const deliveryNumber = ref("");
const deliveryCompany = ref("");
const buyerInfo = ref({
  name: "김뿅뿅",
  address: "경기도 고양시 덕양구 신원3로 20",
  phoneNumber: "01012341234",
});

const handleSubmitDelivery = () => {};
</script>

<style lang="scss" scoped>
.close-btn {
  font-size: 20px;
  place-self: flex-end;
  margin-right: -51px;
}

.buyer-info {
  width: 370px;
  background: rgb(var(--v-theme-surface));
  border-radius: 20px;
  li {
    display: flex;
    h5 {
      flex-basis: 100px;
      font-weight: 600;
    }
    p {
      flex: 1;
    }
    & + li {
      margin-top: 20px;
    }
  }
}

.deliveryInfo {
  li {
    display: flex;
    gap: 20px;

    h4 {
      flex-basis: 20%;
    }
    .deliveryInfo__form {
      flex: 1;
    }
  }
}
</style>
