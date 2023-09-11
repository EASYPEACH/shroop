<template>
  <v-dialog :v-model="dialog" width="500">
    <v-card>
      <v-form v-model="isValid">
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  :rules="[
                    defaultTextRule.required,
                    (value) => defaultTextRule.customMinLength(value, 2),
                  ]"
                  label="이름"
                  v-model="name"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  :rules="[
                    defaultTextRule.required,
                    (value) => defaultTextRule.customMinLength(value, 11),
                  ]"
                  label="계좌번호"
                  v-model="account"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  :rules="[
                    defaultTextRule.required,
                    (value) => defaultTextRule.customMinLength(value, 4),
                  ]"
                  label="비밀번호"
                  type="password"
                  v-model="password"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="blue-darken-1"
            type="submit"
            variant="text"
            :disabled="!isValid"
            @click="saveAccountHandler"
          >
            연동하기
          </v-btn>
          <v-btn
            color="blue-darken-1"
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
import { defaultTextRule } from "@/components/Form/data/formRules";
const name = ref("");
const account = ref("");
const password = ref("");
const isValid = ref(false);

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
    name.value = "";
    account.value = "";
    password.value = "";
    emits("handle-cancle-modal");
    window.location.reload();
  } catch (error) {
    console.log(error);
    alert(error.response.data.message);
  }
};
</script>
