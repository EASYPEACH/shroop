<template>
  <content-layout>
    <main-title title="반품신청" />
    <v-form
      v-model="isValid"
      enctype="multipart/form-data"
      @submit.prevent="handleSubmitReturnRequest"
    >
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
      <p>사진을 2장 이상 첨부해주세요.</p>
      <product-title title="반품 사유" />
      <custom-text-area
        v-model="returnReasonText"
        placeholder="반품사유를 최대한 상세하게 기재 부탁드리겠습니다"
      />
      <submit-button :disabled="!isValid" text="반품신청" />
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
import { ref, onBeforeMount, watchEffect } from "vue";
import { useRoute, useRouter } from "vue-router";
import { changeFiles, deleteImage, multipartFormData } from "@/utils";
import { getApi, multipartPostApi } from "@/api/modules";
import SubmitButton from "@/components/Button/SubmitButton.vue";
import MainTitle from "@/components/Title/MainTitle.vue";
import ContentLayout from "@/layouts/ContentLayout.vue";
import ProductTitle from "@/components/Title/ProductTitle.vue";
import ImageAttach from "@/components/ImageAttach.vue";
import CustomTextArea from "@/components/Form/CustomTextArea.vue";
import PlainModal from "@/components/Modal/PlainModal.vue";

const router = useRouter();
const route = useRoute();
const isValid = ref(false);
const productId = ref(route.params.id);
const productName = ref("");
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
const handleSubmitReport = async () => {
  const formData = multipartFormData(
    "productReturnRequest",
    {
      content: returnReasonText.value,
    },
    requestImageRef.value,
    "productReturnImgList",
  );

  try {
    await multipartPostApi({
      url: `/api/return/${productId.value}`,
      data: formData,
    });

    dialogList.value[0].isShow = true;
  } catch (error) {
    console.log(error);
    const code = error.response.status;
    if (code === 400) {
      dialogList.value.forEach((dialog) => {
        dialog.isShow = false;
      });
      dialogList.value[1].isShow = true;
    } else {
      dialogList.value.forEach((dialog) => {
        dialog.isShow = false;
      });
      dialogList.value[2].isShow = true;
    }
  }
};
const handleSubmitReturnRequest = () => {
  console.log(returnRequestImageData.value);
  console.log(returnReasonText.value);
  handleSubmitReport();
};
const handleGetProduct = async () => {
  try {
    const response = await getApi({
      url: `/api/products/${productId.value}`,
    });

    productName.value = response.title;
  } catch (error) {
    console.log(error);
  }
};
const completeSubmitReturn = () => {
  router.push("/mypage/1");
};
const handleErrorInput = () => {
  dialogList.value[1].isShow = false;
};
const handleErrorEtc = () => {
  dialogList.value[2].isShow = false;
};

onBeforeMount(async () => await handleGetProduct());

watchEffect(() => {
  if (
    returnReasonText.value.length >= 30 &&
    returnRequestImageData.value.length >= 2
  ) {
    isValid.value = true;
  } else {
    isValid.value = false;
  }
});

const dialogList = ref([
  {
    id: 0,
    text: "반품 신청이 완료되었습니다.",
    isShow: false,
    callback: completeSubmitReturn,
  },
  {
    id: 1,
    text: "입력값을 확인하세요.",
    isShow: false,
    callback: handleErrorInput,
  },
  {
    id: 2,
    text: "오류 : 관리자에게 문의하세요",
    isShow: false,
    callback: handleErrorEtc,
  },
]);
</script>

<style lang="scss" scoped></style>
