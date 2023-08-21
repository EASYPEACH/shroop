<template>
  <content-layout>
    <main-title title="반품신청" />
    <v-form @submit.prevent="handleSubmitReturnRequest">
      <product-title title="상품ID" />
      <p>Product #{{ productId }}</p>
      <product-title title="반품신청 대상" />
      <p>{{ productName }}</p>
      <product-title title="반품 관련 사진 첨부" />
      <image-attach
        ref="requestImageRef"
        @change-files="handleAttachProductImage"
        @delete-image="handleDeleteProductImage"
        required
        attach-name="returnRequestImage"
        :images="returnRequestThumb"
      />
      <product-title title="반품 사유" />
      <custom-text-area
        v-model="returnReasonText"
        placeholder="반품사유를 최대한 상세하게 기재 부탁드리겠습니다"
      />
      <submit-button text="반품신청" />
    </v-form>
  </content-layout>
</template>

<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";
import { changeFiles, deleteImage } from "@/utils";
import SubmitButton from "@/components/Button/SubmitButton.vue";
import MainTitle from "@/components/Title/MainTitle.vue";
import ContentLayout from "@/layouts/ContentLayout.vue";
import ProductTitle from "@/components/Title/ProductTitle.vue";
import ImageAttach from "@/components/ImageAttach.vue";
import CustomTextArea from "@/components/Form/CustomTextArea.vue";

const route = useRoute();
const productId = ref(route.params.id);
const productName = ref("아이패드 프로 10.5");
const returnRequestImageData = ref({});
const returnRequestThumb = ref([]);
const requestImageRef = ref(null);
const returnReasonText = ref("");

const handleAttachProductImage = (files) => {
  changeFiles(
    files,
    requestImageRef,
    returnRequestThumb,
    returnRequestImageData,
  );
};
const handleDeleteProductImage = (idx) => {
  deleteImage(idx, requestImageRef, returnRequestThumb, returnRequestImageData);
};
const handleSubmitReturnRequest = () => {
  console.log(returnRequestImageData.value);
  console.log(returnReasonText.value);
};
</script>

<style lang="scss" scoped></style>
