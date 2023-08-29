<template>
  <section>
    <main-title title="프로필 수정" />
    <content-layout>
      <v-form class="profile">
        <div class="profile__info profile__info-imgbox">
          <div class="profile__info-img">
            <img :src="imageThumb ? imageThumb : basicProfile" />
            <label for="profile_image" class="profile__info-edit">
              <v-icon icon="mdi-camera" />
            </label>
            <input
              @change="handleChangeProfile"
              ref="attachRef"
              id="profile_image"
              name="profile_image"
              type="file"
            />
          </div>
        </div>
        <product-title title="프로필 정보" />
        <div class="profile__info">
          <h4 class="profile__info-name">닉네임</h4>
          <custom-text-input
            class="profile__info-input"
            :rules="[defaultTextRule.required, defaultTextRule.min]"
            placeholderText="닉네임"
            v-model="nickname"
          />
        </div>
        <div class="profile__info">
          <h4 class="profile__info-name">패스워드</h4>
          <div class="info__input-box">
            <custom-text-input
              class="profile__info-input"
              :rules="[
                passwordRule.required,
                passwordRule.check,
                passwordRule.min,
              ]"
              placeholderText="기존 패스워드"
              v-model="oldPassword"
            />
            <custom-text-input
              class="profile__info-input"
              :rules="[
                passwordRule.required,
                passwordRule.check,
                passwordRule.min,
              ]"
              placeholderText="변경할 패스워드"
              v-model="newPassword"
            />
          </div>
        </div>
        <div class="profile__info">
          <h4 class="profile__info-name">휴대전화</h4>
          <div class="info__input-box">
            <div class="identify__phoneNumber">
              <custom-text-input
                class="profile__info-input"
                :rules="[phoneNumberRule.required, phoneNumberRule.check]"
                type="newPhoneNumber"
                placeholderText="휴대전화번호"
                v-model="newPhoneNumber"
                hide-details
              />
              <v-btn class="profile__info-btn">인증 하기<br /> </v-btn>
            </div>
            <div class="identify__phoneNumber">
              <custom-text-input
                class="profile__info-input"
                placeholderText="인증번호 4자리 입력"
                v-model="authNumber"
                hide-details
              />
              <v-btn class="profile__info-btn" @click="checkAuthNumber"
                >인증 완료</v-btn
              >
            </div>
            <strong :class="authResult ? 'auth-success' : 'auth-fail'">{{
              authResultMsg
            }}</strong>
          </div>
        </div>
        <submit-button class="submit-button" text="수정 완료" />
      </v-form>
    </content-layout>
  </section>
</template>

<script setup>
import { ref } from "vue";
import { changeImageToData } from "@/utils";
import {
  defaultTextRule,
  phoneNumberRule,
  passwordRule,
} from "@/components/Form/data/formRules";
import ContentLayout from "@/layouts/ContentLayout.vue";
import SubmitButton from "@/components/Button/SubmitButton.vue";
import basicProfile from "@/assets/image/basicProfile.jpeg";
import ProductTitle from "@/components/Title/ProductTitle.vue";
import CustomTextInput from "@/components/Form/CustomTextInput.vue";

const imageThumb = ref("");
const imageData = ref(null);
const authResult = ref(false);
const newPhoneNumber = ref(null);
const authNumber = ref(null);
const oldPassword = ref(null);
const newPassword = ref(null);
const authResultMsg = ref("");

const handleChangeProfile = async (event) => {
  imageData.value = event.target.files[0];
  imageThumb.value = await changeImageToData(imageData.value);
};

const checkAuthNumber = () => {
  authResult.value = !authResult.value;
  authResultMsg.value = "인증 성공 여부";
};
</script>

<style lang="scss" scoped>
section {
  padding-bottom: 200px;
}

.profile {
  .profile__info {
    display: flex;
    margin: 20px 0;
    position: relative;
    .identify__phoneNumber {
      margin-bottom: 20px;
      display: flex;
      gap: 20px;
      align-items: center;
      .profile__info-input {
        flex-basis: 50%;
      }
    }
    &.profile__info-imgbox {
      justify-content: center;
    }
    .info__input-box {
      flex: 1;
    }
    .profile__info-title {
      display: flex;
      margin: 12px;
      font-weight: 600;
      font-size: 20px;
      flex-basis: 20%;
    }
    input {
      width: 0;
      height: 0;
    }
    .profile__info-img {
      width: 200px;
      position: relative;
      img {
        width: 200px;
        height: 200px;
        border-radius: 50%;
        margin-bottom: 1rem;
        object-fit: cover;
        object-position: center;
      }
      label {
        position: absolute;
        bottom: 33px;
        right: 20px;
        font-size: 20px;
      }
    }
    .profile__info-edit {
      position: absolute;
    }
    .profile__info-name {
      display: flex;
      margin: 12px;
      font-weight: 600;
      font-size: 18px;
      flex-basis: 15%;
    }
    .profile__info-input {
      flex: 1;
    }
  }
}

.submit-button {
  margin-top: 80px;
}

.auth-success {
  color: rgb(var(--v-theme-subBlue));
}
.auth-fail {
  color: rgb(var(--v-theme-heartRed));
}
</style>
