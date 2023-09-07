<template>
  <div>
    <main-title title="휴대전화 본인인증" />
    <v-card
      class="mx-auto pa-12 pb-8"
      max-width="448"
      rounded="lg"
      variant="plain"
    >
      <v-form v-model="isValid" @submit.prevent="handleSubmitAuth">
        <custom-text-input
          placeholder-text="전달 받은 인증 번호를 입력해주세요"
          v-model="phoneAuthNumber"
          :rules="[phoneAuthRule.required, phoneAuthRule.length]"
          icon="mdi-account-outline"
        />

        <v-btn
          block
          type="submit"
          class="mb-8 form__btn-login"
          size="large"
          variant="tonal"
          :disabled="!isValid"
        >
          인증
        </v-btn>
        <div v-show="!authResult" class="auth-result">
          인증 번호를 다시 확인해주세요
        </div>
      </v-form>
    </v-card>
  </div>
</template>

<script setup>
import { ref, onBeforeMount } from "vue";
import { useRouter } from "vue-router";
import { phoneAuthRule } from "@/components/Form/data/formRules";
import { postApi } from "@/api/modules";
import { useUserStore } from "@/store/modules";
import { useCookies } from "vue3-cookies";
import { MainTitle } from "@/components/Title";
import CustomTextInput from "@/components/Form/CustomTextInput.vue";

const { cookies } = useCookies();
const router = useRouter();
const phoneAuthNumber = ref("");
const authResult = ref(true);
const userStore = useUserStore();

const isValid = ref(false);
const handleSubmitAuth = async () => {
  try {
    await postApi({
      url: "/api/auth/sign-up",
      data: {
        loginId: userStore.getLoginId,
        nickname: userStore.getNickname,
        password: userStore.getPassword,
        phoneNumber: userStore.getPhoneNumber,
        agreeShroop: userStore.getAgreeShroop,
        agreePersonal: userStore.getAgreePersonal,
        agreeIdentify: userStore.getAgreeIdentify,
        uuid: cookies.get("uuid"),
        phoneAuthNumber: phoneAuthNumber.value,
      },
    });

    router.push("/login");
  } catch (error) {
    if (error.response.status === 400) {
      authResult.value = false;
    }
  }
};

onBeforeMount(() => {
  if (userStore.getLoginId === "") {
    router.push("/signup");
  }
});
</script>

<style lang="scss" scoped>
.form__btn-login {
  background: none !important;
  font-weight: 600;
  color: rgb(var(--v-theme-mainGray));
}
.guide_button {
  color: rgb(var(--v-theme-subBlue));
}

.auth-result {
  line-height: 12px;
  word-break: break-word;
  overflow-wrap: break-word;
  word-wrap: break-word;
  hyphens: auto;
  transition-duration: 150ms;
  margin: 10px;
  margin-bottom: 15px;
  color: rgb(var(--v-theme-heartRed));
}
</style>
