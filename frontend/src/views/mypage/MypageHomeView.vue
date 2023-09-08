<template>
  <content-layout>
    <section>
      <div class="mypage__profile">
        <div class="profile__img">
          <img :src="profile.imagePath ? profile.imagePath : basicProfile" />
        </div>

        <div class="profile__infoBox">
          <aside class="profile__infoBox-details">
            <div class="profile__info-rank">
              <v-icon :icon="profile.rank" class="profile__name-rank" />
              <v-btn variant="plain" class="rank-help">
                <v-icon icon="mdi-help" />
                <v-tooltip activator="parent" location="end">
                  <p>슈룹 안심거래 등급</p>
                  <ul>
                    <li>
                      <v-icon icon="mdi-umbrella-beach-outline" />골프우산: 상
                    </li>
                    <li>
                      <v-icon icon="mdi-umbrella-closed-variant" />접이식우산:
                      중
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
          </aside>
          <aside class="profile__account-box">
            <div class="profile__account-points">
              <div>
                {{ profile.point }}
                <v-icon icon="mdi-water" />
              </div>
              <div class="account__buttons">
                <mini-button
                  variant="outlined"
                  text="충전"
                  @click="showChargePointModal = true"
                />
                <mini-button
                  variant="outlined"
                  text="환전"
                  @click="showExchangePointModal = true"
                />
              </div>
            </div>
            <v-divider :vertical="true" />
            <div>
              <div
                class="profile__account-link"
                v-if="profile.account !== null"
              >
                <h4>연결 계좌</h4>
                <p>
                  {{ profile.account }}
                </p>
              </div>
              <v-btn
                color="subBlue"
                variant="outlined"
                class="account__buttons-link"
                @click="showLinkAccountModal = true"
                >계좌 연동하기</v-btn
              >
            </div>
          </aside>
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
              @handle-click-like="() => handleToggleHeart(product.id, idx)"
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
    </section>

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
      @handle-save-account="handleSaveAccount"
    />
  </content-layout>
</template>

<script setup>
import { ref, onBeforeMount } from "vue";
import { useDisplay } from "vuetify";
import { deleteApi, getApi, postApi } from "@/api/modules";
import ContentLayout from "@/layouts/ContentLayout.vue";
import basicProfile from "@/assets/image/basicProfile.jpeg";

import { InfoAlert } from "@/components/Alert";
import { MypageProductBanner } from "@/components/Banner";
import { MiniButton } from "@/components/Button";
import { ChargePointModal, LinkAccountModal } from "@/components/Modal";

const display = useDisplay();
const showChargePointModal = ref(false);
const showExchangePointModal = ref(false);
const showLinkAccountModal = ref(false);
const isTablet = ref(display.smAndDown);
const profile = ref({
  rank: "mdi-umbrella-beach-outline",
});

// 좋아요
const likeList = ref([]);
const likeCurrentPage = ref(1);
const likeTotalPage = ref(5);

const perPage = ref(5); // 페이지당 상품 수
const startIndex = ref(0); // 상품 시작 인덱스
const endIndex = ref(perPage.value); // 상품 마지막 인덱스

onBeforeMount(async () => {
  await handleGetUserData();
});

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

// 계좌저장
const handleSaveAccount = async () => {
  try {
    const message = await postApi({
      url: "/api/bank/linking",
    });
    alert(message);
  } catch (error) {
    console.error(error);
  }
};
</script>

<style lang="scss" scoped>
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

  .profile__infoBox {
    display: flex;
    align-items: center;

    @media (max-width: 960px) {
      flex-direction: column;
      gap: 20px;
    }

    aside {
      display: flex;
      align-items: center;
    }

    @media (max-width: 960px) {
      flex-direction: column;
      gap: 20px;
    }
    .profile__infoBox-details {
      margin-top: 20px;
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 0 20px;
      @media (max-width: 960px) {
        flex-direction: column;
        gap: 20px;
      }

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
  }

  @media (max-width: 960px) {
    flex-direction: column;
  }
}

.mypage__like,
.mypage__personal {
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

.profile__account-box {
  text-align: center;
  display: flex;
  gap: 20px;
  align-items: center;

  .profile__account-points {
    text-align: center;
    .v-icon {
      color: rgb(var(--v-theme-subBlue));
    }
    .account__buttons {
      display: flex;
      gap: 10px;
    }
  }

  .profile__account-link {
    .account__buttons-link {
      padding: 10px 0;
    }
    h4 {
      font-weight: 600;
    }
    p {
      background: rgb(var(--v-theme-subBlue));
      margin: 10px 0;
      padding: 5px;
      color: #fff;
      border-radius: 5px;
    }
  }
}
</style>
