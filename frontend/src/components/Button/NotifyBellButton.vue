<template>
  <v-btn variant="text" class="text-none" @click="handleShowNotification">
    <v-badge
      v-if="notCheckednotifyCount"
      :content="notCheckednotifyCount"
      color="error"
    >
      <v-icon>mdi-bell-outline</v-icon>
    </v-badge>
    <v-icon v-else>mdi-bell-outline</v-icon>
  </v-btn>
</template>

<script setup>
import { ref, onBeforeMount } from "vue";
import { useRouter } from "vue-router";
import { getApi } from "@/api/modules";
import { useShowNotify, useNotifyList } from "@/store/modules";

const notifyStore = useShowNotify();
const notifyListStore = useNotifyList();
const router = useRouter();
const notCheckednotifyCount = ref();
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
      const getNotCheckedList = notifyListStore.getNotifyChecked;
      notCheckednotifyCount.value = getNotCheckedList.length;
    }
  } catch (error) {
    console.error(error);
  }
};

const handleShowNotification = () => {
  notifyStore.setIsShowNotify(!notifyStore.isShowNotify);
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
