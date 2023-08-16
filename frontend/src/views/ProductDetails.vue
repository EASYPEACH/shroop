<template>
  <section>
    <div class="img_section-content">
      <v-carousel>
        <v-carousel-item
          src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
          cover
        ></v-carousel-item>

        <v-carousel-item
          src="https://cdn.vuetifyjs.com/images/cards/hotel.jpg"
          cover
        ></v-carousel-item>

        <v-carousel-item
          src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
          cover
        ></v-carousel-item>
      </v-carousel>
      <div class="content_div-content">
        <v-card-item>
          <div>
            <div class="item_div-title">
              <div class="text-h3 mb-1">
                냉장고 팔아요냉장고 팔아요냉장고 팔아요냉장고
              </div>
              <div class="item_div-menu">
                <v-menu>
                  <template v-slot:activator="{ props }">
                    <v-btn icon="mdi-dots-vertical" v-bind="props"></v-btn>
                  </template>
                  <v-list>
                    <v-list-item
                      v-for="(item, index) in items"
                      :key="index"
                      :value="index"
                    >
                      <v-list-item-title>{{ item.title }}</v-list-item-title>
                    </v-list-item>
                  </v-list>
                </v-menu>
              </div>
            </div>
            <div class="text-h4 mb-1">1000원</div>
            <div class="text-subtitle-1 mb-1">배송비 포함</div>
            <div class="text-subtitle-1 mb-1">전자제품</div>
            <div class="text-subtitle-1 mb-1">2023-08-09</div>
          </div>
        </v-card-item>
        <v-divider :thickness="4" class="border-opacity-25 mt-8"></v-divider>
        <div class="content_div-profile-contain">
          <div class="content_div-profile">
            <v-avatar>
              <v-img
                src="https://cdn.vuetifyjs.com/images/john.jpg"
                alt="John"
              ></v-img>
            </v-avatar>
            <div class="text-h5">따식이행님</div>
          </div>
          <div class="text-h5">등급 30점</div>
        </div>

        <v-card-actions>
          <v-btn-toggle multiple>
            <v-btn @click="HandleChangeHeart()">
              좋아요
              <v-icon
                v-if="!likeToggle"
                icon="mdi-cards-heart-outline"
              ></v-icon>
              <v-icon v-else icon="mdi-cards-heart"></v-icon>
            </v-btn>
          </v-btn-toggle>
          <v-btn variant="outlined"> 구매하기 </v-btn>
        </v-card-actions>
      </div>
    </div>
    <div class="content_div-detail-contain">
      <div class="content_div-alert">
        <div>
          <v-alert color="green" variant="outlined">
            <div class="text-h4">
              <v-icon icon="mdi-alert-decagram"></v-icon> 구매 주의 사항
            </div>
            <div>
              상품 정보를 꼼꼼히 확인해주세요! <br />
              중고상품이므로 상품 정보 미확인으로 인한 피해는 <br />
              구매자에게 부담이 있습니다.
            </div>
          </v-alert>
        </div>
      </div>
      <div class="content_div-detail">
        <div class="text-h4 mb-8">상품 정보</div>
        <v-table>
          <thead>
            <tr>
              <th class="text-left">정보</th>
              <th class="text-left">내용</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in desserts" :key="item.name">
              <td>{{ item.title }}</td>
              <td>{{ item.content }}</td>
            </tr>
          </tbody>
        </v-table>
      </div>
      <div class="content_div-defect">
        <div class="text-h4 mb-10">상품 결함 정보</div>
        <div class="text-h5 mb-1">경함 여부 : 있음</div>
        <div class="content_div-defect-img">
          <v-img
            width="300"
            src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
          ></v-img>
          <v-img
            width="300"
            src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
          ></v-img>
          <v-img
            width="300"
            src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
          ></v-img>
          <v-img
            width="300"
            src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
          ></v-img>
          <v-img
            width="300"
            src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
          ></v-img>
          <v-img
            width="300"
            src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
          ></v-img>
        </div>
      </div>
      <div class="content_div-detail">
        <div class="text-h4 mb-10">상품 기타 상세 정보</div>
        <div class="text-h5 mb-1">
          정보과학에서 더미 데이터는 유용한 데이터가 포함되지 않지만 공간을
          예비해두어 실제 데이터가 명목상 존재하는 것처럼 다루는 유순한 정보를
          의미한다. 더미 데이터는 테스트 및 운영 목적을 위해 플레이스홀더로
          사용할 수 있다. 정보과학에서 더미 데이터는 유용한 데이터가 포함되지
          않지만 공간을 예비해두어 실제 데이터가 명목상 존재하는 것처럼 다루는
          유순한 정보를 의미한다. 더미 데이터는 테스트 및 운영 목적을 위해
          플레이스홀더로 사용할 수 있다. 정보과학에서 더미 데이터는 유용한
          데이터가 포함되지 않지만 공간을 예비해두어 실제 데이터가 명목상
          존재하는 것처럼 다루는 유순한 정보를 의미한다. 더미 데이터는 테스트 및
          운영 목적을 위해 플레이스홀더로 사용할 수 있다. 정보과학에서 더미
          데이터는 유용한 데이터가 포함되지 않지만 공간을 예비해두어 실제
          데이터가 명목상 존재하는 것처럼 다루는 유순한 정보를 의미한다. 더미
          데이터는 테스트 및 운영 목적을 위해 플레이스홀더로 사용할 수 있다.
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { useRoute } from "vue-router";

const { params } = useRoute();

console.log(params);

import { ref } from "vue";

const likeToggle = ref(false);

const HandleChangeHeart = () => {
  likeToggle.value = !likeToggle.value;
};

const items = [{ title: `삭제하기` }, { title: `신고하기` }];

const desserts = [
  {
    title: `구입시기`,
    content: `2023-08-10`,
  },
  {
    title: `브랜드명/모델명`,
    content: `삼성/갤럭시 플립5`,
  },
  {
    title: `상태`,
    content: `중`,
  },
];
</script>

<style lang="scss" scoped>
section {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.img_section-content {
  display: flex;
  justify-content: center;
  gap: 50px;
}

.v-carousel {
  width: 500px;
  border-radius: 25px;
}

.content_div-content {
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  width: 600px;
}

.v-card-actions {
  justify-content: space-around;
  .v-btn-toggle {
    height: 36px;
    border: thin solid currentColor;
  }
}

.v-btn {
  width: 270px;
}

.content_div-profile-contain {
  padding: 0.625rem 1rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.content_div-profile {
  display: flex;
  align-items: center;
  gap: 20px;
}

.v-avatar.v-avatar--size-default {
  --v-avatar-height: 70px;
}

.v-card-item {
  .item_div-title {
    display: flex;
    justify-content: space-between;
  }
  .item_div-menu {
    width: 48px;
    // .v-list {
    //   width: 53px;
    // }
  }
}

.v-table {
  font-size: 25px;
}

.v-btn-toggle {
  height: 36px;
  border-color: inherit;
}

.content_div-detail-contain {
  width: 1160px;
  display: flex;
  flex-direction: column;
  margin-top: 120px;
  .content_div-alert {
    display: flex;
    justify-content: space-around;
    .v-alert {
      width: 900px;
      font-size: 25px;
      text-align: center;
    }
  }

  .content_div-detail {
    margin-top: 60px;
    div {
      // margin-top: 30px;
    }
  }

  .content_div-defect {
    margin-top: 60px;
    div {
      margin-top: 30px;
    }
    .content_div-defect-img {
      margin-top: 0px;
      display: flex;
      gap: 20px;
      overflow: auto;
      white-space: nowrap;
    }
  }
}
</style>
