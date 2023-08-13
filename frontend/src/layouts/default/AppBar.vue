<template>
  <v-app-bar flat>
    <!-- 헤더 왼쪽 타이틀 & 로고 -->
    <v-app-bar-title>
      <router-link to="/">
        <div class="logo">
          <img src="@/assets/image/logo.png" />
          <h2>슈룹</h2>
        </div>
      </router-link>
    </v-app-bar-title>
    <!-- 헤더 오른쪽 메뉴 -->
    <ul class="header__list">
      <li>
        <v-btn class="header__button-sell">
          <v-icon icon="mdi-currency-usd"></v-icon>
          <span>판매하기</span>
        </v-btn>
      </li>
      <li>
        <v-btn @click="handleShowNotification">
          <v-icon icon="mdi-bell-outline"></v-icon>
        </v-btn>
      </li>
      <li class="header__account">
        <v-menu transition="scale-transition" class="tooltip-menu">
          <template v-slot:activator="{ props }">
            <v-btn v-bind="props">
              <v-icon icon="mdi-account-outline"></v-icon>
            </v-btn>
          </template>
          <ul class="tooltips">
            <li>
              <router-link to="/login">로그인</router-link>
            </li>
            <li>
              <router-link to="/mypage">마이페이지</router-link>
            </li>
          </ul>
        </v-menu>
      </li>
    </ul>
    <!-- 알림 사이드바 -->
    <article :class="{ active: isShowNotification }">
      <div class="article__title">
        <v-btn class="article__button-close" @click="handleShowNotification">
          <v-icon icon="mdi-close"></v-icon>
        </v-btn>
        <p>알림</p>
      </div>
      <ul class="article__list-notification">
        <li v-for="item in 5" :key="item">
          <notification-card
            title="로그인 보안 알림"
            content="Lorem ipsum dolor sit amet, consectetur adipisicing elit. Doloribus et porro repellat! M"
          />
        </li>
      </ul>
    </article>
  </v-app-bar>
</template>

<script setup>
import { ref } from "vue";
import NotificationCard from "@/components/NotificationCard.vue";

const isShowTooltips = ref(false);
const isShowNotification = ref(false);
const handleShowTooltips = () => {
  isShowTooltips.value = !isShowTooltips.value;
};
const handleShowNotification = () => {
  isShowNotification.value = !isShowNotification.value;
  isShowTooltips.value = false;
};
</script>

<style lang="scss" scoped>
/* 헤더 왼쪽 타이틀 & 로고 */
header {
  overflow: visible;
}
.logo {
  width: 30px;
  aspect-ratio: 1 / 1;
  display: flex;
  flex-direction: column;
  align-items: end;
  justify-content: center;
  transform: translateX(20px);
  img {
    width: 15px;
  }
  h2 {
    margin-top: -4px;
    color: rgb(var(--v-theme-mainGray));
    font-weight: 600;
  }
}
/* 헤더 오른쪽 메뉴 */
.header__list {
  display: flex;
  align-items: center;
  gap: 5px;
  padding: 0 10px;
  > li {
    display: flex;
    align-items: center;
    button {
      min-width: fit-content;
      padding: 0;
      padding: 5px;
    }
    span {
      white-space: nowrap;
    }
  }
  .mdi {
    font-size: 25px;
    width: 100%;
  }
}
.header__button-sell {
  font-size: 17px;
}
.header__account {
  position: relative;
}

.tooltips {
  background: rgb(var(--v-theme-mainGray));
  border-radius: 10px;
  > li {
    width: 100px;
    padding: 5px;
    text-align: center;
    color: #fff;
    font-weight: 600;
  }
}

/* 알림 사이드 바 */

article {
  position: fixed;
  top: 0;
  right: -120%;
  width: 400px;
  height: 100vh;
  background: #fff;
  padding: 0 15px 30px;
  overflow-y: scroll;
  overflow-x: hidden;
  transition: 0.5s all;

  &::-webkit-scrollbar {
    display: none;
  }
  &.active {
    right: 0;
  }

  .article__title {
    position: sticky;
    top: 0;
    left: 0;
    display: flex;
    align-items: center;
    padding: 10px 0;
    background: #fff;
    border-bottom: 1px solid rgba(var(--v-theme-mainGray), 0.2);
    z-index: 20;
    width: 100%;

    .article__button-close {
      min-width: 50px;
      aspect-ratio: 1 / 1;
      font-size: 25px;
      transform: translateX(-14px);
    }
    p {
      font-weight: 600;
      transform: translateX(-14px);
    }
  }
  .article__list-notification {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }
}
</style>
