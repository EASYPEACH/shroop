<template>
  <v-dialog :v-model="dialog" width="500">
    <v-card>
      <v-form v-model="isValid" @submit.prevent="saveAccountHandler">
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <div class="text-subtitle-1 text-medium-emphasis">은행사</div>
                <v-select
                  :rules="[selectRule.required]"
                  :items="bankList"
                  v-model="selectedBank"
                  label="은행사"
                ></v-select>
              </v-col>

              <v-divider></v-divider>

              <v-col cols="12">
                <h2 style="margin: 10px 0 30px 0; font-size: 20px">계좌정보</h2>
                <custom-text-input
                  placeholder-text="이름"
                  label="계좌 소유주"
                  v-model="name"
                  :rules="[
                    defaultTextRule.required,
                    accountRule.checkChar,
                    (value) => defaultTextRule.customMinLength(value, 2),
                  ]"
                />
                <custom-text-input
                  label="계좌번호"
                  v-model="account"
                  :rules="[
                    defaultTextRule.required,
                    accountRule.checkNum,
                    (value) => defaultTextRule.customMinLength(value, 11),
                  ]"
                />
                <password-input
                  title="계좌 비밀번호"
                  v-model="password"
                  :rules="[
                    defaultTextRule.required,
                    accountRule.checkNum,
                    (value) => defaultTextRule.customMinLength(value, 4),
                  ]"
                  :visible="isVisible"
                  @toggle-visible="isVisible = !isVisible"
                />
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="mainGreen"
            type="submit"
            variant="text"
            :disabled="!isValid"
          >
            연동하기
          </v-btn>
          <v-btn
            color="mainGreen"
            variant="text"
            @click="$emit('handle-cancle-modal')"
          >
            닫기
          </v-btn>
        </v-card-actions>
      </v-form>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { postApi } from "@/api/modules";
import { ref } from "vue";
import {
  defaultTextRule,
  accountRule,
  selectRule,
} from "@/components/Form/data/formRules";
import { useRouter } from "vue-router";
import { CustomTextInput, PasswordInput } from "../Form";

const router = useRouter();
const name = ref("");
const account = ref("");
const password = ref("");
const isValid = ref(false);
const isVisible = ref(false);
const bankList = ref(["슈룹"]);
const selectedBank = ref("슈룹");

defineProps({ dialog: Boolean });
const emits = defineEmits(["handle-cancle-modal"]);

const saveAccountHandler = async () => {
  try {
    await postApi({
      url: "/api/bank/linking",
      data: {
        name: name.value,
        account: account.value,
        password: password.value,
      },
    });

    alert("계좌 연동에 성공했습니다.");
    console.log("A");
    name.value = "";
    account.value = "";
    password.value = "";
    emits("handle-cancle-modal");
    router.go(0);
  } catch (error) {
    console.log(error);
    alert(error.response.data.message);
  }
};
</script>
