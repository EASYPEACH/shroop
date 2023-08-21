<template>
  <section>
    <main-title title="신고접수" />
    <content-layout>
      <v-form
        enctype="multipart/form-data"
        @submit.prevent="handleSubmitReport"
      >
        <product-title title="제목" isRequired />
        <custom-text-input
          :rules="[defaultTextRule.required]"
          placeholderText="제목"
          v-model="title"
        />
        <product-title title="신고대상 게시물" />
        <custom-text-input v-model="product" disabled />
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
        <submit-button text="신고접수" />
      </v-form>
    </content-layout>
  </section>
</template>

<script setup>
import { ref } from "vue";
import { defaultTextRule, selectRule } from "@/components/Form/data/formRules";
import chanageFiles from "@/utils/changeFiles";
import deleteImage from "@/utils/deleteImage";
import MainTitle from "@/components/Title/MainTitle.vue";
import ContentLayout from "@/layouts/ContentLayout.vue";
import ProductTitle from "@/components/Title/ProductTitle.vue";
import CustomTextInput from "@/components/Form/CustomTextInput.vue";
import CustomTextArea from "@/components/Form/CustomTextArea.vue";
import ImageAttach from "@/components/ImageAttach.vue";
import SubmitButton from "@/components/Button/SubmitButton.vue";

const title = ref("");
const isMediate = ref(null);
const product = ref("아이패드 프로 10.5");
const productImagesThumb = ref([]);
const imageData = ref({});
const productRef = ref(null);
const reportReason = ref("");

const handleAttachProductImage = (files) => {
  chanageFiles(files, productRef, productImagesThumb, imageData);
};
const handleDeleteProductImage = (idx) => {
  deleteImage(idx, productRef, productImagesThumb, imageData);
};
const handleSubmitReport = () => {
  console.log(imageData.value);
};
</script>

<style lang="scss" scoped></style>
