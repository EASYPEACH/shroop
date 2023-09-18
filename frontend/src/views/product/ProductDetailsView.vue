<template>
  <div>
    <product-swiper
      v-if="productImgs.length > 0 && isMobile"
      :product-content="productContent"
      :product-imgs="productImgs"
      @show-image-modal="(imgUrl) => showImageModal(imgUrl)"
    />

    <content-layout>
      <p class="category">
        <span>{{ productContent.category?.name }}</span>
        <v-icon icon="mdi-chevron-right" />
      </p>
      <div class="product__top">
        <product-swiper
          v-if="productImgs.length > 0 && !isMobile"
          :product-content="productContent"
          :product-imgs="productImgs"
          @show-image-modal="(imgUrl) => showImageModal(imgUrl)"
        />
        <div class="product__top-content">
          <v-card-item>
            <div>
              <transaction-badge
                v-if="productContent.transactionStatus !== null"
              />
              <div class="productContent__item">
                <div class="productContent__item-title">
                  <h2>{{ productContent.title }}</h2>
                </div>
                <div class="productContent__side">
                  <div class="productContent__side-heart" v-if="!isMobile">
                    <v-icon icon="mdi-heart" color="heartRed" />
                    <span>{{ productContent.likesCount }}</span>
                  </div>
                </div>
              </div>
              <p class="productContent__price">
                <span>{{ productContent.price?.toLocaleString() }}</span> 원
              </p>
            </div>
          </v-card-item>
          <div class="productContent__profile">
            <aside>
              <div class="productContent__profile-content">
                <v-avatar>
                  <v-img
                    :src="profile.profileImg"
                    :alt="profile.nickName"
                  ></v-img>
                </v-avatar>
                <h5>{{ profile.nickName }}</h5>
              </div>
              <div class="productContent__profile-grade">
                <v-icon :icon="profile.rank" />
                {{ profile.gradeScore }} 점
              </div>
            </aside>
            <v-btn
              variant="text"
              v-if="loginCheckStore.id !== profile.id"
              @click="() => $router.push(`/report/${route.params.id}`)"
              >이 게시물 신고하기</v-btn
            >
          </div>
          <v-divider class="border-opacity-25 mb-5"></v-divider>
          <ul class="sell-details">
            <li>
              <h3>배송비</h3>
              <span>
                {{
                  productContent.isCheckedDeliveryFee
                    ? "배송비 포함"
                    : "배송비 미포함"
                }}
              </span>
            </li>
            <li>
              <h3>교환여부</h3>
              <span> 교환 불가 </span>
            </li>
            <li>
              <h3>등록일</h3>
              <span> {{ formatDate(productContent.createDate) }} </span>
            </li>
          </ul>

          <div class="product__buttons">
            <like-button
              v-if="loginCheckStore.id !== profile.id"
              class="product__buttons-common"
              height="auto"
              :product="productContent"
              @handle-click-like="handleClickLike"
              :no-count-text="!isMobile"
            />

            <v-btn
              class="product__buttons-common"
              v-if="loginCheckStore.id !== profile.id"
              :disabled="productContent.transactionStatus !== null"
              height="auto"
              variant="text"
              @click="handlePurchase"
            >
              {{
                TRANSACTION_STATUS[productContent.transactionStatus]
                  ? "품절"
                  : "구매하기"
              }}
            </v-btn>
            <v-btn
              v-if="loginCheckStore.id === profile.id"
              class="product__buttons-common product__buttons-manage"
              height="auto"
              variant="text"
              @click="() => $router.push('/mypage/sellList')"
            >
              내 상품 관리하기
            </v-btn>
          </div>
        </div>
      </div>

      <div class="productDetail">
        <v-alert color="red" variant="outlined" data-aos="fade-up">
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
          <product-title bigTitle title="상품 정보" data-aos="fade-up" />
          <v-table data-aos="fade-up">
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
                <td>{{ PRODUCT_GRADE_EN[productContent.productGrade] }}</td>
              </tr>
            </tbody>
          </v-table>
        </div>
        <div class="productDetail__defect">
          <product-title bigTitle title="상품 판매 이유" data-aos="fade-up" />
          <div class="text-h6" data-aos="fade-up">
            {{ productContent.saleReason }}
          </div>
          <product-title bigTitle title="상품 결함 정보" data-aos="fade-up" />
          <div
            v-if="!productContent.isDefect"
            class="text-h6"
            data-aos="fade-up"
          >
            결함 여부 : 없음
          </div>
          <div v-else class="text-h6" data-aos="fade-up">결함 여부 : 있음</div>
          <div
            v-if="productContent.isDefect"
            class="productDetail__defect"
            data-aos="fade-up"
          >
            <v-img
              v-for="(defectImg, idx) in defectImgs"
              :src="defectImg.productImgUrl"
              :key="idx"
              @click="() => showImageModal(defectImg.productImgUrl)"
              cover
            ></v-img>
          </div>
        </div>
        <div class="productDetail__content">
          <product-title
            bigTitle
            title="상품 기타 상세 정보"
            data-aos="fade-up"
          />
          <div
            data-aos="fade-up"
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
      <image-thumb-modal
        v-model="imageModalValue"
        :img-src="imageModalSrc"
        @handle-close-modal="imageModalValue = false"
      />
    </content-layout>
  </div>
</template>

<script setup>
import { onBeforeMount, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getApi, deleteApi, postApi } from "@/api/modules";
import { PRODUCT_GRADE_EN } from "@/consts/productGrade";
import { useCheckLogin } from "@/store/modules";
import TRANSACTION_STATUS from "@/consts/status";
import { useDisplay } from "vuetify";

import ContentLayout from "@/layouts/ContentLayout.vue";
import TransactionBadge from "@/components/TransactionBadge.vue";
import { ProductTitle } from "@/components/Title";
import { PlainModal, ImageThumbModal } from "@/components/Modal";
import { LikeButton } from "@/components/Button";
import { formatDate } from "@/utils";
import ProductSwiper from "@/components/ProductSwiper.vue";

const loginCheckStore = useCheckLogin();
const route = useRoute();
const router = useRouter();
const productImgs = ref([]);
const defectImgs = ref([]);
const profile = ref({});

const productContent = ref({});
const deleteModal = ref(false);
const imageModalValue = ref(false);
const imageModalSrc = ref("");
const display = useDisplay();
const isMobile = ref(display.smAndDown);

onBeforeMount(async () => {
  // 특정 상품 데이터 받아오기
  try {
    const data = await getApi({
      url: `/api/products/${route.params.id}`,
    });

    productContent.value = data;
    productImgs.value = data.productImgList.filter((img) => !img.isDefect);
    defectImgs.value = data.productImgList.filter((img) => img.isDefect);

    profile.value = data.seller;

    handleScoreToRankIcon(data.seller.gradeScore);
  } catch (err) {
    console.error(err);
  }
});

//Grade Transfer
const handleScoreToRankIcon = (gradeScore) => {
  if (gradeScore < 30) {
    profile.value.rank = "mdi-coat-rack";
  }
  if (30 <= gradeScore && gradeScore < 60) {
    profile.value.rank = "mdi-umbrella-closed-variant";
  }
  if (gradeScore >= 60) {
    profile.value.rank = "mdi-umbrella-beach-outline";
  }
};

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
const handlePurchase = async () => {
  if (loginCheckStore.isLogin) {
    try {
      const response = await getApi({
        url: "/api/bank/checkingAccount",
      });
      if (response === false) {
        alert("연동된 계좌가 없습니다.");
        router.push("/mypage/home");
      } else {
        router.push(`/purchase/${route.params.id}`);
      }
    } catch (error) {
      alert(error.response.data.message);
    }
  } else {
    router.push("/login");
  }
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

const showImageModal = (imgUrl) => {
  imageModalSrc.value = imgUrl;
  imageModalValue.value = true;
};
</script>

<style lang="scss" scoped>
.category {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  font-weight: 600;
  width: fit-content;
  padding: 2px 10px;
  border-radius: 3px;
  color: #494949;
  font-size: 15px;
  @media (max-width: 750px) {
    padding: 0;
  }
}
.product__top {
  display: flex;
  justify-content: space-between;
  gap: 50px;

  &-content {
    flex: 1;
    display: flex;
    justify-content: space-between;
    flex-direction: column;
    gap: 20px;
    overflow: visible;
    .productContent__price {
      margin: 5px 0;
      span {
        font-size: 35px;
      }
      font-weight: 600;
      @media (max-width: 750px) {
        span {
          font-size: 20px;
        }
      }
    }
    .v-card-item {
      padding: 0;
      .productContent__item {
        display: flex;
        justify-content: space-between;
        .productContent__item-title {
          width: fit-content;
          display: flex;
          flex-wrap: wrap;
          justify-content: space-between;
          align-items: center;
          h2 {
            display: inline-block;
            word-break: keep-all;
            font-size: 25px;
            font-weight: 600;
          }
          @media (max-width: 750px) {
            h2 {
              font-size: 16px;
            }
          }
        }
        .productContent__side {
          display: flex;
          align-items: center;

          &-heart {
            display: flex;
            align-items: center;
            gap: 5px;
          }
          .v-btn {
            box-shadow: none;
          }
        }
      }
    }

    @media (max-width: 960px) {
      width: 100%;
    }
  }

  @media (max-width: 960px) {
    flex-direction: column;
    align-items: center;
    .productContent {
      width: 100%;
    }
  }
  .sell-details {
    margin: 20px 0;
    li + li {
      margin-top: 10px;
    }
    li {
      display: flex;
      h3 {
        width: 20%;
      }
    }
  }
  .productContent__profile {
    display: flex;
    align-items: center;
    gap: 10px;
    justify-content: space-between;

    aside {
      display: flex;
      align-items: center;
      gap: 10px;
      justify-content: space-between;
      width: 100%;
      .productContent__profile-content {
        display: flex;
        align-items: center;
        gap: 10px;
      }
    }

    .v-btn {
      text-decoration: underline;
      padding: 0;
    }
  }
}

.product__buttons {
  display: flex;
  gap: 10px;
  align-items: center;
  width: calc(100% - 10px);
  &-common {
    flex-basis: 50%;
    background: rgb(var(--v-theme-mainGray));
    font-weight: 600;
    font-size: 20px;
    color: #fff;
    padding: 10px 0;
    transition: 0.5s all;
    &:hover {
      transform: scale(1.05);
    }
    @media (max-width: 750px) {
      font-size: 16px;
    }
  }
  &-manage {
    flex: 1;
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
      @media (max-width: 750px) {
        td {
          font-size: 15px;
        }
      }
    }
  }
  .productDetail__defect {
    margin-top: 60px;
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
