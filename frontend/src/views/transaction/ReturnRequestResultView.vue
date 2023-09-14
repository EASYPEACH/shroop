<template>
  <content-layout class="layout">
    <main-title title="반품신청서" />

    <ul>
      <li>
        <product-title title="반품 신청 상품" />
        <p>{{ returnRequestData.productTitle }}</p>
      </li>
      <li>
        <product-title title="반품 신청 관련 이미지" />
        <div class="productDetail__defect">
          <v-img
            v-for="defectImg in returnRequestData.productReturnImgResponseList"
            :src="defectImg.imgUrl"
            :key="defectImg.id"
            @click="() => showImageModal(defectImg.imgUrl)"
            cover
          ></v-img>
        </div>
      </li>
      <li>
        <product-title title="반품 사유" />
        <p>{{ returnRequestData.content }}</p>
      </li>
    </ul>

    <div class="btnWrap">
      <v-btn
        height="auto"
        color="mainGreen"
        variant="elevated"
        @click="confirmReturn = true"
        >반품 확정</v-btn
      >
      <v-btn
        height="auto"
        color="mainGreen"
        variant="outlined"
        @click="cancleReturn = true"
        >반품 반려</v-btn
      >
    </div>

    <image-thumb-modal
      v-model="imageModalValue"
      :img-src="imageModalSrc"
      @handle-close-modal="imageModalValue = false"
    />

    <plain-modal
      modalText="반품을 확정하시겠습니까?"
      v-model="confirmReturn"
      @handle-confirm="handleConfirmReturn"
      @handle-cancle="confirmReturn = false"
    />

    <plain-modal
      modalText="반품을 반려 하시겠습니까?"
      v-model="cancleReturn"
      @handle-confirm="handleCancleReturn"
      @handle-cancle="cancleReturn = false"
    />
  </content-layout>
</template>

<script setup>
import router from "@/router";
import ContentLayout from "@/layouts/ContentLayout.vue";
import { MainTitle, ProductTitle } from "@/components/Title";
import { ImageThumbModal, PlainModal } from "@/components/Modal";
import { onBeforeMount, ref } from "vue";
import { deleteApi, getApi, patchApi } from "@/api/modules";
import { useRoute } from "vue-router";

const route = useRoute();
const returnRequestData = ref({});
const imageModalValue = ref(false);
const imageModalSrc = ref("");
const confirmReturn = ref(false);
const cancleReturn = ref(false);

onBeforeMount(async () => {
  const data = await getApi({
    url: `/api/return/${route.params.id}`,
  });
  returnRequestData.value = data;
});

const showImageModal = (imgUrl) => {
  imageModalSrc.value = imgUrl;
  imageModalValue.value = true;
};

// 반품확정
const handleConfirmReturn = async () => {
  try {
    await patchApi({
      url: `/api/buying/return/confirm/${route.params.id}`,
    });
    router.push("/mypage/sellList");
    confirmReturn.value = false;
  } catch (error) {
    console.log(error);
  }
};

// 반품반려
const handleCancleReturn = async () => {
  try {
    await deleteApi({
      url: `/api/return/${route.params.id}`,
    });
    router.push("/mypage/sellList");
    cancleReturn.value = false;
  } catch (error) {
    console.log(error);
  }
};
</script>

<style lang="scss" scoped>
.layout {
  display: flex;
  flex-direction: column;

  .btnWrap {
    margin: 100px 0;
    place-self: flex-end;
    display: flex;
    gap: 20px;
    .v-btn {
      padding: 10px;
      font-size: 16px;
    }
  }
}
.productDetail__defect {
  display: flex;
  align-items: center;
  gap: 20px;
  overflow: auto;

  .v-img {
    flex: none;
    width: 200px;
    height: 200px;
    cursor: pointer;
    background: #000;
    object-fit: cover;
    object-position: center;
  }
  div {
    margin-top: 30px;
  }
  .productDetail__defect {
    margin-top: 0px;
    display: flex;
    gap: 20px;
    overflow: auto;
    white-space: nowrap;
  }
}
</style>
