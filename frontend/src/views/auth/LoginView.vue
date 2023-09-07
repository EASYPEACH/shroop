<template>
  <div>
    <main-title title="로그인" />
    <v-card
      class="mx-auto pa-12 pb-8"
      max-width="448"
      rounded="lg"
      variant="plain"
    >
      <v-form v-model="isValid" @submit.prevent="handleSubmitLogin">
        <custom-text-input
          label="아이디"
          placeholder-text="아이디를 입력해주세요"
          v-model="id"
          :rules="[idRule.required]"
          icon="mdi-account-outline"
        />
        <password-input
          :visible="visible"
          @toggle-visible="visible = !visible"
          v-model="password"
          :rules="[passwordRule.required]"
        />
        <v-btn
          block
          type="submit"
          class="mb-8 form__btn-login"
          size="large"
          variant="tonal"
          :disabled="!isValid"
        >
          로그인
        </v-btn>
        <div v-if="!authResult" class="auth-result">
          아이디 및 비밀번호를 다시 확인해주세요
        </div>
        <v-card-text class="text-center">
          슈룹회원이 아니신가요?
          <router-link
            to="/signup"
            class="guide_button"
            rel="noopener noreferrer"
          >
            회원가입<v-icon icon="mdi-chevron-right"></v-icon>
          </router-link>
        </v-card-text>
      </v-form>
    </v-card>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { postApi } from "@/api/modules";
import { idRule, passwordRule } from "@/components/Form/data/formRules";
import { CustomTextInput, PasswordInput } from "@/components/Form";
import { MainTitle } from "@/components/Title";

const router = useRouter();
const visible = ref(false);
const id = ref("");
const password = ref("");
const isValid = ref(false);
const authResult = ref(true);

// 로그인 핸들러
const handleSubmitLogin = async () => {
  try {
    await postApi({
      url: "/api/auth/sign-in",
      data: {
        loginId: id.value,
        password: password.value,
      },
    });
    router.push("/");
  } catch (error) {
    if (error.response.status === 403) {
      authResult.value = false;
    }
  }
};
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
