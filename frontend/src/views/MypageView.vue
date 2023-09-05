<template>
  <content-layout>
    <v-card variant="plain" class="mypage">
      <!-- <v-toolbar>
        <v-toolbar-title>{{ tab }}</v-toolbar-title>
      </v-toolbar> -->
      <div class="mypage__content">
        <v-tabs
          v-model="tab"
          :direction="isTablet ? 'horizontal' : 'vertical'"
          color="subBlue"
        >
          <v-tab
            v-for="t in tabList"
            :key="t.id"
            :value="t.title"
            @click="handleTabClick(t.id)"
          >
            <v-icon start> {{ t.icon }} </v-icon>
            {{ t.title }}
          </v-tab>
        </v-tabs>
        <v-window v-model="tab">
          <!-- 마이페이지 -->
          <v-window-item value="마이페이지">
            <div class="mypage__profile">
              <div class="profile__img">
                <img
                  :src="profile.imagePath ? profile.imagePath : basicProfile"
                />
                <mini-button
                  text="프로필수정"
                  @click="() => $router.push(`/profileEdit/${0}`)"
                />
              </div>

              <div class="profile__edit">
                <div class="profile__info">
                  <div class="profile__info-rank">
                    <v-icon :icon="profile.rank" class="profile__name-rank" />
                    <v-btn variant="plain" class="rank-help">
                      <v-icon icon="mdi-help" />
                      <v-tooltip activator="parent" location="end">
                        <p>슈룹 안심거래 등급</p>
                        <ul>
                          <li>
                            <v-icon
                              icon="mdi-umbrella-beach-outline"
                            />골프우산: 상
                          </li>
                          <li>
                            <v-icon
                              icon="mdi-umbrella-closed-variant"
                            />접이식우산: 중
                          </li>
                          <li><v-icon icon="mdi-coat-rack" />우비: 하</li>
                        </ul>
                      </v-tooltip>
                    </v-btn>
                  </div>
                  <p class="profile__info-name">
                    <span>{{ profile.nickName }}</span
                    >님
                  </p>
                </div>

                <div class="profile__point">
                  <div>
                    {{ profile.point }}
                    <v-icon icon="mdi-water" />
                  </div>
                  <mini-button
                    text="충전"
                    @click="showChargePointModal = true"
                  />
                  <mini-button
                    text="환전"
                    @click="showExchangePointModal = true"
                  />
                </div>
                <div class="profile_account">
                  <v-col cols="auto">
                    <v-btn
                      v-if="profile.account === null"
                      color="blue"
                      size="large"
                      @click="showLinkAccountModal = true"
                      >계좌 연동하기</v-btn
                    >
                  </v-col>
                  <div
                    class="profile_account-block"
                    v-if="profile.account !== null"
                  >
                    <div class="profile_account-text">내 연결 계좌</div>
                    <div class="profile_account-info">
                      {{ profile.account }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <v-divider />
            <div class="mypage__like">
              <h3>좋아요 <v-icon icon="mdi-heart" class="like-icon" /></h3>
              <ul>
                <info-alert
                  v-if="likeList.length === 0"
                  title="'좋아요' 상품이 없습니다"
                />

                <li
                  v-for="(product, idx) in likeList.slice(startIndex, endIndex)"
                  :key="product.id"
                >
                  <mypage-product-banner
                    :product="product"
                    :is-heart="true"
                    @handle-click-like="
                      () => handleToggleHeart(product.id, idx)
                    "
                  />
                </li>
              </ul>
              <v-pagination
                v-model="likeCurrentPage"
                :length="likeTotalPage"
                :total-visible="isTablet ? 3 : 5"
                @click="handleChangeLikePage"
              >
              </v-pagination>
            </div>
          </v-window-item>
          <!-- 구매내역 -->
          <v-window-item value="구매내역">
            <ul>
              <info-alert
                v-if="purchaseList.length === 0"
                title="구매내역이 없습니다"
              />
              <li
                v-for="product in purchaseList.slice(startIndex, endIndex)"
                :key="product.id"
              >
                <mypage-product-banner :product="product" isStatus />
              </li>
            </ul>
            <v-pagination
              v-model="currentPage"
              :length="pageCount"
              :total-visible="isTablet ? 3 : 5"
              @click="handleChangePurchasePage"
            >
            </v-pagination>
          </v-window-item>
          <!-- 판매내역 -->
          <v-window-item value="판매내역">
            <ul>
              <info-alert
                v-if="sellList.length === 0"
                title="판매내역이 없습니다"
              />
              <li v-for="product in sellList" :key="product.id">
                <mypage-product-banner :product="product" isStatus isSeller />
              </li>
            </ul>
            <v-pagination
              v-model="sellingPage"
              :length="sellPageCount"
              :total-visible="isTablet ? 3 : 5"
              @click="handleGetSellHistory"
            >
            </v-pagination>
          </v-window-item>
        </v-window>
      </div>
    </v-card>
    <charge-point-modal
      v-model="showChargePointModal"
      @handle-cancel="showChargePointModal = false"
      @handle-return-point-result="(result) => (profile.point = result)"
      label="충전할 방울"
      isCharged
    />
    <charge-point-modal
      v-model="showExchangePointModal"
      @handle-cancel="showExchangePointModal = false"
      @handle-return-point-result="(result) => (profile.point = result)"
      label="환전할 방울"
    />
    <link-account-modal
      v-model="showLinkAccountModal"
      @handle-cancle-modal="showLinkAccountModal = !showLinkAccountModal"
      @handle-save-account="onHandleSaveAccount"
    />
  </content-layout>
</template>

<script setup>
import { ref, onBeforeMount, computed, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useDisplay } from "vuetify";
import { deleteApi, getApi } from "@/api/modules";
import ContentLayout from "@/layouts/ContentLayout.vue";
import basicProfile from "@/assets/image/basicProfile.jpeg";
import InfoAlert from "@/components/Alert/InfoAlert.vue";
import MypageProductBanner from "@/components/Banner/MypageProductBanner.vue";
import MiniButton from "@/components/Button/MiniButton.vue";
import ChargePointModal from "@/components/Modal/ChargePointModal.vue";
import LinkAccountModal from "@/components/Modal/LinkAccountModal.vue";

const tabList = ref([
  {
    id: 0,
    title: "마이페이지",
    icon: "mdi-account ",
  },
  {
    id: 1,
    title: "구매내역",
    icon: "mdi-archive",
  },
  {
    id: 2,
    title: "판매내역",
    icon: "mdi-currency-krw",
  },
]);
const route = useRoute();
const router = useRouter();
const display = useDisplay();
const tab = ref(tabList.value[route.params.index].title);
const showChargePointModal = ref(false);
const showExchangePointModal = ref(false);
const showLinkAccountModal = ref(false);
const isTablet = ref(display.smAndDown);
const profile = ref({
  rank: "mdi-umbrella-beach-outline",
});

const tabId = ref(0);

const perPage = ref(5); // 페이지당 상품 수
const currentPage = ref(1); // 현재 페이지
const startIndex = ref(0); // 상품 시작 인덱스
const endIndex = ref(perPage.value); // 상품 마지막 인덱스

// 좋아요
const likeList = ref([]);
const likeCurrentPage = ref(1);
const likeTotalPage = ref(5);

// 구매내역
const purchaseList = ref([]);

// 판매내역
const sellingPage = ref(0);
const sellList = ref([]);
const sellPageCount = ref();

onBeforeMount(async () => {
  tabId.value = Number(route.params.index);
  handleGetPurchaseHistory();
  handleGetSellHistory();
  handleGetUserData();
});

// 상품 수 계산
const productCount = computed(() => {
  return purchaseList.value.length;
});

// 페이지 수 계산
const pageCount = computed(() => {
  return Math.ceil(productCount.value / perPage.value);
});

watch(purchaseList, () => {
  currentPage.value = 1;
  handleChangePurchasePage();
});

// 마이페이지 탭 이동
const handleTabClick = (tabId) => {
  router.push(`/mypage/${tabId}`);
  handleGetHistory(tabId);
};

// 마이페이지 유저 정보
const handleGetUserData = async () => {
  const userData = await getApi({
    url: `/api/members/me?page=${
      likeCurrentPage.value - 1
    }&size=5&sort=id,desc `,
  });
  if (userData.userImg !== null) {
    profile.value.imagePath = userData.userImg;
  }
  profile.value.nickName = userData.nickname;
  profile.value.point = userData.point;
  profile.value.account = userData.account;
  likeList.value = userData.page.content.map((data) => {
    data.isLike = true;
    return data;
  });

  likeTotalPage.value = userData.page.totalPages;
};

// 좋아요 페이징
const handleChangeLikePage = async () => {
  try {
    const userData = await getApi({
      url: `/api/members/me?page=${
        likeCurrentPage.value - 1
      }&size=5&sort=id,desc `,
    });
    if (userData !== null) {
      likeList.value = userData.page.content.map((data) => {
        data.isLike = true;
        return data;
      });
      likeTotalPage.value = userData.page.totalPages;
    }
  } catch (error) {
    console.error(error);
  }
};

// 좋아요 취소
const handleToggleHeart = async (id, idx) => {
  if (likeList.value[idx].isLike) {
    await deleteApi({
      url: `/api/likes/${id}`,
    });
    likeList.value = [...likeList.value].filter((data) => data.id !== id);
  }
};

// 구매내역
const handleGetPurchaseHistory = async () => {
  try {
    const purchaseData = await getApi({ url: "/api/buying/history" });
    if (purchaseData !== null) {
      purchaseList.value = purchaseData;
    }
  } catch (error) {
    console.error(error);
  }
};

// 판매내역
const handleGetSellHistory = async () => {
  try {
    const sellData = await getApi({
      url: `/api/selling/history?page=${
        sellingPage.value - 1
      }&size=5&sort=transactionCreateDate,desc`,
    });
    if (sellData !== null) {
      sellList.value = sellData.historyResponseList;
      sellPageCount.value = sellData.pageCount;
    }
  } catch (error) {
    console.error(error);
  }
};

// 판매내역과 구매내역 불러오기
const handleGetHistory = async (id) => {
  tabId.value = id;
  if (id === 1) {
    await handleGetPurchaseHistory();
  } else if (id === 2) {
    await handleGetSellHistory();
  }
};

// 페이징 변경 핸들러
const handleChangePurchasePage = () => {
  startIndex.value = (currentPage.value - 1) * perPage.value;
  endIndex.value = Math.min(
    startIndex.value + perPage.value,
    productCount.value,
  );
  window.scrollTo({ top: 0 });
};
</script>

<style lang="scss" scoped>
.mypage {
  opacity: 1;
  .v-toolbar-title {
    text-align: center;
  }
  .mypage__content {
    display: flex;
    margin-top: 80px;
    @media (max-width: 960px) {
      flex-direction: column;
      .v-tabs {
        margin-bottom: 50px;
      }
    }
  }

  .v-toolbar {
    color: rgb(var(--v-theme-mainGray));
    background: none;
    margin-bottom: 20px;
    .v-toolbar-title {
      font-size: 30px;
      font-weight: 500;
    }
  }

  .v-toolbar,
  .v-tab {
    font-weight: 600;
  }
}
.v-window {
  flex: 1;
  .v-window-item {
    padding-left: 20px;
  }
  @media (max-width: 960px) {
    .v-window-item {
      padding: 0px;
    }
  }
}

.mypage__profile {
  display: flex;
  align-items: center;
  gap: 30px;
  margin: 20px 0 100px;
  .profile__img {
    display: flex;
    align-items: center;
    flex-direction: column;
    gap: 10px;

    img {
      width: 150px;
      aspect-ratio: 1 / 1;
      border-radius: 50%;
      object-fit: cover;
      object-position: center;
    }
  }

  .profile__edit {
    display: flex;
    align-items: center;
    .profile__info {
      margin-top: 20px;
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 0 20px;
      .profile__info-name {
        font-size: small;
        span {
          font-weight: 600;
          font-size: 18px;
        }
      }
      .profile__info-rank {
        display: flex;
        .rank-help {
          min-width: 10px;
          aspect-ratio: 1 / 1;
          border-radius: 50%;
          padding: 0;
          font-size: 12px;
        }
      }
    }
    .profile__point {
      padding: 0 20px;
      text-align: center;
      .v-icon {
        color: rgb(var(--v-theme-subBlue));
      }
    }
  }

  @media (max-width: 960px) {
    flex-direction: column;
  }
}
.mypage__like {
  margin-top: 60px;
  h3 {
    margin-bottom: 20px;
    font-weight: 600;
    font-size: 20px;
  }
  .like-icon {
    color: rgb(var(--v-theme-heartRed));
  }
}
.v-pagination {
  margin: 0 auto;
}
.profile_account {
  .profile_account-block {
    padding-bottom: 30px;
    display: flex;
    align-items: center;
    @media (max-width: 960px) {
      flex-direction: column;
      font-size: 8px;
      align-items: center;
    }
    .profile_account-text {
      font-weight: bold;
      padding-right: 10px;
    }
    .profile_account-info {
      border: 1px solid lightgray;
      padding: 7px;
    }
  }
}
</style>
