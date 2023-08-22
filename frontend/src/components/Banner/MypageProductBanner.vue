<template>
  <div>
    <product-banner
      :product="product"
      :is-heart="isHeart"
      @handle-click-like="$emit('handle-click-like')"
    >
      <aside>
        <div v-if="isStatus" class="status">
          <div>{{ product.status }}</div>
          <ul>
            <li v-for="list in MYPAGE" :key="list.ACTION">
              <mini-button
                :text="list.ACTION"
                v-if="list.STATUS(product.status)"
                @click="() => list.CLICK_EVENT(product.id)"
              />
            </li>
          </ul>
        </div>
      </aside>
    </product-banner>
    <plain-modal
      v-for="list in dialogList"
      :key="list.id"
      :modalText="list.text"
      v-model="list.value"
      @handle-cancle="() => (list.value = !list.value)"
    />
    <buyer-info-modal
      v-model="showBuyerInfoDialog"
      @handle-cancle="showBuyerInfoDialog = false"
      :buyerInfo="buyeInfo"
    />
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import TRANSACTION_STATUS from "@/consts/delivery";
import MiniButton from "../Button/MiniButton.vue";
import PlainModal from "../Modal/PlainModal.vue";
import BuyerInfoModal from "../Modal/BuyerInfoModal.vue";
import ProductBanner from "../Banner/ProductBanner.vue";

const props = defineProps({
  product: Object,
  isStatus: Boolean,
  isSeller: Boolean,
  isHeart: Boolean,
});
defineEmits(["handle-click-like"]);

const router = useRouter();
const showBuyerInfoDialog = ref(false);
const showReturnResultDialog = ref(false);
const buyeInfo = ref({
  name: "김뿅뿅",
  address: "경기도 고양시 덕양구 신원3로 20",
  phoneNumber: "01012341234",
});
const MYPAGE = [
  {
    ACTION: "신고하기",
    STATUS: (status) => {
      return !(
        status === TRANSACTION_STATUS.SELLING ||
        status === TRANSACTION_STATUS.PURCHASE_REQUEST
      );
    },
    CLICK_EVENT: (id) => router.push(`/report/${id}`),
  },

  {
    ACTION: "반품신청",
    STATUS: (status) => {
      return (
        !props.isSeller &&
        !(
          status === TRANSACTION_STATUS.PURCHASE_REQUEST ||
          status === TRANSACTION_STATUS.RETURN_COMPLETE
        )
      );
    },
    CLICK_EVENT: (id) => router.push(`/return/${id}`),
  },
  {
    ACTION: "구매확정",
    STATUS: (status) => {
      return (
        !props.isSeller &&
        (status === TRANSACTION_STATUS.SHIPPING ||
          status === TRANSACTION_STATUS.COMPLETE)
      );
    },
    CLICK_EVENT: () => (dialogList.value[0].value = true),
  },
  {
    ACTION: "반품확정",
    STATUS: (status) => {
      return props.isSeller && status === TRANSACTION_STATUS.RETURN_REQUEST;
    },
    CLICK_EVENT: () => (dialogList.value[1].value = true),
  },

  {
    ACTION: "삭제하기",
    STATUS: (status) => {
      return props.isSeller && status === TRANSACTION_STATUS.SELLING;
    },
    CLICK_EVENT: () => (dialogList.value[2].value = true),
  },
  {
    ACTION: "구매취소",
    STATUS: (status) => {
      return !props.isSeller && status === TRANSACTION_STATUS.PURCHASE_REQUEST;
    },
    CLICK_EVENT: () => (dialogList.value[3].value = true),
  },
  {
    ACTION: "반품결과",
    STATUS: (status) => {
      return props.isSeller && status === TRANSACTION_STATUS.RETURN_COMPLETE;
    },
    CLICK_EVENT: () => (showReturnResultDialog.value = true),
  },
  {
    ACTION: "구매자정보",
    STATUS: (status) => {
      return props.isSeller && status !== TRANSACTION_STATUS.SELLING;
    },
    CLICK_EVENT: () => (showBuyerInfoDialog.value = true),
  },
  {
    ACTION: "배송등록",
    STATUS: (status) => {
      return props.isSeller && status === TRANSACTION_STATUS.PURCHASE_REQUEST;
    },
    CLICK_EVENT: (id) => router.push(`/deliveryRegist/${id}`),
  },
];
const dialogList = ref([
  {
    id: 1,
    text: "구매를 확정 하시겠습니까?",
    value: false,
  },
  {
    id: 2,
    text: "반품을 확정 하시겠습니까?",
    value: false,
  },
  {
    id: 3,
    text: "상품을 삭제하시겠습니까?",
    value: false,
  },
  {
    id: 4,
    text: "구매신청을 취소하시겠습니까?",
    value: false,
  },
]);
// TODO
// 삭제하기
const handleDeleteProduct = () => {};
// 구매확정
const handleConfirmPurchase = () => {};
// 구매신청 취소
const handleCanclePurchaseRequest = () => {};
// 반품확정
const handleReturnRequestConfirm = () => {};
// 구매자정보보기
const handleShowBuyerInfo = () => {};
</script>

<style lang="scss" scoped>
aside {
  display: flex;
  align-items: center;
  .status {
    display: flex;
    align-items: center;
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
}
</style>
