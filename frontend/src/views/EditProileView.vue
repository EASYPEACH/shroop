<template>
  <section>
    <main-title title="프로필 수정" />
    <v-form
      class="profile"
      @submit.prevent="handleSubmitRegister"
      enctype="multipart/form-data"
    >
      <div class="profile__info-imgbox">
        <div class="profile__info-img">
          <img :src="imageThumb ? imageThumb : basicProfile" />
          <label for="profile_image" class="profile__info-edit">
            <v-icon icon="mdi-camera" />
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
          <custom-text-input
            class="profile__info-input"
            placeholderText="기존 패스워드"
            v-model="oldPassword"
            type="password"
          />
          <custom-text-input
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
            <v-btn @click="requestAuthNumber" class="profile__info-btn"
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
          </div>
        </div>
      </div>
      <div v-show="!authResult" class="auth-fail">
        {{ modifyResultMsg }}
      </div>
      <submit-button
        :disabled="!isValid"
        class="submit-button"
        text="수정 완료"
      />
    </v-form>
    <plain-modal
      modalText="수정이 완료되었습니다"
      v-model="showPlainModal"
      @handle-cancle="showPlainModal = false"
      @handle-confirm="showPlainModal = false"
    />
  </section>
</template>

<script setup>
import { onBeforeMount, ref } from "vue";
import {
  changeImageToData,
  multipartFormDataJson,
  changeUrlToFiles,
} from "@/utils";
import { getApi, postApi, multipartPatchApi } from "@/api/modules";
import { useCookies } from "vue3-cookies";
import SubmitButton from "@/components/Button/SubmitButton.vue";
import basicProfile from "@/assets/image/basicProfile.jpeg";
import ProductTitle from "@/components/Title/ProductTitle.vue";
import CustomTextInput from "@/components/Form/CustomTextInput.vue";
import PlainModal from "@/components/Modal/PlainModal.vue";

const { cookies } = useCookies();
const isValid = ref(false);
const imageThumb = ref("");
const imageData = ref(null);
const authResult = ref(false);
const phoneNumber = ref("");
const phoneAuthNumber = ref("");
const nickname = ref("");
const oldPassword = ref("");
const newPassword = ref("");
const authResultMsg = ref("");
const profileImgRef = ref(null);
const showPlainModal = ref(false);
const modifyResultMsg = ref("");

// Image preview
const handleChangeProfile = async (event) => {
  isValid.value = true;
  imageData.value = event.target.files[0];
  imageThumb.value = await changeImageToData(imageData.value);
};

// 기존 회원 정보 가져오기
onBeforeMount(async () => {
  const userData = await getApi({
    url: "/api/members/profile",
  });
  console.error(userData.profileImg);
  let profileImgTransfer = await changeUrlToFiles(
    [userData.profileImg],
    new DataTransfer(),
  );
  profileImgRef.value.files = profileImgTransfer.files;
  imageData.value = profileImgTransfer.files;
  nickname.value = userData.nickname;
  phoneNumber.value = userData.phoneNumber;
  imageThumb.value = userData.profileImg;
});

// input event
const handleInputChnageEvent = () => {
  isValid.value = true;
};

// modify submit
const handleSubmitRegister = async () => {
  let formData = new FormData();

  if (profileImgRef.value !== null) {
    Array.from(profileImgRef.value.files).forEach((file) => {
      formData.append("userImg", file);
    });
  }
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

const requestAuthNumber = async () => {
  try {
    const data = await postApi({
      url: "/api/auth/phone",
      data: {
        phoneNumber: phoneNumber.value,
      },
    });
    cookies.set("uuid", data.uuid);
  } catch (error) {
    console.error(error);
  }
};
</script>

<style lang="scss" scoped>
section {
  width: 30%;
  margin: 0 auto;
  padding: 100px 0;

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
        border: 5px solid rgb(var(--v-theme-mainGray), 0.3);
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
        bottom: 33px;
        right: 20px;
        font-size: 20px;
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
      .profile__info-input {
        flex-basis: 50%;
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
  color: rgb(var(--v-theme-subBlue));
}
.auth-fail {
  color: rgb(var(--v-theme-heartRed));
}
</style>
