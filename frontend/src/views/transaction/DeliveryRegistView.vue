<template>
  <content-layout>
    <main-title title="배송등록" />
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
    <v-form v-model="isValid" @submit.prevent="handleSubmitDelivery">
      <product-title title="운송장번호 등록" />
      <ul class="deliveryInfo">
        <li>
          <h4>운송장번호</h4>
          <custom-text-input
            placeholder-text="운송장번호"
            v-model="deliveryNumber"
            :rules="[defaultTextRule.required, trackingNumberRule.check]"
            class="deliveryInfo__form"
          />
        </li>
        <li>
          <h4>택배사</h4>
          <custom-text-input
            placeholder-text="택배사"
            v-model="deliveryCompany"
            :rules="[defaultTextRule.required]"
            class="deliveryInfo__form"
          />
        </li>
      </ul>
      <submit-button :disabled="!isValid" text="배송 등록" />
    </v-form>
    <plain-modal
      v-for="dialog in dialogList"
      :key="dialog.id"
      :modalText="dialog.text"
      v-model="dialog.isShow"
      @handle-cancle="() => (dialog.isShow = !dialog.isShow)"
      @handle-confirm="dialog.callback"
    />
  </content-layout>
</template>

<script setup>
import { ref, onBeforeMount } from "vue";
import { useRouter, useRoute } from "vue-router";
import {
  defaultTextRule,
  trackingNumberRule,
} from "@/components/Form/data/formRules";
import { getApi, postApi } from "@/api/modules";

import ContentLayout from "@/layouts/ContentLayout.vue";
import { MainTitle, ProductTitle } from "@/components/Title";
import { CustomTextInput } from "@/components/Form";
import { SubmitButton } from "@/components/Button";
import { PlainModal } from "@/components/Modal";

const router = useRouter();
const route = useRoute();
const isValid = ref(false);
const productName = ref("");
const deliveryNumber = ref("");
const deliveryCompany = ref("");
const buyerInfo = ref({
  name: "",
  address: "",
  phoneNumber: "",
});

const dialogList = ref([
  {
    id: 0,
    text: "배송 등록이 완료되었습니다.",
    isShow: false,
    callback: () => {
      router.push("/mypage/sellList");
    },
  },
  {
    id: 1,
    text: "운송장번호가 이미 존재합니다.",
    isShow: false,
    callback: () => {
      dialogList.value[1].isShow = false;
    },
  },
  {
    id: 2,
    text: "입력값을 확인하세요.",
    isShow: false,
    callback: () => {
      dialogList.value[2].isShow = false;
    },
  },
]);

const handleSubmitDelivery = async () => {
  try {
    await postApi({
      url: `/api/delivery/${route.params.id}`,
      data: {
        trackingNumber: deliveryNumber.value,
        parcel: deliveryCompany.value,
      },
    });
    dialogList.value[0].isShow = true;
  } catch (error) {
    const code = error.response.status;
    const msg = error.response.data.message;
    if (code === 409) {
      dialogList.value[2].isShow = true;
    } else {
      alert(msg);
      console.error(error);
    }
  }
};
const handelGetTransactionData = async () => {
  try {
    const response = await getApi({
      url: `/api/buying/completed/${route.params.id}`,
    });
    productName.value = response.productTitle;
    buyerInfo.value.name = response.buyerName;
    buyerInfo.value.address = response.buyerLocation;
    buyerInfo.value.phoneNumber = response.buyerPhoneNumber;
  } catch (error) {
    console.error(error);
  }
};

onBeforeMount(async () => await handelGetTransactionData());
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
