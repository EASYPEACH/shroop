<template>
  <div class="banner">
    <router-link :to="`/detail/${item.id}`">
      <v-icon start>mdi-archive</v-icon>
      <h4>
        {{ item.title }}
      </h4>
    </router-link>

    <aside>
      <div v-if="isStatus" class="status">
        <div>{{ item.status }}</div>
        <ul>
          <li
            v-if="
              !(
                item.status === TRANSACTION_STATUS.SELLING ||
                item.status === TRANSACTION_STATUS.PURCHASE_REQUEST
              )
            "
          >
            <mini-button text="신고하기" @click="handleReport" />
          </li>
          <li
            v-if="
              !isSeller &&
              (item.status === TRANSACTION_STATUS.SHIPPING ||
                item.status === TRANSACTION_STATUS.COMPLETE)
            "
          >
            <mini-button
              text="구매확정"
              @click="purchaseConfirmDialog = true"
            />
          </li>
          <li
            v-if="
              !isSeller && item.status === TRANSACTION_STATUS.PURCHASE_REQUEST
            "
          >
            <mini-button
              text="구매취소"
              @click="canclePurchaseConfirmDialog = true"
            />
          </li>
          <li
            v-if="
              !isSeller &&
              !(
                item.status === TRANSACTION_STATUS.PURCHASE_REQUEST ||
                item.status === TRANSACTION_STATUS.RETURN_COMPLETE
              )
            "
          >
            <mini-button text="반품신청" />
          </li>
          <li
            v-if="isSeller && item.status === TRANSACTION_STATUS.RETURN_REQUEST"
          >
            <mini-button
              text="반품확정"
              @click="returnRequestConfirmDialog = true"
            />
          </li>
          <li v-if="isSeller && item.status !== TRANSACTION_STATUS.SELLING">
            <mini-button
              text="구매자정보"
              @click="showBuyerInfoDialog = true"
            />
          </li>
          <li v-if="isSeller && item.status === TRANSACTION_STATUS.SELLING">
            <mini-button
              text="삭제하기"
              @click="deleteProductConfirmDialog = true"
            />
          </li>
          <li
            v-if="
              isSeller && item.status === TRANSACTION_STATUS.RETURN_COMPLETE
            "
          >
            <mini-button text="반품결과" />
          </li>
        </ul>
      </div>
      <v-btn
        v-if="!isStatus"
        @click="$emit('toggleHeart', item.id)"
        class="like-btn"
        variant="text"
      >
        <v-icon icon="mdi-heart" />
      </v-btn>
    </aside>
    <plain-modal
      v-model="purchaseConfirmDialog"
      @handle-cancle="purchaseConfirmDialog = false"
      modalText="구매를 확정 하시겠습니까?"
    />
    <plain-modal
      v-model="returnRequestConfirmDialog"
      @handle-cancle="returnRequestConfirmDialog = false"
      modalText="반품을 확정 하시겠습니까?"
    />
    <plain-modal
      v-model="deleteProductConfirmDialog"
      @handle-cancle="deleteProductConfirmDialog = false"
      modalText="상품을 삭제하시겠습니까?"
    />
    <plain-modal
      v-model="canclePurchaseConfirmDialog"
      @handle-cancle="canclePurchaseConfirmDialog = false"
      modalText="구매신청을 취소하시겠습니까?"
    />
    <buyer-info-modal
      v-model="showBuyerInfoDialog"
      @handle-cancle="showBuyerInfoDialog = false"
      :buyerInfo="buyeInfo"
    />
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import MiniButton from "../Button/MiniButton.vue";
import PlainModal from "../Modal/PlainModal.vue";
import BuyerInfoModal from "../Modal/BuyerInfoModal.vue";
import TRANSACTION_STATUS from "@/consts/delivery";
import { ref } from "vue";

defineProps({
  item: Object,
  isStatus: Boolean,
  isSeller: Boolean,
});
defineEmits(["toggleHeart"]);

const router = useRouter();
const purchaseConfirmDialog = ref(false);
const returnRequestConfirmDialog = ref(false);
const deleteProductConfirmDialog = ref(false);
const canclePurchaseConfirmDialog = ref(false);
const showBuyerInfoDialog = ref(false);
const buyeInfo = ref({
  name: "김뿅뿅",
  address: "경기도 고양시 덕양구 신원3로 20",
  phoneNumber: "01012341234",
});

// 신고하기
const handleReport = () => {
  router.push("/");
};
// 삭제하기
const handleDeleteProduct = () => {};
// 구매확정
const handleConfirmPurchase = () => {};
// 구매신청 취소
const handleCanclePurchaseRequest = () => {};
// 반품확정
const handleReturnRequest = () => {};
// 구매자정보보기
const handleShowBuyerInfo = () => {};
</script>

<style lang="scss" scoped>
.banner {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid rgba(var(--v-theme-mainGray), 0.4);
  padding: 5px 0;
  height: 90px;
  a {
    display: flex;
    align-items: center;
    gap: 20px;

    .v-icon {
      border-radius: 50%;
      background: rgb(var(--v-theme-subBlue));
      color: #fff;
      padding: 20px;
    }
    h4 {
      font-weight: 600;
    }
  }
}

aside {
  display: flex;
  align-items: center;
  .status {
    display: flex;
    align-items: center;
    gap: 30px;
    > div {
      width: 80px;
      text-align: center;
    }
    ul {
      font-size: 14px;
      width: 90px;
      li {
        text-align: center;
      }
    }
  }
  .like-btn {
    color: rgb(var(--v-theme-heartRed));
  }
}
</style>
