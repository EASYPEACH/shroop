<template>
  <div>
    <main-title title="회원가입" />
    <v-card
      class="mx-auto pa-12 pb-8"
      max-width="540"
      rounded="lg"
      variant="text"
    >
      <v-form ref="form" v-model="isValid" @submit.prevent="submit">
        <custom-text-input
          label="아이디"
          placeholder-text="아이디를 입력해주세요"
          v-model="id"
          :rules="[idRule.required, idRule.min, idRule.check]"
          icon="mdi-account-outline"
          :hide-details="isDuplId"
          :blur="checkDuplicateID"
        />
        <div v-if="isDuplId" class="duplicate-message">
          <div>중복된 아이디 입니다</div>
        </div>
        <password-input
          title="패스워드"
          :visible="visible"
          @toggle-visible="visible = !visible"
          v-model="password"
          :rules="[passwordRule.required, passwordRule.min, passwordRule.check]"
        />
        <custom-text-input
          label="닉네임"
          placeholder-text="닉네임을 입력해주세요"
          v-model="nickname"
          :rules="[nickNameRule.required, nickNameRule.min, nickNameRule.check]"
          :hide-details="isDuplNickname"
          :blur="checkDuplicateNickname"
        />
        <div v-if="isDuplNickname" class="duplicate-message">
          <div>중복된 닉네임 입니다</div>
        </div>
        <div>
          <div class="text-subtitle-1 text-medium-emphasis">휴대전화번호</div>
          <div class="sigupForm__block-phoneNumber">
            <PhoneInput
              v-model="phoneNumber"
              :rules="[phoneNumberRule.required, phoneNumberRule.check]"
              :hide-details="isDuplPhone"
              :blur="checkDuplicatePhone"
            />
          </div>
          <div v-if="isDuplPhone" class="duplicate-message">
            <div>중복된 휴대전화번호 입니다</div>
          </div>
        </div>
        <agreement-check-box
          v-for="item in usageRules"
          :key="item.id"
          v-model="agreement[item.id]"
          @show-dialog="dialog[item.id] = true"
          :agreement-statement="item.title"
          :rules="[agreeRule.required]"
        />
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            :disabled="!isValid || isDuplId || isDuplNickname || isDuplPhone"
            type="submit"
            color="subGreen"
          >
            다음
          </v-btn>
        </v-card-actions>
      </v-form>
      <agreement-dialog
        v-for="item in usageRules"
        :key="item.id"
        v-model="dialog[item.id]"
        @change-to-agree="() => changeToAgree(item.id)"
        @change-to-disagree="() => changeToDisagree(item.id)"
        :title="item.title"
        :content-text="item.contentText"
      />
    </v-card>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { postApi } from "@/api/modules";
import { useUserStore } from "@/store/modules";
import { useCookies } from "vue3-cookies";
import { AGREE } from "@/consts/agree";

import { MainTitle } from "@/components/Title";
import {
  AgreementCheckBox,
  CustomTextInput,
  PasswordInput,
  PhoneInput,
  AgreementDialog,
} from "@/components/Form";
import usageRules from "@/components/Form/data/usageRules";
import {
  idRule,
  passwordRule,
  nickNameRule,
  agreeRule,
  phoneNumberRule,
} from "@/components/Form/data/formRules";

const { cookies } = useCookies();
const router = useRouter();

const id = ref("");
const password = ref("");
const phoneNumber = ref("");
const nickname = ref("");

const visible = ref(false);
const isValid = ref(false);
const dialog = ref({
  [AGREE.SHROOP]: false,
  [AGREE.PERSONAL]: false,
  [AGREE.IDENTIFY]: false,
});
const agreement = ref({
  [AGREE.SHROOP]: false,
  [AGREE.PERSONAL]: false,
  [AGREE.IDENTIFY]: false,
});
const isDuplId = ref(false);
const isDuplNickname = ref(false);
const isDuplPhone = ref(false);
const userStore = useUserStore();

const changeToAgree = (id) => {
  dialog.value[id] = false;
  agreement.value[id] = true;
};
const changeToDisagree = (id) => {
  dialog.value[id] = false;
  agreement.value[id] = false;
};
const submit = () => {
  handleSubmitSignUp();
};

const handleSubmitSignUp = () => {
  userStore.signUp({
    loginId: id.value,
    nickname: nickname.value,
    password: password.value,
    phoneNumber: phoneNumber.value,
    agreeShroop: agreement.value[AGREE.SHROOP],
    agreePersonal: agreement.value[AGREE.PERSONAL],
    agreeIdentify: agreement.value[AGREE.IDENTIFY],
  });
  requestAuthNumber();
  router.push("/phone");
};

const checkDuplicateID = async () => {
  return await checkDuplication({
    checkType: "loginId",
    dataBody: {
      loginId: id.value,
    },
    dataRef: isDuplId,
  });
};
const checkDuplicateNickname = async () => {
  return await checkDuplication({
    checkType: "nickname",
    dataBody: {
      nickname: nickname.value,
    },
    dataRef: isDuplNickname,
  });
};
const checkDuplicatePhone = async () => {
  return await checkDuplication({
    checkType: "phoneNumber",
    dataBody: {
      phoneNumber: phoneNumber.value,
    },
    dataRef: isDuplPhone,
  });
};
const requestAuthNumber = async () => {
  try {
    const data = await postApi({
      url: "/api/auth/phone",
      data: {
        phoneNumber: phoneNumber.value,
      },
    });
    cookies.set("uuid", data.uuid);
    cookies.set("seconds", data.seconds);
  } catch (error) {
    console.error(error);
  }
};

const checkDuplication = async (param) => {
  try {
    const data = await postApi({
      url: `/check/${param.checkType}`,
      data: param.dataBody,
    });
    if (data.result === false) {
      param.dataRef.value = true;
    } else {
      param.dataRef.value = false;
    }
  } catch (error) {
    console.error(error);
  }
};
</script>

<style lang="scss" scoped>
.sigupForm__block-phoneNumber {
  display: flex;
  gap: 10px;

  .phoneNumber-input {
    flex-basis: 70%;
  }
}

.duplicate-message {
  position: relative;

  padding-inline-start: 16px;
  padding-inline-end: 16px;

  color: rgb(var(--v-theme-heartRed));
  div {
    font-size: 12px;
    font-weight: 400;
    grid-area: messages;
    letter-spacing: 0.0333333333em;
    line-height: normal;
    min-height: 22px;
    padding-top: 3px;
    overflow: hidden;
    justify-content: space-between;
  }
}
</style>
