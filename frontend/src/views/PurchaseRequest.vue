<template>
  <section>
    <content-layout>
      <v-banner color="pink-darken-1" lines="two">
        <v-img
          class="banner-img"
          src="https://cdn.vuetifyjs.com/images/john.jpg"
          width="50px"
          height="50px"
        ></v-img>
        <div class="banner-content">
          <div class="product-info">
            <div class="product-name">{{ product }}</div>
            <div class="product-price">{{ price }}원</div>
          </div>
        </div>
      </v-banner>
      <v-form
        enctype="multipart/form-data"
        @submit.prevent="handleSubmitReport"
      >
        <product-title title="배송주소" isRequired />
        <custom-text-input
          :rules="[defaultTextRule.required]"
          placeholderText="배송주소를 입력해주세요."
          v-model="address"
        />
        <product-title title="휴대폰 번호" isRequired />
        <custom-text-input
          :rules="[defaultTextRule.required]"
          placeholderText="휴대폰 번호를 입력해주세요."
          v-model="phoneNumber"
        />

        <div class="profile__point">
          <div class="profile__point-count">
            사용 가능한 방울 : {{ profile.point }}
            <v-icon icon="mdi-water" class="drop" />
          </div>
          <mini-button
            variant="outlined"
            text="충전"
            @click="showChargePointModal = true"
          />
        </div>

        <div class="payment-price">결제 금액:{{ price }} 원</div>

        <div class="caution">
          <div class="caution__head">주의 사항</div>
          <div class="caution__block">
            <div class="caution__block__text">
              <div class="caution__block__text-bold">
                구매하는 상품 종류를 확인하였습니다.
              </div>
              <div class="caution__block__text-gray">
                주문자의 착오로 원하지 않는 상품이 전달된 경우 환불이
                불가합니다.
              </div>
            </div>
            <div>
              <v-checkbox v-model="checkboxStates[0]" />
            </div>
          </div>
          <div class="caution__block">
            <div class="caution__block__text">
              <div class="caution__block__text-bold">
                상품의 상태를 확인하였습니다.
              </div>
              <div class="caution__block__text-gray">
                생활 기스가 있는 상품입니다.
              </div>
            </div>
            <div>
              <v-checkbox v-model="checkboxStates[1]" />
            </div>
          </div>
          <div class="caution__block">
            <div class="caution__block__text">
              <div class="caution__block__text-bold">
                부주의로 인한 파손일 경우 보상이 불가능합니다.
              </div>
            </div>
            <div>
              <v-checkbox v-model="checkboxStates[2]" />
            </div>
          </div>
          <div class="caution__block">
            <div class="caution__block__text">
              <div class="caution__block__text-bold">
                슈룹의 최신 이용정책을 모두 확인하였으며, 구매에 동의합니다.
              </div>
              <div class="caution__block__text-gray">
                사용자로서 숙지해야할 패널티, 이용 정책을 모두 확인하였습니다.
              </div>
            </div>
            <div>
              <v-checkbox v-model="checkboxStates[3]" />
            </div>
          </div>
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
        <submit-button text="결제하기" />
      </v-form>
      <charge-point-modal
        v-model="showChargePointModal"
        @handle-cancle="showChargePointModal = false"
      />
    </content-layout>

    <v-dialog v-model="showWarningModal" max-width="500">
      <v-card>
        <v-card-title class="headline">경고</v-card-title>
        <v-card-text>
          모든 주의 사항에 동의해야 결제할 수 있습니다.
        </v-card-text>
        <v-card-actions>
          <v-btn color="primary" text @click="showWarningModal = false"
            >확인</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>
  </section>
</template>

<script setup>
import { ref, watch } from "vue";
import { defaultTextRule, selectRule } from "@/components/Form/data/formRules";
import ContentLayout from "@/layouts/ContentLayout.vue";
import ProductTitle from "@/components/Title/ProductTitle.vue";
import CustomTextInput from "@/components/Form/CustomTextInput.vue";
import SubmitButton from "@/components/Button/SubmitButton.vue";
import MiniButton from "@/components/Button/MiniButton.vue";
import ChargePointModal from "@/components/Modal/ChargePointModal.vue";

const address = ref("");
const phoneNumber = ref("");
const price = ref("700,000");
const product = ref("아이패드 프로 10.5");
const imageData = ref({});
const profile = ref({
  point: 20000,
});
const showChargePointModal = ref(false);

const checkboxStates = ref([false, false, false, false]);
const allCheckboxesChecked = ref(false);

const toggleAllCheckboxes = () => {
  const newState = allCheckboxesChecked.value;
  for (let i = 0; i < checkboxStates.value.length; i++) {
    checkboxStates.value[i] = newState;
  }
};

watch(checkboxStates.value, (newState) => {
  console.log(newState);
  const filterTrue = newState.filter((value) => value);
  if (filterTrue.length === 4) {
    allCheckboxesChecked.value = true;
  } else {
    allCheckboxesChecked.value = false;
  }
});

const showWarningModal = ref(false);

const handleSubmitReport = () => {
  const allCheckboxesAreChecked = checkboxStates.value.every((state) => state);

  if (!allCheckboxesAreChecked) {
    showWarningModal.value = true;
    return;
  }
};
</script>

<style lang="scss" scoped>
.banner-content {
  display: flex;
  align-items: center;
  width: 100%;
  padding-right: 20px;
}

.product-info {
  display: flex;
  flex-direction: column;
  padding: 0.5rem;
}

.product-name {
  font-size: 1.2rem;
  font-weight: bold;
}

.product-price {
  font-size: 1rem;
  opacity: 0.7;
}
.payment-price {
  display: flex;
  justify-content: flex-end;
  padding: 50px;
  font-weight: bold;
}
.caution {
  display: flex;
  flex-direction: column;
}
.caution__head {
  font-size: 25px;
  font-weight: bold;
  margin-bottom: 30px;
}
.caution__block {
  display: flex;
  justify-content: space-between;
}
.caution__block__text {
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.caution__block__text-bold {
  font-weight: bold;
}
.caution__block__text-gray {
  background-color: lightgray;
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

  .drop {
    color: rgb(var(--v-theme-subBlue));
  }
}
</style>
