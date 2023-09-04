<template>
  <v-app-bar flat>
    <section>
      <!-- 헤더 왼쪽 타이틀 & 로고 -->
      <v-app-bar-title @click="() => $router.push('/')">
        <div class="logo">
          <img src="@/assets/image/logo-black.png" />
          <h2>슈룹</h2>
        </div>
      </v-app-bar-title>

      <v-form class="search__box">
        <input type="text" v-model="search" />
        <v-btn type="submit">
          <v-icon icon="mdi-magnify" />
        </v-btn>
      </v-form>

      <!-- 헤더 오른쪽 메뉴 -->

      <ul v-if="!isMobile" class="header__list">
        <li>
          <v-btn
            class="header__button-sell"
            @click="() => $router.push('/regist')"
          >
            <v-icon icon="mdi-currency-usd"></v-icon>
            <span>판매하기</span>
          </v-btn>
        </li>
        <li>
          <NotifyBellButton v-if="loginCheckStore.isLogin" />
        </li>
        <li class="header__account">
          <v-menu transition="scale-transition" class="tooltip-menu">
            <template v-slot:activator="{ props }">
              <v-btn v-bind="props">
                <v-icon icon="mdi-account-outline"></v-icon>
              </v-btn>
            </template>
            <ul class="tooltips">
              <li
                v-if="!loginCheckStore.isLogin"
                @click="() => $router.push('/login')"
              >
                로그인
              </li>
              <li v-if="loginCheckStore.isLogin" @click="logout">로그아웃</li>
              <li
                v-if="!loginCheckStore.isLogin"
                @click="() => $router.push('/signup')"
              >
                회원가입
              </li>
              <li
                v-if="loginCheckStore.isLogin"
                @click="() => $router.push('/mypage/0')"
              >
                마이페이지
              </li>
            </ul>
          </v-menu>
        </li>
      </ul>
    </section>
  </v-app-bar>
</template>

<script setup>
import { ref } from "vue";
import { useCheckLogin } from "@/store/useCheckLogin";
import { postApi } from "@/api/modules";
import { useDisplay } from "vuetify";
import NotifyBellButton from "@/components/Button/NotifyBellButton.vue";
import router from "@/router";

const loginCheckStore = useCheckLogin();
const search = ref("");

const display = useDisplay();
const isMobile = ref(display.smAndDown);

const logout = async () => {
  try {
    await postApi({
      url: "/logout",
    });
    router.go(0);
  } catch (error) {
    console.error(error);
  }
};
</script>

<style lang="scss" scoped>
header {
  overflow: visible;
  border-bottom: 1px solid rgb(var(--v-theme-mainGray), 0.2);
  section {
    display: flex;
    width: 70%;
    margin: 0 auto;
    align-items: center;
    justify-content: space-between;
    position: relative;
    .search__box {
      position: absolute;
      width: 40%;
      border: 2px solid rgb(var(--v-theme-mainGray));
      top: 50%;
      height: 40px;
      left: calc(50% - 20px);
      transform: translate(-50%, -50%);
      display: flex;
      justify-content: space-between;
      align-items: center;

      input {
        flex: 1;
        width: calc(100% - 50px);
        height: 100%;
        text-indent: 10px;
      }
      .v-btn {
        min-width: 50px;
        padding: 0px;
      }
    }
  }
  @media (max-width: 960px) {
    section {
      width: 100%;
      .search__box {
        left: 50%;
      }
    }
  }
}

/* 헤더 왼쪽 타이틀 & 로고 */
.v-app-bar-title {
  width: fit-content;
  cursor: pointer;
  .logo {
    width: 30px;
    aspect-ratio: 1 / 1;
    display: flex;
    flex-direction: column;
    align-items: end;
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
    cursor: pointer;
    text-align: center;
    color: #fff;
    font-weight: 600;
    padding: 10px 20px;
  }
}
</style>
