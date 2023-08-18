<template>
  <div>
    <section>
      <Title :title="isRegister ? '상품등록' : '상품수정'" />
      <v-form
        v-model="isValid"
        @submit.prevent="handleSubmitRegister"
        enctype="multipart/form-data"
      >
        <product-title title="상품 제목" />
        <custom-text-input
          placeholder-text="제목을 입력해주세요"
          v-model="title"
          :rules="[defaultTextRule.required, defaultTextRule.min]"
        />
        <product-title title="상품 이미지" />
        <image-attach
          ref="productRef"
          @change-files="handleAttachProductImage"
          @delete-image="handleDeleteProductImage"
          required
          attach-name="productImage"
          :images="productImages"
        />
        <p v-if="productImages.length < 2" class="checkRequired">
          사진을 2장 이상 등록해주세요
        </p>
        <info-alert
          title="이미지는 상품 등록 시 정사각형으로 잘려서 등록됩니다"
        />
        <product-title title="상품 정보" />
        <ul class="productInfo">
          <li>
            <h4>카테고리</h4>
            <v-select
              :items="category"
              :rules="[defaultTextRule.required]"
              v-model="categoryValue"
              label="카테고리"
              class="info__form"
            ></v-select>
          </li>
          <li>
            <h4>가격</h4>
            <custom-text-input
              placeholder-text="가격"
              :rules="[priceRule.required, priceRule.min, priceRule.check]"
              v-model="price"
              @input="handleFormatPrice"
              append-icon="mdi-currency-krw"
              class="info__form"
            />
          </li>
          <guide-text
            title="상태 기준 가이드"
            :guide-list="[
              '상: 거의 새상품',
              '중: 생활기스 있음',
              '하: 사용감 많음',
            ]"
          />
          <li>
            <h4>상태</h4>
            <v-select
              :items="itemLevel"
              v-model="itemLevelValue"
              :rules="[defaultTextRule.required]"
              label="상태"
              class="info__form"
            ></v-select>
          </li>
          <guide-text
            title="결함 기준 가이드"
            :guide-list="[
              '가전제품: 일부 기능이 동작하지 않음.',
              '휴대전화: 화면 깨짐 혹은 기능이 동작하지 않음.',
              '의류: 구멍이 있거나 재봉이 잘못됨. 색빠짐이 있음.',
            ]"
          />
          <li>
            <h4 for="isDefected">결함여부</h4>
            <v-radio-group
              :rules="[defaultTextRule.required]"
              inline
              column
              v-model="isDefected"
              class="info__form"
            >
              <v-radio :value="true" label="예"></v-radio>
              <v-radio :value="false" label="아니오"></v-radio>
            </v-radio-group>
          </li>
        </ul>
        <image-attach
          v-if="isDefected"
          :required="isDefected"
          ref="defectedtRef"
          @change-files="handleAttachDefectedImage"
          @delete-image="handleDeleteDefectedImage"
          attach-name="defectedImage"
          :images="defectedImages"
        />
        <p v-if="isDefected && defectedImages.length < 2" class="checkRequired">
          사진을 2장 이상 등록해주세요
        </p>
        <guide-text
          title="상세 조건 가이드"
          :guide-list="[
            '컴퓨터: 메모리 / 배터리 수명 / 옵션',
            '휴대전화: 메모리 / 배터리 수명 / 부품 교체 여부',
            '가구 & 가전제품: 실측 사이즈',
          ]"
        />

        <product-title title="상품 상세조건" />
        <v-textarea
          :rules="[
            productDetailRule.required,
            productDetailRule.min,
            productDetailRule.max,
          ]"
          v-model="productDetailText"
          variant="filled"
          auto-grow
          label="상세조건"
          rows="10"
          row-height="100"
        ></v-textarea>
        <p>{{ productDetailText.length }} / 200</p>
        <div class="agreement">
          <label for="agree">
            <warn-alert
              title="상품 상태의 허위기재로 인한 피해는 판매자가 부담합니다"
            />
          </label>
          <v-checkbox
            :rules="[defaultTextRule.required]"
            label="동의"
            id="agree"
            v-model="agreement"
          ></v-checkbox>
        </div>
        <p v-if="!checkRequired" class="checkRequired">
          필수 사항을 확인 해주세요
        </p>
        <v-btn class="form__button-submit" variant="tonal" type="submit">{{
          isRegister ? "상품 등록" : "상품 수정"
        }}</v-btn>
      </v-form>
    </section>
  </div>
</template>

<script setup>
import { onBeforeMount, ref } from "vue";
import { useRoute } from "vue-router";
import {
  defaultTextRule,
  priceRule,
  productDetailRule,
} from "@/components/Form/data/formRules";
import changeFiles from "@/utils/changeFiles";
import deleteImage from "@/utils/deleteImage";
import CustomTextInput from "@/components/Form/CustomTextInput.vue";
import Title from "@/components/Title/MainTitle.vue";
import ProductTitle from "@/components/Title/ProductTitle.vue";
import ImageAttach from "@/components/ImageAttach.vue";
import InfoAlert from "@/components/Alert/InfoAlert.vue";
import WarnAlert from "@/components/Alert/WarnAlert.vue";
import GuideText from "@/components/GuideText.vue";

const { path } = useRoute();
const isValid = ref(false);
const isRegister = ref(false);
const agreement = ref(false);
const checkRequired = ref(true);
const productImages = ref([]);
const defectedImages = ref([]);
const productRef = ref(null);
const defectedtRef = ref(null);
const isDefected = ref(null);
const category = ref(["가전", "전자제품", "의류"]);
const itemLevel = ref(["상", "중", "하"]);
const categoryValue = ref("");
const itemLevelValue = ref("");
const productDetailText = ref("");
const title = ref("");
const price = ref("");

onBeforeMount(() => {
  if (path.split("/")[1] === "regist") {
    isRegister.value = true;
  } else {
    isRegister.value = false;
  }
});
const handleSubmitRegister = () => {
  if (!isValid.value) {
    checkRequired.value = false;
  } else {
    checkRequired.value = true;
  }
};
const handleAttachProductImage = (files) => {
  changeFiles(files, productImages);
};
const handleAttachDefectedImage = (files) => {
  changeFiles(files, defectedImages);
};
const handleDeleteProductImage = (idx) => {
  deleteImage(idx, productRef, productImages);
};
const handleDeleteDefectedImage = (idx) => {
  deleteImage(idx, defectedtRef, defectedImages);
};
const handleFormatPrice = () => {
  const checkNum = Number(price.value.replaceAll(",", ""));
  if (isNaN(checkNum)) {
    price.value = "0";
  } else {
    price.value = checkNum.toLocaleString("ko-KR");
  }
};
</script>

<style lang="scss" scoped>
.v-form {
  padding: 30px;
  .form__button-submit {
    width: 100%;
    padding: 15px 0;
    margin: 40px 0;
    height: auto;
    font-weight: 600;
    background: rgb(var(--v-theme-mainGray));
    opacity: 1;
    color: #fff;
    font-size: 1.1rem;
  }
}
.productInfo {
  li {
    display: flex;
    width: 100%;
    align-items: center;
    h4 {
      margin-top: -23px;
      flex-basis: 100px;
    }
    .info__form {
      flex: 1;
    }
    .v-radio + .v-radio {
      padding: 0 20px;
    }
  }
}
.agreement {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.checkRequired {
  color: red;
  text-align: left;
  padding-bottom: 20px;
}
</style>
