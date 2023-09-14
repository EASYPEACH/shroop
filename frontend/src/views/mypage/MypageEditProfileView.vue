<template>
  <section>
    <main-title title="프로필 수정" style="padding-top: 0" />
    <v-form
      class="profile"
      @submit.prevent="handleSubmitRegister"
      enctype="multipart/form-data"
    >
      <div class="profile__info-imgbox">
        <div class="profile__info-img">
          <img :src="imageThumb" />
          <label for="profile_image" class="profile__info-edit">
            <div />
            <span> <v-icon icon="mdi-camera-outline" /> </span>
          </label>
          <input
            @change="handleChangeProfile"
            ref="profileImgRef"
            id="profile_image"
            name="profile_image"
            type="file"
            accept="image/*"
          />
        </div>
      </div>
      <product-title title="프로필 정보" />
      <div class="profile__info">
        <h4 class="profile__info-name">닉네임</h4>
        <custom-text-input
          class="profile__info-input"
          placeholderText="닉네임"
          v-model="nickname"
          @keydown="handleInputChnageEvent"
        />
      </div>
      <div class="profile__info">
        <h4 class="profile__info-name">패스워드</h4>
        <div class="info__input-box">
          <password-input
            :visible="oldVisible"
            @toggle-visible="oldVisible = !oldVisible"
            title="기존 패스워드"
            class="profile__info-input"
            placeholderText="기존 패스워드"
            v-model="oldPassword"
            type="password"
          />
          <password-input
            :visible="newVisible"
            @toggle-visible="newVisible = !newVisible"
            title="새로운 패스워드"
            class="profile__info-input"
            placeholderText="변경할 패스워드"
            v-model="newPassword"
            @keydown="handleInputChnageEvent"
            type="password"
          />
        </div>
      </div>
      <div class="profile__info">
        <h4 class="profile__info-name">휴대전화</h4>
        <div class="info__input-box">
          <div class="identify__phoneNumber">
            <custom-text-input
              class="profile__info-input"
              type="phoneNumber"
              placeholderText="휴대전화번호"
              v-model="phoneNumber"
              hide-details
              @keydown="handleInputChnageEvent"
            />

            <v-btn
              @click="requestAuthNumber"
              class="profile__info-btn"
              :disabled="!replyisValid"
              >인증 하기<br />
            </v-btn>
          </div>
          <div class="identify__phoneNumber">
            <custom-text-input
              class="profile__info-input"
              placeholderText="인증번호 4자리 입력"
              v-model="phoneAuthNumber"
              hide-details
            />
            <span v-if="isTimeRest">{{ `0${minute}:${second}` }}</span>
          </div>
          <div v-show="!authResult" class="auth-fail">
            {{ modifyResultMsg }}
          </div>
        </div>
      </div>
      <!-- <div v-show="!authResult" class="auth-fail">
        {{ modifyResultMsg }}
      </div> -->
      <submit-button
        :disabled="!isValid"
        class="submit-button"
        text="수정 완료"
      />
    </v-form>
    <v-form @submit.prevent="handleCancelMemberShip">
      <product-title title="회원 탈퇴" />
      <password-input
        class="profile__info-input"
        type="password"
        placeholderText="비밀번호입력"
        v-model="password"
        hide-details
        @keydown="handleInputSignOutEvent"
      />
      <div v-show="!cancelMembershipResult" class="auth-fail">
        {{ cancelMembershipResultMsg }}
      </div>
      <submit-button
        :disabled="!isSignOutValid"
        class="submit-button"
        text="확인"
      />
    </v-form>

    <plain-modal
      modalText="수정이 완료되었습니다"
      v-model="showPlainModal"
      @handle-cancle="showPlainModal = false"
      @handle-confirm="handleConfirmEdit"
    />
  </section>
</template>

<script setup>
import { onBeforeMount, ref, watchEffect } from "vue";
import {
  changeImageToData,
  multipartFormDataJson,
  changeUrlToFiles,
} from "@/utils";
import { getApi, postApi, multipartPatchApi, deleteApi } from "@/api/modules";
import { useCookies } from "vue3-cookies";
import { useRouter } from "vue-router";

import { SubmitButton } from "@/components/Button";
import { MainTitle, ProductTitle } from "@/components/Title";
import { CustomTextInput, PasswordInput } from "@/components/Form";
import { PlainModal } from "@/components/Modal";

const { cookies } = useCookies();
const router = useRouter();
const isValid = ref(false);
const imageThumb = ref("");
const imageData = ref(null);
const authResult = ref(false);
const cancelMembershipResult = ref(false);
const cancelMembershipResultMsg = ref("");
const phoneNumber = ref("");
const phoneAuthNumber = ref("");
const nickname = ref("");
const oldPassword = ref("");
const newPassword = ref("");
const password = ref("");
const profileImgRef = ref(null);
const showPlainModal = ref(false);
const modifyResultMsg = ref("");
const isSignOutValid = ref(false);
const oldVisible = ref(false);
const newVisible = ref(false);

//휴대전화인증
const time = ref(120);
const timeOrigin = ref(120);
const timerInterval = ref(null);
const minute = ref(0);
const second = ref(0);
const replyisValid = ref(false);
const isTimeRest = ref(false);

// Image preview
const handleChangeProfile = async (event) => {
  isValid.value = true;
  imageData.value = event.target.files[0];
  imageThumb.value = await changeImageToData(imageData.value);
};

// 기존 회원 정보 가져오기
onBeforeMount(async () => {
  try {
    const userData = await getApi({
      url: "/api/members/profile",
    });
    let profileImgTransfer = await changeUrlToFiles(
      [userData.profileImg],
      new DataTransfer(),
    );
    profileImgRef.value.files = profileImgTransfer.files;
    imageData.value = profileImgTransfer.files;
    nickname.value = userData.nickname;
    phoneNumber.value = userData.phoneNumber;
    imageThumb.value = userData.profileImg;
  } catch (error) {
    console.error(error);
  }
});

// input event
const handleInputChnageEvent = () => {
  replyisValid.value = true;
  isValid.value = true;
};

const handleInputSignOutEvent = () => {
  isSignOutValid.value = true;
};

// modify submit
const handleSubmitRegister = async () => {
  let formData = new FormData();

  Array.from(profileImgRef.value.files).forEach((file) => {
    formData.append("userImg", file);
  });

  multipartFormDataJson(formData, "editRequest", {
    nickname: nickname.value,
    oldPassword: oldPassword.value,
    newPassword: newPassword.value,
    uuid: cookies.get("uuid"),
    phoneNumber: phoneNumber.value,
    phoneAuthNumber: phoneAuthNumber.value,
  });

  try {
    await multipartPatchApi({
      url: `/api/members/profile`,
      data: formData,
    });
    isValid.value = false;
    showPlainModal.value = true;
    authResult.value = true;
    phoneAuthNumber.value = "";
  } catch (err) {
    authResult.value = false;
    modifyResultMsg.value = err.response.data.message;
  }
  oldPassword.value = "";
  newPassword.value = "";
};

watchEffect(() => {
  if (time.value === 0) {
    clearInterval(timerInterval.value);
  }

  if (time.value <= timeOrigin.value - 2) {
    replyisValid.value = true;
  }

  minute.value = Math.floor(time.value / 60);
  second.value = time.value - Math.floor(time.value / 60) * 60;
  if (second.value < 10) {
    second.value = "0" + second.value;
  }
});

// 인증번호 요청
const requestAuthNumber = async () => {
  try {
    isTimeRest.value = true;
    replyisValid.value = false;
    clearInterval(timerInterval.value);
    const data = await postApi({
      url: "/api/auth/phone",
      data: {
        phoneNumber: phoneNumber.value,
      },
    });
    cookies.set("uuid", data.uuid);
    time.value = data.seconds;
    authResult.value = false;
    modifyResultMsg.value = "인증번호를 전송하였습니다";
    timerInterval.value = setInterval(() => {
      time.value--; //타이머 시간 감소
      if (time.value === 0) {
        isTimeRest = false;
      }
    }, 1000);
  } catch (error) {
    console.error(error);
  }
};

// 수정 확인
const handleConfirmEdit = () => {
  router.push("/mypage/home");
  showPlainModal.value = false;
};

// 탈퇴 요청
const handleCancelMemberShip = async () => {
  try {
    const data = await deleteApi({
      url: "/api/members/leave",
      data: {
        password: password.value,
      },
    });

    alert(data.message);
    logout();
  } catch (error) {
    console.error(error);
    if (error.response.status === 400) {
      cancelMembershipResultMsg.value = error.response.data.message;
    }
  }
};

const logout = async () => {
  try {
    await postApi({
      url: "/logout",
    });
    router.go(0);
  } catch (error) {
    console.error(error);
  }
};
</script>

<style lang="scss" scoped>
section {
  width: 60%;
  margin: 0 auto;

  @media (max-width: 1200px) {
    width: 60%;
  }

  @media (max-width: 720px) {
    width: 90%;
  }
}

.profile {
  .profile__info-imgbox {
    display: flex;
    margin: 20px 0;
    position: relative;
    justify-content: center;
    .profile__info-img {
      width: 200px;
      position: relative;
      img {
        width: 200px;
        height: 200px;
        border-radius: 50%;
        border: 5px solid rgb(var(--v-theme-mainGray), 0.8);
        margin-bottom: 1rem;
        object-fit: cover;
        object-position: center;
      }
      input {
        width: 0;
        height: 0;
      }
      label {
        position: absolute;
        bottom: 81px;
        right: 74px;
        font-size: 20px;
        cursor: pointer;
        color: #fff;

        > div {
          background: rgb(var(--v-theme-mainGray), 0.8);
          height: 100%;
          padding: 50px;
          position: absolute;
          border-radius: 50%;
          clip-path: ellipse(100px 100px at -28% -40%);
        }
        span {
          position: absolute;
          top: 11px;
          left: 18px;
          font-size: 14px;
        }
      }
    }
  }
  .profile__info {
    display: flex;
    margin: 20px 0;
    position: relative;

    @media (max-width: 720px) {
      flex-direction: column;
    }
    .identify__phoneNumber {
      margin-bottom: 20px;
      display: flex;
      gap: 20px;
      align-items: center;
      position: relative;
      .profile__info-input {
        flex-basis: 50%;
      }
      span {
        position: absolute;
        top: 50%;
        right: -1px;
        transform: translate(-50%, -50%);
        color: rgb(var(--v-theme-heartRed));
      }
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

    .profile__info-edit {
      position: absolute;
    }
    .profile__info-name {
      display: flex;
      margin: 12px;
      font-weight: 600;
      font-size: 18px;
      flex-basis: 15%;
      white-space: nowrap;
    }
    .profile__info-input {
      flex: 1;
    }
  }
}

.submit-button {
  margin-top: 70px;
}

.auth-success {
  color: rgb(var(--v-theme-subGreen));
}
.auth-fail {
  color: rgb(var(--v-theme-heartRed));
  font-size: 15px;
}

.v-pagination {
  margin: 0 auto;
}
</style>
