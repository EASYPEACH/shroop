<template>
  <v-navigation-drawer
    v-model="notifyStore.isShowNotify"
    location="right"
    :width="isMobile ? 256 : 500"
    temporary
  >
    <div v-if="notifyListStore.hasNotify">
      <v-list
        density="compact"
        v-for="(notification, index) in notifyListStore.notifyList"
        :key="notification.id"
      >
        <div
          class="listitem"
          :class="{
            listitem__checked: notification.checked,
            listitem__hover: hovered[index],
          }"
          @click="() => handleChangeChecked(notification.id)"
          @mouseover="hovered[index] = true"
          @mouseout="hovered[index] = false"
        >
          <v-icon icon="mdi-bell" />
          <div>
            <p class="listitem__title">{{ notification.title }}</p>
            <p class="listitem__message">{{ notification.message }}</p>
          </div>
        </div>
        <v-divider></v-divider>
      </v-list>
    </div>
    <div v-else>
      <div class="listitem">
        <v-icon icon="mdi-bell-off" />
        <div>
          <p>현재 알림이 없습니다.</p>
        </div>
      </div>
      <v-divider></v-divider>
    </div>
  </v-navigation-drawer>
</template>

<script setup>
import { ref, onBeforeMount, watch } from "vue";
import { useRouter } from "vue-router";
import { useDisplay } from "vuetify";
import { useShowNotify } from "@/store/useShowNotify";
import { getApi, patchApi } from "@/api/modules";
import { useNotifyList } from "@/store/useNotifyList";

const display = useDisplay();
const isMobile = ref(display.mdAndDown);
const notifyListStore = useNotifyList();
const router = useRouter();
const notifyStore = useShowNotify();
const hovered = ref(new Array(notifyListStore.notifyList.length).fill(false));

const handleChangeChecked = async (id) => {
  try {
    const response = await patchApi({
      url: `/api/notifications/${id}`,
    });
    router.push(response.link);
  } catch (error) {
    console.log(error);
  }
};
</script>

<style lang="scss" scoped>
.v-navigation-drawer {
  .listitem {
    display: flex;
    align-items: center;
    padding: 10px;
    gap: 20px;
    .v-icon {
      color: rgb(var(--v-theme-mainGray));
    }
    .listitem__title {
      font-size: 13px;
    }
    .listitem__message {
      font-size: 15px;
    }
    &.listitem__checked {
      opacity: 0.5;
    }

    &.listitem__hover {
      font-weight: 700;
      cursor: pointer;
    }
  }
}
</style>
