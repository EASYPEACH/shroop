<template>
  <div>
    <Title title="휴대전화 본인인증" />
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
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import Title from "@/components/Title/MainTitle.vue";
import CustomTextInput from "@/components/Form/CustomTextInput.vue";
import { phoneAuthRule } from "@/components/Form/data/formRules";
import { postApi } from "@/api/modules";

const route = useRoute();
const router = useRouter();
const phoneAuthNumber = ref("");
const authResult = ref(true);

const isValid = ref(false);
const handleSubmitAuth = async () => {
  try {
    const result = await postApi({
      url: "/api/auth/phone",
      data: {
        loginId: `${route.params.id}`,
        phoneAuthNumber: phoneAuthNumber.value,
      },
    });

    router.push("/");
  } catch (error) {
    if (error.response.status === 400) {
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
