<template>
  <section>
    <main-title title="신고접수" />
    <content-layout>
      <v-form
        v-model="isVailed"
        enctype="multipart/form-data"
        @submit.prevent="handleSubmit"
      >
        <product-title title="제목" isRequired />
        <custom-text-input
          :rules="[defaultTextRule.required]"
          placeholderText="제목"
          v-model="title"
        />
        <product-title title="신고대상 게시물" />
        <p>{{ productName }}</p>
        <product-title title="중재신청 여부" isRequired />
        <v-radio-group
          :rules="[selectRule.required]"
          inline
          v-model="isMediate"
        >
          <v-radio :value="true" label="예"></v-radio>
          <v-radio :value="false" label="아니오"></v-radio>
        </v-radio-group>
        <image-attach
          v-if="isMediate"
          ref="productRef"
          @change-files="handleAttachProductImage"
          @delete-image="handleDeleteProductImage"
          required
          attach-name="reportImage"
          :images="productImagesThumb"
        />
        <product-title title="신고사유" isRequired />
        <CustomTextArea v-model="reportReason" />
        <submit-button :disabled="!isVailed" text="신고접수" />
      </v-form>
    </content-layout>
    <plain-modal
      v-for="dialog in dialogList"
      :key="dialog.id"
      :modalText="dialog.text"
      v-model="dialog.isShow"
      @handle-cancle="() => (dialog.isShow = !dialog.isShow)"
      @handle-confirm="dialog.callback"
    />
  </section>
</template>

<script setup>
import { ref, onBeforeMount } from "vue";
import { useRouter, useRoute } from "vue-router";
import { defaultTextRule, selectRule } from "@/components/Form/data/formRules";
import {
  changeFiles,
  deleteImage,
  multipartFormDataFile,
  multipartFormDataJson,
} from "@/utils";
import { multipartPostApi, getApi } from "@/api/modules";
import { MainTitle, ProductTitle } from "@/components/Title";
import { CustomTextArea, CustomTextInput } from "@/components/Form";
import { SubmitButton } from "@/components/Button";
import { PlainModal } from "@/components/Modal";
import ContentLayout from "@/layouts/ContentLayout.vue";
import ImageAttach from "@/components/ImageAttach.vue";

const router = useRouter();
const route = useRoute();

const title = ref("");
const productName = ref("");
const reportReason = ref("");

const productImagesThumb = ref([]);
const imageData = ref({});

const isVailed = ref(false);
const isMediate = ref(null);
const productRef = ref(null);

const handleAttachProductImage = (files) => {
  changeFiles(files, productRef, productImagesThumb, imageData);
};
const handleDeleteProductImage = (idx) => {
  deleteImage(idx, productRef, productImagesThumb, imageData);
};
const handleSubmit = () => {
  dialogList.value[0].isShow = true;
};
const handleSubmitReport = async () => {
  const formData = new FormData();

  multipartFormDataJson(formData, "reportRequest", {
    title: title.value,
    content: reportReason.value,
    isMediate: isMediate.value,
    productId: route.params.id,
  });

  multipartFormDataFile(formData, productRef.value, "multipartFileList");

  try {
    await multipartPostApi({
      url: "/api/reports",
      data: formData,
    });

    dialogList.value[0].isShow = false;
    dialogList.value[1].isShow = true;
  } catch (error) {
    const code = error.response.status;
    if (code === 400) {
      dialogList.value.forEach((dialog) => {
        dialog.isShow = false;
      });
      dialogList.value[2].isShow = true;
    } else {
      dialogList.value.forEach((dialog) => {
        dialog.isShow = false;
      });
      dialogList.value[3].isShow = true;
    }
  }
};
const completeSubmitRport = () => {
  router.push("/mypage/home");
};
const handleErrorInput = () => {
  dialogList.value[2].isShow = false;
};
const handleErrorEtc = () => {
  dialogList.value[3].isShow = false;
};

const dialogList = ref([
  {
    id: 0,
    text: "신고를 진행하시겠습니까?<br>신고는 신중히 부탁드립니다.",
    isShow: false,
    callback: handleSubmitReport,
  },
  {
    id: 1,
    text: "신고 접수가 완료되었습니다.",
    isShow: false,
    callback: completeSubmitRport,
  },
  {
    id: 2,
    text: "입력값을 확인하세요.",
    isShow: false,
    callback: handleErrorInput,
  },
  {
    id: 3,
    text: "오류 : 관리자에게 문의하세요",
    isShow: false,
    callback: handleErrorEtc,
  },
]);

const handleGetProductDate = async () => {
  try {
    const response = await getApi({
      url: `/api/products/${route.params.id}`,
    });

    productName.value = response.title;
  } catch (error) {
    console.error(error);
  }
};

onBeforeMount(() => {
  handleGetProductDate();
});
</script>
