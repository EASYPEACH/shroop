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
          <router-link to="/regist">
            <v-icon icon="mdi-currency-usd"></v-icon>
            <span>판매하기</span>
          </router-link>
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
              <router-link to="/signup">회원가입</router-link>
            </li>
            <li>
              <router-link to="/mypage/0">마이페이지</router-link>
            </li>
          </ul>
        </v-menu>
      </li>
    </ul>
  </v-app-bar>
</template>

<script setup>
import { useShowNotify } from "@/store/useShowNotify";

const notifyStore = useShowNotify();

const handleShowNotification = () => {
  notifyStore.setIsShowNotify();
  console.log(notifyStore.isShowNotify);
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
      padding: 5px;
    }
    span {
      white-space: nowrap;
    }
  }
  .mdi {
    font-size: 25px;
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
    text-align: center;
    color: #fff;
    font-weight: 600;
    a {
      width: 100%;
      padding: 5px;
      display: inline-block;
    }
  }
}
</style>
