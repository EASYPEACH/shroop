<template>
  <div>
    <product-banner
      :product="product"
      :is-heart="isHeart"
      @handle-click-like="$emit('handle-click-like')"
    >
      <aside>
        <div v-if="isStatus" class="status">
          <p>
            {{
              product.transactionStatus === null
                ? TRANSACTION_STATUS.SELLING
                : TRANSACTION_STATUS[product.transactionStatus]
            }}
          </p>
          <ul>
            <li v-for="list in MYPAGE" :key="list.ACTION">
              <mini-button
                :text="list.ACTION"
                v-if="
                  list.STATUS(
                    product.transactionStatus === null
                      ? TRANSACTION_STATUS.SELLING
                      : TRANSACTION_STATUS[product.transactionStatus],
                  )
                "
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
      @handle-confirm="list.callback"
    />
    <buyer-info-modal
      v-model="showBuyerInfoDialog"
      @handle-cancle="showBuyerInfoDialog = false"
      @handle-confirm="showBuyerInfoDialog = false"
      :buyerInfo="buyeInfo"
    />
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { deleteApi, patchApi, getApi } from "@/api/modules";
import TRANSACTION_STATUS from "@/consts/status";

import { PlainModal, BuyerInfoModal } from "../Modal";
import { MiniButton } from "../Button";
import { ProductBanner } from "../Banner";

const props = defineProps({
  product: Object,
  isStatus: Boolean,
  isSeller: Boolean,
  isHeart: Boolean,
});
const emits = defineEmits([
  "handle-click-like",
  "handle-get-sellHistory",
  "handle-get-purchaseHistory",
]);

const router = useRouter();
const showBuyerInfoDialog = ref(false);
const showReturnResultDialog = ref(false);
const buyeInfo = ref({
  name: "",
  phoneNumber: "",
  postCode: "",
  address: "",
  detailAddress: "",
});
const MYPAGE = [
  {
    ACTION: "중재하기",
    STATUS: (status) => {
      return !(
        status === TRANSACTION_STATUS.SELLING ||
        status === TRANSACTION_STATUS.PURCHASE_REQUEST ||
        status === TRANSACTION_STATUS.MEDIATE_REQUEST
      );
    },
    CLICK_EVENT: (id) => router.push(`/mediate/${id}`),
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
      return !props.isSeller && status === TRANSACTION_STATUS.COMPLETE;
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
    ACTION: "수정하기",
    STATUS: (status) => {
      return props.isSeller && status === TRANSACTION_STATUS.SELLING;
    },
    CLICK_EVENT: (id) => router.push(`/edit/${id}`),
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
    CLICK_EVENT: () => handleShowBuyerInfo(),
  },
  {
    ACTION: "배송등록",
    STATUS: (status) => {
      return props.isSeller && status === TRANSACTION_STATUS.PURCHASE_REQUEST;
    },
    CLICK_EVENT: (id) => router.push(`/deliveryRegist/${id}`),
  },
];

// 구매확정
const handleConfirmPurchase = async () => {
  try {
    await patchApi({
      url: `/api/buying/confirm/${props.product.id}`,
    });
    emits("handle-get-purchaseHistory");
  } catch (error) {
    console.log(error);
  }
};
// 구매신청 취소
const handleCanclePurchaseRequest = async () => {
  try {
    await deleteApi({
      url: `/api/buying/${props.product.id}`,
    });
    emits("handle-get-purchaseHistory");
  } catch (error) {
    console.log(error);
  }
};
// 반품확정
const handleReturnRequestConfirm = async () => {
  try {
    await patchApi({
      url: `/api/buying/return/confirm/${props.product.id}`,
    });
    emits("handle-get-sellHistory");
  } catch (error) {
    console.log(error);
  }
};
// 구매자정보보기
const handleShowBuyerInfo = async () => {
  try {
    const response = await getApi({
      url: `/api/buying/buyer/${props.product.id}`,
    });
    buyeInfo.value.name = response.name;
    buyeInfo.value.phoneNumber = response.phoneNumber;
    buyeInfo.value.postCode = response.postcode;
    buyeInfo.value.address = response.location;
    buyeInfo.value.detailAddress = response.detailLocation;

    showBuyerInfoDialog.value = true;
  } catch (error) {
    console.error(error);
  }
};

// 상품 삭제
const handleDeleteProduct = async () => {
  try {
    await deleteApi({
      url: `/api/products/${props.product.id}`,
    });
    emits("handle-get-sellHistory");
  } catch (error) {
    console.error(error);
  }
};

const dialogList = ref([
  {
    id: 1,
    text: "구매를 확정 하시겠습니까?",
    value: false,
    callback: handleConfirmPurchase,
  },
  {
    id: 2,
    text: "반품을 확정 하시겠습니까?",
    value: false,
    callback: handleReturnRequestConfirm,
  },
  {
    id: 3,
    text: "상품을 삭제하시겠습니까?",
    value: false,
    callback: handleDeleteProduct,
  },
  {
    id: 4,
    text: "구매신청을 취소하시겠습니까?",
    value: false,
    callback: handleCanclePurchaseRequest,
  },
]);
</script>

<style lang="scss" scoped>
aside {
  display: flex;
  align-items: center;
  .status {
    display: flex;
    align-items: center;
    @media (max-width: 750px) {
      p {
        font-size: 13px;
      }
    }

    > div {
      width: 80px;
      text-align: center;
    }
    ul {
      width: 90px;
      li {
        font-size: 13px;
        text-align: center;
      }
    }
  }
}
</style>
