<template>
  <div>
    <Title title="회원가입" />
    <v-card class="mx-auto pa-12 pb-8" max-width="540" rounded="lg">
      <v-form ref="form" v-model="isValid" @submit.prevent="submit">
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
        />
        <custom-text-input
          label="닉네임"
          placeholder-text="닉네임을 입력해주세요"
          v-model="nickname"
          :rules="[nickNameRule.required, nickNameRule.min, nickNameRule.check]"
        />
        <div>
          <div class="text-subtitle-1 text-medium-emphasis">휴대폰번호</div>
          <div class="sigupForm__block-phoneNumber">
            <v-select
              v-model="mobileCarrier"
              :items="phoneItems"
              :rules="[selectRule.require]"
              density="compact"
              label="통신사"
            ></v-select>
            <PhoneInput
              v-model="phoneNumber"
              :rules="[phoneNumberRule.require, phoneNumberRule.check]"
            />
          </div>
        </div>
        <agreement-check-box
          v-for="item in usageRules"
          :key="item.id"
          v-model="agreement[item.id]"
          @show-dialog="dialog[item.id] = true"
          :agreement-statement="item.title"
          :rules="[agreeRule.require]"
        />
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn :disabled="!isValid" type="submit" color="subBlue">
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
import Title from "@/components/Title.vue";
import CustomTextInput from "@/components/Form/CustomTextInput.vue";
import PasswordInput from "@/components/Form/PasswordInput.vue";
import PhoneInput from "@/components/Form/PhoneInput.vue";
import AgreementCheckBox from "@/components/Form/AgreementCheckBox.vue";
import AgreementDialog from "@/components/Form/AgreementDialog.vue";
import usageRules from "@/components/Form/data/usageRules";
import {
  idRule,
  passwordRule,
  nickNameRule,
  agreeRule,
  selectRule,
  phoneNumberRule,
} from "@/components/Form/data/formRules";
import { AGREE } from "@/consts/agree";
import { ref } from "vue";

const phoneItems = ref(["LGT", "SKT", "KT"]);
const id = ref("");
const password = ref("");
const visible = ref(false);
const phoneNumber = ref("");
const mobileCarrier = ref("");
const nickname = ref("");
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

const changeToAgree = (id) => {
  dialog.value[id] = false;
  agreement.value[id] = true;
};
const changeToDisagree = (id) => {
  dialog.value[id] = false;
  agreement.value[id] = false;
};
const submit = () => {
  console.log(id.value);
  console.log(password.value);
  console.log(phoneNumber.value);
  console.log(mobileCarrier.value);
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
</style>
