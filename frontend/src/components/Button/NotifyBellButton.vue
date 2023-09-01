<template>
  <v-btn @click="handleShowNotification">
    <v-icon v-if="!hasNewNotification" icon="mdi-bell-outline"></v-icon>
    <v-icon v-else icon="mdi-bell-badge-outline" color="heartRed"></v-icon>
  </v-btn>
</template>

<script setup>
import { ref, onBeforeMount } from "vue";
import { useRouter } from "vue-router";
import { getApi } from "@/api/modules";
import { useShowNotify } from "@/store/useShowNotify";
import { useNotifyList } from "@/store/useNotifyList";

const hasNewNotification = ref(false);
const notifyStore = useShowNotify();
const notifyListStore = useNotifyList();
const router = useRouter();
router.beforeEach(async () => await handleGetNotification());
onBeforeMount(async () => await handleGetNotification());

const handleGetNotification = async () => {
  try {
    const response = await getApi({
      url: `/api/notifications`,
    });

    const responseSize = response.length;

    if (responseSize > 0) {
      notifyListStore.setHasNotify(true);
      notifyListStore.setNotifyList(response);
      const getNotCheckedList = notifyListStore.getNotifyChecked(
        notifyListStore.notifyList,
      );
      if (getNotCheckedList.length > 0) {
        hasNewNotification.value = true;
      }
    }
  } catch (error) {
    console.error(error);
  }
};

const handleShowNotification = () => {
  notifyStore.setIsShowNotify();
};
</script>

<style lang="scss" scoped>
.v-btn {
  min-width: fit-content;
  padding: 5px;
}

.mdi {
  font-size: 25px;
}
</style>
