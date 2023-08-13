<template>
  <div>
    <Title title="로그인" />
    <v-card
      class="mx-auto pa-12 pb-8"
      elevation="8"
      max-width="448"
      rounded="lg"
    >
      <custom-text-input
        label="아이디"
        placeholder-text="아이디를 입력해주세요"
        v-model="id"
        :rules="[idRule.required, idRule.min, idRule.check]"
        icon="mdi-account-outline"
      />
      <password-input
        :visible="visible"
        v-model="password"
        :rules="[passwordRule.required, passwordRule.min, passwordRule.check]"
        isNeedSearchState
      />
      <v-btn block class="mb-8 form__btn-login" size="large" variant="tonal">
        로그인
      </v-btn>
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
    </v-card>
  </div>
</template>

<script setup>
  import { ref } from "vue";
  import Title from "@/components/Title.vue";
  import CustomTextInput from "@/components/Form/CustomTextInput.vue";
  import PasswordInput from "@/components/Form/PasswordInput.vue";
  import { idRule, passwordRule } from "@/components/Form/data/formRules";

  const visible = ref(false);
  const id = ref("");
  const password = ref("");
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
</style>
