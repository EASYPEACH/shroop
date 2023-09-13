<template>
  <content-layout>
    <v-card variant="plain" class="mypage">
      <div class="mypage__content">
        <v-tabs
          v-model="mypageTabStore.tabIndex"
          :direction="isTablet ? 'horizontal' : 'vertical'"
          :density="isTablet ? 'compact' : 'default'"
          :show-arrows="isTablet ? true : false"
          :stacked="isTablet ? true : false"
          color="#166678"
        >
          <v-tab
            v-for="t in tabList"
            :key="t.title"
            :value="t.title"
            @click="() => handleTabClick(t.path, t.id)"
          >
            <v-icon start> {{ t.icon }} </v-icon>
            {{ t.title }}
          </v-tab>
        </v-tabs>
        <router-view />
      </div>
    </v-card>
  </content-layout>
</template>

<script setup>
import { onBeforeMount, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useDisplay } from "vuetify";
import ContentLayout from "@/layouts/ContentLayout.vue";
import { useMypageTab } from "@/store/modules";

const tabList = ref([
  {
    id: 0,
    title: "마이페이지",
    path: "home",
    icon: "mdi-account ",
  },
  {
    id: 1,
    title: "구매내역",
    path: "purchaseList",
    icon: "mdi-archive",
  },
  {
    id: 2,
    title: "판매내역",
    path: "sellList",
    icon: "mdi-currency-krw",
  },
  {
    id: 3,
    title: "개인정보",
    path: "profileEdit",
    icon: "mdi-shield-lock",
  },
]);
const route = useRoute();
const router = useRouter();
const display = useDisplay();
const mypageTabStore = useMypageTab();

const isTablet = ref(display.smAndDown);

onBeforeMount(() => {
  mypageTabStore.setTabIndex(
    tabList.value.findIndex((list) => list.path === route.path.split("/")[2]),
  );
});

router.afterEach(() => {
  mypageTabStore.setTabIndex(
    tabList.value.findIndex((list) => list.path === route.path.split("/")[2]),
  );
});

const handleTabClick = (path) => {
  router.push(`/mypage/${path}`);
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
    .v-tabs {
      border-right: 1px solid rgba(0, 0, 0, 0.2);
      padding-right: 20px;
    }

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
        color: rgb(var(--v-theme-subGreen));
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

.mypage__personal {
  margin: 10px 50px;
  max-width: 500px;
  .delete {
    margin-top: 80px;
  }
}
</style>
