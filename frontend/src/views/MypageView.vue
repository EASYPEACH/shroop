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
          <v-tab value="마이페이지">
            <v-icon start> mdi-account </v-icon>
            마이페이지
          </v-tab>
          <v-tab value="구매내역">
            <v-icon start> mdi-archive </v-icon>
            구매내역
          </v-tab>
          <v-tab value="판매내역">
            <v-icon start> mdi-currency-krw</v-icon>
            판매내역
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
            <div class="mypage__like">
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
            </div>
          </v-window-item>
          <v-window-item value="구매내역">
            <ul>
              <info-alert
                v-if="purchaseList.length === 0"
                title="구매내역이 없습니다"
              />
              <li v-for="product in purchaseList" :key="product.id">
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

const tabList = ["마이페이지", "구매내역", "판매내역"];
const router = useRoute();
const productDummyList = ref(DUMMY);
const display = useDisplay();
const tab = ref(tabList[router.params.index]);
const showChargePointModal = ref(false);
const isTablet = ref(display.smAndDown);
const profile = ref({
  imagePath: basicProfile,
  nickName: "김뿅뿅",
  rank: "mdi-umbrella-beach-outline",
  point: 20000,
});

const purchaseList = ref([
  {
    id: 1,
    title: "아이폰 14 pro",
    status: "반품신청",
    thumb: "https://cdn.vuetifyjs.com/images/john.jpg",
    createDate: `2023-08-10`,
    price: 4000,
  },
  {
    id: 2,
    title: "아이폰 14 pro",
    status: "배송중",
    thumb: "https://cdn.vuetifyjs.com/images/john.jpg",
    createDate: `2023-08-10`,
    price: 4000,
  },
  {
    id: 3,
    title: "아이폰 14 pro",
    status: "구매신청",
    thumb: "https://cdn.vuetifyjs.com/images/john.jpg",
    createDate: `2023-08-10`,
    price: 4000,
  },
  {
    id: 4,
    title: "아이폰 14 pro",
    status: "반품완료",
    thumb: "https://cdn.vuetifyjs.com/images/john.jpg",
    price: 4000,
  },
]);
const sellList = ref([
  {
    id: 1,
    title: "아이폰 14 pro",
    status: "판매중",
    thumb: "https://cdn.vuetifyjs.com/images/john.jpg",
    createDate: `2023-08-10`,
    price: 4000,
  },
  {
    id: 2,
    title: "아이폰 14 pro",
    status: "반품신청",
    thumb: "https://cdn.vuetifyjs.com/images/john.jpg",
    createDate: `2023-08-10`,
    price: 4000,
  },
  {
    id: 3,
    title: "아이폰 14 pro",
    status: "배송중",
    thumb: "https://cdn.vuetifyjs.com/images/john.jpg",
    createDate: `2023-08-10`,
    price: 4000,
  },
  {
    id: 4,
    title: "아이폰 14 pro",
    status: "구매신청",
    thumb: "https://cdn.vuetifyjs.com/images/john.jpg",
    createDate: `2023-08-10`,
    price: 4000,
  },
]);
const handleToggleHeart = (id) => {
  productDummyList.value = productDummyList.value
    .map((item) => {
      if (item.id === id) {
        item.like = false;
      }
      return item;
    })
    .filter((item) => item.id != id);
};
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
