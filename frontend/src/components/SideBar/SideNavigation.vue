<template>
  <div>
    <v-card>
      <v-layout>
        <v-navigation-drawer permanent location="right">
          <v-list density="compact" nav>
            <v-list-item
              @click="() => navigationAction('/regist')"
              prepend-icon="mdi-currency-usd"
              title="판매하기"
              value="판매하기"
            ></v-list-item>
            <v-list-item
              @click="() => navigationAction('/mypage/0')"
              v-if="loginCheckStore.isLogin"
              prepend-icon="mdi-account"
              title="마이페이지"
              value="마이페이지"
            ></v-list-item>
            <v-list-item
              @click="() => navigationAction('/login')"
              v-if="!loginCheckStore.isLogin"
              prepend-icon="mdi-login-variant"
              title="로그인"
              value="로그인"
            ></v-list-item>
            <v-list-item
              @click="logout"
              v-if="loginCheckStore.isLogin"
              prepend-icon="mdi-export"
              title="로그아웃"
              value="로그아웃"
            ></v-list-item>
            <v-list-item
              @click="() => navigationAction('/signup')"
              v-if="!loginCheckStore.isLogin"
              prepend-icon="mdi-account-edit-outline"
              title="회원가입"
              value="회원가입"
            ></v-list-item>
          </v-list>
        </v-navigation-drawer>
        <v-main style="height: 250px"></v-main>
      </v-layout>
    </v-card>
    <div class="dim" @click="() => mobileNavStore.setIsMobileNav(false)" />
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { postApi } from "@/api/modules";
import { useCheckLogin, useMobileNav } from "@/store/modules";

const router = useRouter();
const loginCheckStore = useCheckLogin();
const mobileNavStore = useMobileNav();

const navigationAction = (path) => {
  router.push(path);
  mobileNavStore.setIsMobileNav(false);
};

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
.v-card {
  position: fixed;
  top: 0;
  right: 0;
  height: 100%;
  z-index: 2000;
  padding-top: 50px;
  border-radius: 0;
}

.dim {
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1500;
}
</style>
