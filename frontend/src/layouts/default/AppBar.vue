<template>
  <v-app-bar>
    <section>
      <!-- 헤더 왼쪽 타이틀 & 로고 -->
      <v-app-bar-title @click="() => $router.push('/')">
        <div class="logo">
          <img src="@/assets/image/logo-black.png" />
          <h2>슈룹</h2>
        </div>
      </v-app-bar-title>

      <v-form class="search__box" @submit.prevent="handleSearchProduct">
        <input type="text" v-model="search" @input="handleSearchInput" />
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
                @click="() => $router.push('/mypage/home')"
              >
                마이페이지
              </li>
            </ul>
          </v-menu>
        </li>
      </ul>
      <NotifyBellButton
        v-else-if="isMobile && loginCheckStore.isLogin"
        style="margin-right: 40px"
      />
    </section>
  </v-app-bar>
</template>

<script setup>
import { ref, onBeforeMount } from "vue";
import { useCheckLogin, useSearchProduct } from "@/store/modules";
import { postApi } from "@/api/modules";
import { useDisplay } from "vuetify";
import { useRoute } from "vue-router";
import { NotifyBellButton } from "@/components/Button";
import router from "@/router";

const route = useRoute();
const searchProductStore = useSearchProduct();
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

const handleSearchProduct = async () => {
  searchProductStore.setSearchTitle(search.value.trim());
  search.value = search.value.trim();
  await router.push({
    name: "Products",
    query: {
      title: search.value,
      categotyId: route.query.categoryId,
      isSelling: route.query.isSelling,
      page: route.query.page,
    },
  });
};

const handleSearchInput = () => {
  searchProductStore.setSearchTitle(search.value);
};

onBeforeMount(() => {
  searchProductStore.setSearchTitle(route.query.title);
  search.value = searchProductStore.searchTitle;
});

router.afterEach((to) => {
  if (to.name === "Home") {
    searchProductStore.setSearchTitle("");
    search.value = searchProductStore.searchTitle;
  }
});
</script>

<style lang="scss" scoped>
header {
  overflow: visible;
  section {
    display: flex;
    width: 60%;
    margin: 0 auto;
    align-items: center;
    justify-content: space-between;
    position: relative;
    .search__box {
      position: absolute;
      width: 40%;
      border: 1px solid rgb(var(--v-theme-mainGray));
      border-radius: 5px;
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
        left: 53%;
      }
    }
  }
}

/* 헤더 왼쪽 타이틀 & 로고 */
.v-app-bar-title {
  max-width: 100px;
  cursor: pointer;
  .logo {
    width: 50px;
    aspect-ratio: 1 / 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    transform: translateX(20px);
    margin-top: 7px;
    @media (max-width: 960px) {
      transform: translateX(10px);
    }
    img {
      align-self: flex-end;
      margin-right: 6px;
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
