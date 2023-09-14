<template>
  <content-layout>
    <main-title v-if="isReport" title="신고접수" />
    <main-title v-else title="중재접수" />
    <v-form
      v-model="isVailed"
      enctype="multipart/form-data"
      @submit.prevent="handleSubmit"
    >
      <product-title title="제목" isRequired />
      <custom-text-input
        :rules="[
          defaultTextRule.required,
          (v) => defaultTextRule.customMinLength(v, 2),
          (v) => defaultTextRule.customMaxLength(v, 30),
        ]"
        placeholderText="제목"
        @input="limitTitleCount"
        v-model="title"
      />
      <p>{{ title.length }} / 30</p>
      <product-title v-if="isReport" title="신고대상 게시물" />
      <product-title v-else title="중재대상 게시물" />
      <p>{{ productName }}</p>
      <div v-show="!isReport">
        <product-title title="중재 첨부사진" isRequired />
        <image-attach
          v-if="isMediate"
          ref="productRef"
          @change-files="handleAttachProductImage"
          @delete-image="handleDeleteProductImage"
          required
          attach-name="reportImage"
          :images="productImagesThumb"
        />
      </div>
      <product-title v-if="isReport" title="신고사유" isRequired />
      <product-title v-else title="중재사유" isRequired />
      <CustomTextArea v-model="reportReason" @input="limitContentCount" />
      <p>{{ reportReason.length }} / 255</p>
      <submit-button v-if="isReport" :disabled="!isVailed" text="신고접수" />
      <submit-button v-else :disabled="!isVailed" text="중재접수" />
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
import { useRouter, useRoute } from "vue-router";
import { defaultTextRule } from "@/components/Form/data/formRules";
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

const isReport = route.meta.isReport;
const isVailed = ref(false);
const isMediate = ref(!isReport);
const productRef = ref(null);

const limitTitleCount = () => {
  if (title.value.length >= 30) {
    title.value = title.value.substring(0, 30);
  }
};

const limitContentCount = () => {
  if (reportReason.value.length >= 255) {
    reportReason.value = reportReason.value.substring(0, 255);
    console.log(reportReason.value.length);
  }
};

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
    text: isReport
      ? "신고를 진행하시겠습니까?<br>신고는 신중히 부탁드립니다."
      : "중재를 진행하시겠습니까?<br>중재는 신중히 부탁드립니다.",
    isShow: false,
    callback: handleSubmitReport,
  },
  {
    id: 1,
    text: isReport
      ? "신고 접수가 완료되었습니다."
      : "중재 접수가 완료되었습니다.",
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

watchEffect(() => {
  if (title.value.length > 0 && reportReason.value.length >= 30) {
    if (!isReport) {
      if (productImagesThumb.value.length > 0) {
        isVailed.value = true;
      } else {
        isVailed.value = false;
      }
    } else {
      isVailed.value = true;
    }
  } else {
    isVailed.value = false;
  }
});
</script>
