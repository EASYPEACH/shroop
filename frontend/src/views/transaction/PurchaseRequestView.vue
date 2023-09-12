<template>
  <section>
    <content-layout>
      <product-banner :product="product" isPurchase />
      <v-form @submit.prevent="handleRequestPurchase" v-model="isValid">
        <product-title title="이름" isRequired />
        <custom-text-input
          :rules="[
            defaultTextRule.required,
            (value) => defaultTextRule.customMinLength(value, 2),
          ]"
          placeholderText="이름을 입력해주세요."
          v-model="buyerName"
        />
        <product-title title="휴대폰 번호" isRequired />
        <custom-text-input
          :rules="[
            phoneNumberRule.required,
            phoneNumberRule.check,
            (value) => defaultTextRule.customMinLength(value, 11),
          ]"
          placeholderText="휴대폰 번호를 입력해주세요.(하이픈 빼고 입력)"
          v-model="phoneNumber"
        />
        <product-title title="배송주소" isRequired />
        <div class="postCode__box">
          <custom-text-input
            class="postCode__box-input"
            :rules="[
              defaultTextRule.required,
              (value) => defaultTextRule.customMinLength(value, 5),
            ]"
            placeholderText="우편번호"
            hide-details
            v-model="postCode"
            :disabled="true"
          />
          <v-btn variant="plain" @click="openPostCode">검색</v-btn>
        </div>

        <custom-text-input
          :rules="[
            defaultTextRule.required,
            (value) => defaultTextRule.customMinLength(value, 8),
          ]"
          placeholderText="주소"
          v-model="location"
          :disabled="true"
        />
        <custom-text-input
          :rules="[
            defaultTextRule.required,
            (value) => defaultTextRule.customMinLength(value, 2),
          ]"
          placeholderText="상세주소"
          v-model="detailLocation"
        />
        <div class="profile__point">
          <div class="profile__point-count">
            사용 가능한 방울 : {{ profile.point.toLocaleString() }}
            <v-icon icon="mdi-water" class="drop" />
          </div>
          <mini-button
            variant="outlined"
            text="충전"
            @click="showChargePointModal = true"
          />
        </div>

        <ul class="payment">
          <li>
            <h3>상품 금액:</h3>
            <p>{{ product.price.toLocaleString() }} 원</p>
          </li>
          <li>
            <h3>안전 결제 수수료:</h3>
            <p>
              {{ fee.toLocaleString() }}
              원
            </p>
          </li>
          <v-divider />
          <li>
            <h3>총 결제금액:</h3>
            <p>
              {{ (product.price + fee).toLocaleString() }}
              원
            </p>
          </li>
        </ul>
        <v-divider />
        <div class="caution">
          <h2>주의 사항</h2>
          <caution-block
            v-for="info in cautionInfoList"
            :key="info.id"
            :caution-info="info"
            v-model="info.value"
          />

          <div class="caution__block-all-agree">
            <div class="caution__block-all-agree__text">전체 동의</div>
            <div>
              <v-checkbox
                v-model="allCheckboxesChecked"
                @change="toggleAllCheckboxes"
              />
            </div>
          </div>
        </div>
        <submit-button :disabled="!isValid" text="결제하기" />
      </v-form>
      <charge-point-modal
        v-model="showChargePointModal"
        @handle-cancel="showChargePointModal = false"
        @handle-return-point-result="(result) => (profile.point = result)"
        label="충전할 방울"
        isCharged
      />
    </content-layout>
  </section>
</template>

<script setup>
import { ref, watch, onBeforeMount } from "vue";
import {
  defaultTextRule,
  phoneNumberRule,
} from "@/components/Form/data/formRules";
import { getApi, postApi } from "@/api/modules";
import { useRoute, useRouter } from "vue-router";

import ContentLayout from "@/layouts/ContentLayout.vue";
import { ProductTitle } from "@/components/Title";
import { CustomTextInput } from "@/components/Form";
import { SubmitButton } from "@/components/Button";
import { MiniButton } from "@/components/Button";
import { ChargePointModal } from "@/components/Modal";
import CautionBlock from "@/components/CautionBlock.vue";
import { ProductBanner } from "@/components/Banner";
import { computed } from "vue";

const router = useRouter();
const route = useRoute();
const isValid = ref(false);
const buyerName = ref("");
const phoneNumber = ref("");
const postCode = ref("");
const location = ref("");
const detailLocation = ref("");
const product = ref({
  title: "",
  price: 0,
  thumb: "",
});
const profile = ref({
  point: 0,
});
const cautionInfoList = ref([
  {
    id: 0,
    p1: "구매하는 상품 종류를 확인하였습니다.",
    p2: "주문자의 착오로 원하지 않는 상품이 전달된 경우 환불이 불가합니다.",
    value: false,
  },
  {
    id: 1,
    p1: "상품의 상태를 확인하였습니다.",
    p2: "생활 기스가 있는 상품입니다.",
    value: false,
  },
  {
    id: 2,
    p1: "부주의로 인한 파손일 경우 보상이 불가능합니다.",
    value: false,
  },
  {
    id: 3,
    p1: "슈룹의 최신 이용정책을 모두 확인하였으며, 구매에 동의합니다.",
    p2: "사용자로서 숙지해야할 패널티, 이용 정책을 모두 확인하였습니다.",
    value: false,
  },
]);

const fee = computed(() => {
  return Math.round(product.value.price * 0.035);
});

const showChargePointModal = ref(false);
const allCheckboxesChecked = ref(false);

const toggleAllCheckboxes = () => {
  if (allCheckboxesChecked.value) {
    cautionInfoList.value = cautionInfoList.value.map((list) => {
      list.value = true;
      return list;
    });
  } else {
    cautionInfoList.value = cautionInfoList.value.map((list) => {
      list.value = false;
      return list;
    });
  }
};

onBeforeMount(async () => {
  try {
    const response = await getApi({
      url: `/api/buying/${route.params.id}`,
    });
    product.value = response;
    profile.value = response;
  } catch (error) {
    console.error(error);
  }
});

const openPostCode = () => {
  new window.daum.Postcode({
    oncomplete: (data) => {
      postCode.value = data.zonecode;
      location.value = data.roadAddress;
    },
  }).open();
};

watch(cautionInfoList.value, (caution) => {
  const filterTrue = caution.filter((list) => list.value);
  if (filterTrue.length === 4) {
    allCheckboxesChecked.value = true;
  } else {
    allCheckboxesChecked.value = false;
  }
});

const handleRequestPurchase = async () => {
  try {
    await postApi({
      url: `/api/buying/${route.params.id}`,
      data: {
        buyerName: buyerName.value,
        buyerPhoneNumber: phoneNumber.value,
        buyerPostcode: postCode.value,
        buyerLocation: location.value,
        buyerDetailLocation: detailLocation.value,
      },
    });
    router.push(`/PurchaseComplete/${route.params.id}`);
  } catch (error) {
    alert(error.response.data.message);
  }
};
</script>

<style lang="scss" scoped>
.product-info {
  display: flex;
  flex-direction: column;
  padding: 0.5rem;
  h4 {
    font-size: 1.2rem;
    font-weight: bold;
  }
  p {
    font-size: 16px;
    opacity: 0.7;
  }
}
.v-form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 50px;
}
.payment {
  width: 400px;
  place-self: flex-end;
  margin: 50px 0;
  display: flex;
  flex-direction: column;
  gap: 20px;

  li {
    display: flex;
    justify-content: space-between;
    gap: 10px;
    h3 {
      font-weight: 600;
    }
  }
  li:last-child {
    p {
      color: rgb(var(--v-theme-heartRed));
    }
    font-weight: 600;
  }

  @media (max-width: 750px) {
    width: 100%;
    place-self: center;

    .payment-text {
      padding: 0px;
    }
    .payment-price {
      padding: 0px;
    }
  }
}
.caution {
  display: flex;
  flex-direction: column;
  h2 {
    font-size: 25px;
    font-weight: bold;
    margin-bottom: 30px;
  }
}

.caution__block-all-agree {
  padding-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.caution__block-all-agree__text {
  padding-top: 15px;
}

.checkbox {
  display: flex;
  align-items: center;
}
.profile__point {
  display: flex;
  align-items: center;
  gap: 20px;

  .drop {
    color: rgb(var(--v-theme-subGreen));
  }
}
.postCode__box {
  display: flex;
  width: 60%;
  align-items: center;
  margin-bottom: 20px;
  .postCode__box-input {
    flex-basis: 50%;
  }
  .v-btn {
    background-color: #000;
    opacity: 1;
    color: #fff;
    height: 44px;
    border-radius: 0 4px 0 0;
  }
}
</style>
