<template>
  <content-layout>
    <v-card variant="plain" class="mypage">
      <v-toolbar>
        <v-toolbar-title>{{ tab }}</v-toolbar-title>
      </v-toolbar>
      <div class="myapge__content">
        <v-tabs
          v-model="tab"
          :direction="isTablet ? 'horizontal' : 'vertical'"
          color="subBlue"
        >
          <v-tab
            v-for="t in tabList"
            :key="t.id"
            :value="t.title"
            @click="
              () => {
                $router.push(`/mypage/${t.id}`);
                handleHistory(t.id);
              }
            "
          >
            <v-icon start> {{ t.icon }} </v-icon>
            {{ t.title }}
          </v-tab>
        </v-tabs>
        <v-window v-model="tab">
          <v-window-item value="마이페이지">
            <div class="mypage__profile">
              <div class="profile__info">
                <img :src="profile.imagePath" class="profile__info-img" />
                <div>
                  <div>
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
                  <div class="profile__info-name">
                    {{ profile.nickName }} 님
                  </div>
                  <mini-button
                    text="프로필수정"
                    @click="() => $router.push(`/profileEdit/${0}`)"
                  />
                </div>
              </div>
              <div class="profile__point">
                <div class="profile__point-count">
                  {{ profile.point }} <v-icon icon="mdi-water" class="drop" />
                </div>
                <mini-button text="충전" @click="showChargePointModal = true" />
              </div>
            </div>
            <!-- <div class="mypage__like">
              <h3>좋아요 <v-icon icon="mdi-heart" class="like-icon" /></h3>
              <ul>
                <info-alert
                  v-if="
                    productDummyList.filter((list) => list.like).length === 0
                  "
                  title="'좋아요' 상품이 없습니다"
                />
                <li v-for="product in productDummyList" :key="product.id">
                  <mypage-product-banner
                    :product="product"
                    :is-heart="true"
                    @handle-click-like="() => handleToggleHeart(product.id)"
                  />
                </li>
              </ul>
            </div> -->
          </v-window-item>
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
          </v-window-item>
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
          </v-window-item>
        </v-window>
      </div>
    </v-card>
    <charge-point-modal
      v-model="showChargePointModal"
      @handle-cancle="showChargePointModal = false"
    />
    <div class="mt-5">
      <v-pagination
        v-if="tabId === 1"
        v-model="currentPage"
        :length="pageCount"
        :total-visible="5"
        @click="handleChangePage"
      >
      </v-pagination>
      <v-pagination
        v-else-if="tabId === 2"
        v-model="sellingPage"
        :length="sellPageCount"
        :total-visible="5"
        @click="handleSellHistory"
      >
      </v-pagination>
    </div>
  </content-layout>
</template>

<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";
import { useDisplay } from "vuetify";
import ContentLayout from "@/layouts/ContentLayout.vue";
import basicProfile from "@/assets/image/basicProfile.jpeg";
import InfoAlert from "@/components/Alert/InfoAlert.vue";
import MypageProductBanner from "@/components/Banner/MypageProductBanner.vue";
import MiniButton from "@/components/Button/MiniButton.vue";
import ChargePointModal from "@/components/Modal/ChargePointModal.vue";
import DUMMY from "@/consts/dummy";
import { getApi } from "@/api/modules";
import { onBeforeMount } from "vue";
import { computed } from "vue";
import { watch } from "vue";

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
const router = useRoute();
const productDummyList = ref(DUMMY);
const display = useDisplay();
const tab = ref(tabList.value[router.params.index].title);
const showChargePointModal = ref(false);
const isTablet = ref(display.smAndDown);
const profile = ref({
  imagePath: basicProfile,
  nickName: "김뿅뿅",
  rank: "mdi-umbrella-beach-outline",
  point: 20000,
});

const purchaseList = ref([]);
const sellList = ref([]);

const tabId = ref(0);

onBeforeMount(async () => {
  tabId.value = Number(router.params.index);
  handlePurchaseHistory();
  handleSellHistory();
});

// const handleToggleHeart = (id) => {
//   productDummyList.value = productDummyList.value
//     .map((item) => {
//       if (item.id === id) {
//         item.like = false;
//       }
//       return item;
//     })
//     .filter((item) => item.id != id);
// };

const fetchData = async (url) => {
  try {
    const response = await getApi({ url });
    return response;
  } catch (error) {
    console.log(error);
    return null;
  }
};

const handlePurchaseHistory = async () => {
  try {
    const purchaseData = await fetchData("/api/buying/history");
    if (purchaseData !== null) {
      purchaseList.value = purchaseData;
    }
  } catch (error) {
    console.log(error);
  }
};

const handleSellHistory = async () => {
  try {
    const sellData = await fetchData(
      `/api/selling/history?page=${sellingPage.value}&size=5`,
    );
    if (sellData !== null) {
      console.log(sellData);
      sellList.value = sellData.historyResponseList;
      sellPageCount.value = sellData.pageCount - 1;
    }
  } catch (error) {
    console.log(error);
  }
};

const handleHistory = async (id) => {
  tabId.value = id;
  if (id === 1) {
    await handlePurchaseHistory();
  } else if (id === 2) {
    await handleSellHistory();
  }
};

const perPage = ref(5); // 페이지당 상품 수
const currentPage = ref(1); // 현재 페이지

const sellingPage = ref(1);
const productCount = computed(() => {
  return purchaseList.value.length;
});

const pageCount = computed(() => {
  return Math.ceil(productCount.value / perPage.value);
});

const sellPageCount = ref();
const startIndex = ref(0); // 상품 시작 인덱스
const endIndex = ref(perPage.value); // 상품 마지막 인덱스

const handleChangePage = () => {
  startIndex.value = (currentPage.value - 1) * perPage.value;
  endIndex.value = Math.min(
    startIndex.value + perPage.value,
    productCount.value,
  );
  window.scrollTo({ top: 0 });
};

watch(purchaseList, () => {
  currentPage.value = 1;
  handleChangePage();
});
</script>

<style lang="scss" scoped>
.mypage {
  opacity: 1;
  .myapge__content {
    display: flex;
    @media (max-width: 960px) {
      flex-direction: column;
      .v-tabs {
        margin-bottom: 30px;
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
}

.mypage__profile {
  display: flex;
  justify-content: space-between;
  align-items: center;

  .profile__info {
    display: flex;
    gap: 20px;
    .profile__info-img {
      width: 100px;
      border-radius: 50%;
    }
    > div {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: space-between;
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
    display: flex;
    flex-direction: column;
    align-items: center;
    .drop {
      color: rgb(var(--v-theme-subBlue));
    }
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
</style>
