<template>
  <v-dialog :v-model="dialog" width="auto">
    <v-btn
      variant="text"
      color="#fff"
      class="close-btn"
      @click="$emit('handleCancle')"
    >
      <v-icon icon="mdi-close" />
    </v-btn>
    <div class="buyer-info">
      <h4>구매자</h4>
      <ul>
        <li>
          <h5>이름</h5>
          <p>{{ buyerInfo.name }}</p>
        </li>
        <li>
          <h5>배송지</h5>
          <p>
            {{ buyerInfo.address }}
          </p>
        </li>
        <li>
          <h5>전화번호</h5>
          <p>{{ buyerInfo.phoneNumber }}</p>
        </li>
      </ul>
      <v-btn color="subBlue" block @click="$emit('handleConfirm')">확인</v-btn>
    </div>
  </v-dialog>
</template>

<script setup>
defineProps({
  buyerInfo: Object,
  dialog: Boolean,
});
defineEmits(["handleConfirm", "handleCancle"]);
</script>

<style lang="scss" scoped>
.close-btn {
  font-size: 20px;
  place-self: flex-end;
  margin-right: -51px;
}

.buyer-info {
  width: 370px;
  background: rgb(var(--v-theme-surface));
  padding: 20px;
  border-radius: 20px;
  h4 {
    font-weight: 600;
    text-align: center;
    padding: 10px 0;
  }
  ul {
    padding: 20px 0;
    li {
      display: flex;
      h5 {
        flex-basis: 100px;
      }
      p {
        flex: 1;
      }
      & + li {
        margin-top: 10px;
      }
    }
  }
}
</style>
