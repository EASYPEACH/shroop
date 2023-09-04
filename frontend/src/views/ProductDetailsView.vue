<template>
  <content-layout class="layout">
    <div class="imgSlide">
      <v-carousel
        hide-delimiter-background
        v-if="productImgs.length > 0"
        height="450"
      >
        <v-carousel-item
          v-for="productImg in productImgs"
          :key="productImg.id"
          :src="productImg.productImgUrl"
          contain
          eager
        >
        </v-carousel-item>
      </v-carousel>
      <div class="productContent">
        <v-card-item>
          <div>
            <div class="productContent__item">
              <div class="productContent__item-title">
                <h2>{{ productContent.title }}</h2>
                <transaction-badge
                  v-if="productContent.transactionStatus !== null"
                />
              </div>
              <div class="productContent__side-tooltips">
                <v-menu transition="scale-transition">
                  <template v-slot:activator="{ props }">
                    <v-btn icon="mdi-dots-vertical" v-bind="props"></v-btn>
                  </template>
                  <ul>
                    <li
                      class="font-weight-bold"
                      v-if="loginCheckStore.id !== profile.id"
                      @click="() => $router.push(`/report/${route.params.id}`)"
                    >
                      신고하기
                    </li>
                    <li
                      class="font-weight-bold"
                      v-if="
                        loginCheckStore.id === profile.id &&
                        !TRANSACTION_STATUS[productContent.transactionStatus]
                      "
                      @click="handleClickDeleteButton"
                    >
                      삭제하기
                    </li>
                    <li
                      class="font-weight-bold"
                      v-if="loginCheckStore.id === profile.id"
                      @click="() => $router.push(`/edit/${$route.params.id}`)"
                    >
                      수정하기
                    </li>
                  </ul>
                </v-menu>
              </div>
            </div>
            <div class="productContent__price">
              <div class="text-h5 mb-1">
                {{ productContent.price?.toLocaleString() }}원
              </div>
              <div
                v-if="productContent.isCheckedDeliveryFee"
                class="text-caption mb-1"
              >
                배송비 포함
              </div>
              <div v-else class="text-caption mb-1">배송비 미포함</div>
            </div>
            <v-table class="mt-2">
              <tbody>
                <tr>
                  <td class="text-subtitle-2">
                    <v-icon icon="mdi-vector-point"></v-icon>카테고리
                  </td>
                  <td>{{ productContent.category?.name }}</td>
                </tr>
                <tr>
                  <td class="text-subtitle-2">
                    <v-icon icon="mdi-vector-point"></v-icon>생성일자
                  </td>
                  <td>{{ formatDate(productContent.createDate) }}</td>
                </tr>
              </tbody>
            </v-table>
          </div>
        </v-card-item>
        <v-divider class="border-opacity-25 mb-5"></v-divider>
        <div class="productContent__profile">
          <div class="productContent__profile-content">
            <v-avatar>
              <v-img
                src="https://cdn.vuetifyjs.com/images/john.jpg"
                alt="John"
              ></v-img>
            </v-avatar>
            <div class="text-h5">{{ profile.nickName }}</div>
          </div>
          <div class="productContent__profile-grade">
            <v-icon icon="mdi-umbrella-beach-outline" />
          </div>
        </div>

        <v-card-actions>
          <like-button
            :product="productContent"
            variant="outlined"
            @handle-click-like="handleClickLike"
          />
          <v-btn
            v-if="
              loginCheckStore.id !== profile.id &&
              !TRANSACTION_STATUS[productContent.transactionStatus]
            "
            variant="outlined"
            @click="handlePurchase"
          >
            구매하기
          </v-btn>
        </v-card-actions>
      </div>
    </div>

    <div class="productDetail">
      <v-alert color="red" variant="outlined">
        <div class="text-h5">
          <v-icon icon="mdi-alert-decagram"></v-icon> 구매 주의 사항
        </div>
        <div>
          상품 정보를 꼼꼼히 확인해주세요! <br />
          중고상품이므로 상품 정보 미확인으로 인한 피해는 <br />
          구매자에게 부담이 있습니다.
        </div>
      </v-alert>
      <div class="productDetail__content">
        <product-title bigTitle title="상품 정보" />
        <v-table>
          <thead>
            <tr>
              <th class="text-left">정보</th>
              <th class="text-left">내용</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td class="font-weight-bold">구입시기</td>
              <td>{{ productContent.purchaseDate }}</td>
            </tr>
            <tr>
              <td class="font-weight-bold">브랜드명/모델명</td>
              <td>{{ productContent.brand }}</td>
            </tr>
            <tr>
              <td class="font-weight-bold">상태</td>
              <td>{{ PRODUCT_GRADE_EN[productContent.grade] }}</td>
            </tr>
          </tbody>
        </v-table>
      </div>
      <div class="productDetail__defect">
        <product-title bigTitle title="상품 판매 이유" />
        <div class="text-h6">
          {{ productContent.saleReason }}
        </div>
        <product-title bigTitle title="상품 결함 정보" />
        <div v-if="!productContent.isDefect" class="text-h6">
          결함 여부 : 없음
        </div>
        <div v-else class="text-h6">결함 여부 : 있음</div>
        <div v-if="productContent.isDefect" class="productDetail__defect">
          <v-img
            width="300"
            v-for="(defectImg, idx) in defectImgs"
            :src="defectImg.productImgUrl"
            :key="idx"
          ></v-img>
        </div>
      </div>
      <div class="productDetail__content">
        <product-title bigTitle title="상품 기타 상세 정보" />
        <div
          class="text-h6"
          v-html="productContent.content?.replaceAll('\n', '<br />')"
        ></div>
      </div>
    </div>
    <plain-modal
      modalText="정말 삭제하시겠습니까?"
      v-model="deleteModal"
      @handle-confirm="handleClickDeleteRequest"
      @handle-cancle="handleClickCancle"
    />
  </content-layout>
</template>

<script setup>
import { onBeforeMount, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getApi, deleteApi, postApi } from "@/api/modules";
import { formatDate } from "@/utils/formatDate";
import { PRODUCT_GRADE_EN } from "@/consts/productGrade";
import { useCheckLogin } from "@/store/useCheckLogin";
import TRANSACTION_STATUS from "@/consts/status";
import ContentLayout from "@/layouts/ContentLayout.vue";
import ProductTitle from "@/components/Title/ProductTitle.vue";
import PlainModal from "@/components/Modal/PlainModal.vue";
import TransactionBadge from "@/components/Badge/TransactionBadge.vue";
import LikeButton from "@/components/Button/LikeButton.vue";

const loginCheckStore = useCheckLogin();
const route = useRoute();
const router = useRouter();
const productImgs = ref([]);
const defectImgs = ref([]);
const profile = ref({
  id: 0,
  nickName: "",
});

const productContent = ref({});
const deleteModal = ref(false);

onBeforeMount(async () => {
  // 특정 상품 데이터 받아오기
  try {
    const data = await getApi({
      url: `/api/products/${route.params.id}`,
    });

    productContent.value = data;
    productImgs.value = data.productImgList.filter((img) => !img.isDefect);
    defectImgs.value = data.productImgList.filter((img) => img.isDefect);

    profile.value.nickName = data.seller.nickName;
    profile.value.id = data.seller.id;
  } catch (err) {
    console.error(err);
  }
});

// 상품 좋아요, 좋아요 취소
const handleClickLike = async () => {
  try {
    if (productContent.value.isLike) {
      await deleteApi({
        url: `/api/likes/${productContent.value.id}`,
      });
      productContent.value.isLike = false;
      productContent.value.likesCount -= 1;
    } else {
      await postApi({
        url: `/api/likes/${productContent.value.id}`,
      });
      productContent.value.isLike = true;
      productContent.value.likesCount += 1;
    }
  } catch (err) {
    if (err.response.status === 403) {
      router.push("/login");
    }
  }
};

// 구매하기
const handlePurchase = () => {
  router.push(`/purchase/${route.params.id}`);
};

// 상품 삭제하기 - 확인모달노출
const handleClickDeleteButton = async () => {
  deleteModal.value = true;
};

// 상품 삭제하기 확인모달 취소 - 삭제 취소
const handleClickCancle = async () => {
  deleteModal.value = false;
};

// 상품삭제요청
const handleClickDeleteRequest = async () => {
  try {
    await deleteApi({
      url: `/api/products/${route.params.id}`,
    });
    deleteModal.value = false;
    router.push("/");
  } catch (err) {
    console.error(err);
  }
};
</script>

<style lang="scss" scoped>
.layout {
  margin-top: 50px;
}

.imgSlide {
  display: flex;
  justify-content: space-between;
  gap: 50px;

  .v-carousel {
    flex-basis: 50%;
  }
  .productContent {
    flex: 1;
    display: flex;
    justify-content: space-between;
    flex-direction: column;
    gap: 20px;
    .productContent__price {
      margin: 15px 0;
      display: flex;
      align-items: flex-end;
      gap: 20px;
    }
    .v-card-item {
      .productContent__item {
        display: flex;
        justify-content: space-between;
        .productContent__item-title {
          width: 100%;
          display: flex;
          flex-wrap: wrap;
          justify-content: space-between;
          align-items: center;
          h2 {
            width: 400px;
            word-break: keep-all;
            font-size: 25px;
            font-weight: 600;
            @media (max-width: 1500px) {
              width: 300px;
            }
          }
        }
        .productContent__side-tooltips {
          width: 48px;
          .v-btn {
            box-shadow: none;
          }
        }
      }

      .v-table {
        width: 70%;
        td {
          height: fit-content;
          padding: 0;
          border: none;
          color: gray;
        }
      }
    }
    .productContent__profile {
      display: flex;
      align-items: center;
      gap: 10px;

      .productContent__profile-content {
        display: flex;
        align-items: center;
        gap: 10px;
      }
    }
  }
  @media (max-width: 960px) {
    flex-direction: column;
    align-items: center;
    .productContent {
      width: 100%;
    }
  }
}

.v-carousel {
  .v-carousel-item {
    background-color: #000;
  }
  @media (max-width: 960px) {
    width: 100%;
  }
}

.v-card-actions {
  justify-content: space-between;
  gap: 20px;
  .v-btn {
    width: 100%;
    flex: 1;
  }
  .v-btn-toggle {
    height: 36px;
    border: thin solid currentColor;
  }
}

.productDetail {
  display: flex;
  flex-direction: column;
  margin-top: 100px;
  .v-alert {
    width: 600px;
    font-size: 20px;
    text-align: center;
    margin: 100px auto;
    word-break: keep-all;
    padding: 20px 0;
  }
  .productDetail__content {
    margin-top: 60px;
    .v-table {
      font-size: 20px;
    }
  }
  .productDetail__defect {
    margin-top: 60px;
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
  @media (max-width: 960px) {
    width: 100%;
    .v-alert {
      width: 100%;
      font-size: 16px;
    }
  }
}

.v-menu {
  ul {
    margin-top: 10px;
    background: rgb(var(--v-theme-mainGray));
    border-radius: 10px;
    > li {
      cursor: pointer;
      padding: 10px 20px;
      text-align: center;
      color: #fff;
      font-weight: 600;
    }
  }

  .v-overlay__content {
    .v-list {
      background: rgb(var(--v-theme-mainGray));
      border-radius: 10px;
      color: white;
      padding: 0;
    }
  }
}
</style>
