<template>
  <div>
    <main-title title="휴대전화 본인인증" />
    <v-card
      class="mx-auto pa-12 pb-8"
      max-width="470"
      rounded="lg"
      variant="plain"
    >
      <v-form v-model="isValid" @submit.prevent="handleSubmitAuth">
        <div class="phoneAuth__box">
          <custom-text-input
            class="phoneAuth__box-input"
            placeholder-text="인증 번호를 입력해주세요"
            v-model="phoneAuthNumber"
            :rules="[phoneAuthRule.required, phoneAuthRule.length]"
          />
          <span>{{ `0${minute}:${second}` }}</span>
          <span class="phoneAuth__box-reply">
            <v-btn
              class="reply-btn"
              @click="requestAuthNumber"
              :disabled="!replyisValid"
              >재요청</v-btn
            >
          </span>
        </div>

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
          {{ authResultMsg }}
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
import { CustomTextInput } from "@/components/Form";
import { onMounted } from "vue";
import { watchEffect } from "vue";

const { cookies } = useCookies();
const router = useRouter();
const phoneAuthNumber = ref("");
const authResult = ref(true);
const authResultMsg = ref("");
const userStore = useUserStore();
const time = ref(0);
const timeOrigin = ref(0);
const timerInterval = ref(null);
const minute = ref(0);
const second = ref(0);
const replyisValid = ref(true);

const isValid = ref(false);
const handleSubmitAuth = async () => {
  try {
    await postApi({
      url: "/api/auth/sign-up",
      data: {
        loginId: userStore.loginId,
        nickname: userStore.nickname,
        password: userStore.password,
        phoneNumber: userStore.phoneNumber,
        agreeShroop: userStore.agreeShroop,
        agreePersonal: userStore.agreePersonal,
        agreeIdentify: userStore.agreeIdentify,
        uuid: cookies.get("uuid"),
        phoneAuthNumber: phoneAuthNumber.value,
      },
    });

    router.push("/login");
  } catch (error) {
    if (error.response.status === 400) {
      authResult.value = false;
      authResultMsg.value = "인증 번호를 다시 확인해주세요";
    }
    if (error.response.status === 410) {
      authResult.value = false;
      authResultMsg.value = "인증 시간이 초과되었습니다";
    }
    if (error.response.status === 429) {
      alert("인증을 5회 실패하여 회원가입 페이지로 이동합니다");
      router.push("/signup");
    }
  }
};

onBeforeMount(() => {
  if (userStore.loginId === "") {
    router.push("/signup");
  }
  time.value = cookies.get("seconds");
  timeOrigin.value = cookies.get("seconds");
});

onMounted(() => {
  if (time.value > 0) {
    timerInterval.value = setInterval(() => {
      time.value--;
    }, 1000);
  }
});

watchEffect(() => {
  if (time.value === 0) {
    clearInterval(timerInterval.value);
  }

  if (time.value === timeOrigin.value - 2) {
    replyisValid.value = true;
  }

  minute.value = Math.floor(time.value / 60);
  second.value = time.value - Math.floor(time.value / 60) * 60;
  if (second.value < 10) {
    second.value = "0" + second.value;
  }
});

const requestAuthNumber = async () => {
  //재요청 버튼 비활성화

  try {
    replyisValid.value = false;
    clearInterval(timerInterval.value);
    const data = await postApi({
      url: "/api/auth/phone",
      data: {
        phoneNumber: userStore.phoneNumber,
      },
    });
    cookies.set("uuid", data.uuid);
    time.value = data.seconds;
    authResult.value = false;
    authResultMsg.value = "인증번호를 재전송하였습니다";
    timerInterval.value = setInterval(() => {
      time.value--; //타이머 시간 감소
    }, 1000);
  } catch (error) {
    console.error(error);
  }
};
</script>

<style lang="scss" scoped>
.v-form {
  width: 90%;
}
.phoneAuth__box {
  width: 100%;
  height: 44px;
  display: flex;
  position: relative;
  .phoneAuth__box-input {
    flex: 1;
  }
  span {
    position: absolute;
    top: 50%;
    right: -5px;
    transform: translate(-50%, -50%);
    color: rgb(var(--v-theme-heartRed));
  }
  .phoneAuth__box-reply {
    right: -101px;
    .reply-btn {
      padding-left: 0px;
      padding-right: 0px;
    }
  }
}
.form__btn-login {
  background: none !important;
  font-weight: 600;
  color: rgb(var(--v-theme-mainGray));
  margin-top: 22px;
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
