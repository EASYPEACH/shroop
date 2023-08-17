<template>
  <section>
    <div class="imgSlide">
      <v-carousel>
        <v-carousel-item
          v-for="(productImg, idx) in productImgs"
          :key="idx"
          :src="productImg.imgUrl"
          cover
        ></v-carousel-item>
      </v-carousel>
      <div class="productContent">
        <v-card-item>
          <div>
            <div class="productContent__item-title">
              <div class="text-h5 pt-2">냉장고 팔아요</div>
              <div class="productContent__side-tooltips">
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
                      <v-list-item-title class="font-weight-bold">
                        {{ item.title }}
                      </v-list-item-title>
                    </v-list-item>
                  </v-list>
                </v-menu>
              </div>
            </div>
            <div class="productContent__price">
              <div class="text-h5 mb-1">1000원</div>
              <div class="text-caption mb-1">배송비 포함</div>
            </div>
            <v-table class="mt-2">
              <tbody>
                <tr>
                  <td class="text-subtitle-2">
                    <v-icon icon="mdi-vector-point"></v-icon>카테고리
                  </td>
                  <td>{{ productContent.category }}</td>
                </tr>
                <tr>
                  <td class="text-subtitle-2">
                    <v-icon icon="mdi-vector-point"></v-icon>생성일자
                  </td>
                  <td>{{ productContent.createDate }}</td>
                </tr>
              </tbody>
            </v-table>
          </div>
        </v-card-item>
        <v-divider :thickness="4" class="border-opacity-25 mb-5"></v-divider>
        <div class="productContent__profile">
          <div class="productContent__profile-content">
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
              <span class="font-weight-bold pb-1">103</span>
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

    <div class="productDetail">
      <div class="productDetail__alert">
        <div>
          <v-alert color="red" variant="outlined">
            <div class="text-h5">
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
      <div class="productDetail__content">
        <div class="text-h5 mb-6 font-weight-bold">상품 정보</div>
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
      <div class="productDetail__defect">
        <div class="text-h5 mb-6 font-weight-bold">상품 결함 정보</div>
        <div class="text-h6">경함 여부 : 있음</div>
        <div class="productDetail__defect">
          <v-img
            width="300"
            v-for="(defectImg, idx) in defectImgs"
            :src="defectImg.imgUrl"
            :key="idx"
          ></v-img>
        </div>
      </div>
      <div class="productDetail__content">
        <div class="text-h5 mb-6 font-weight-bold">상품 기타 상세 정보</div>
        <div class="text-h6">
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

const productImgs = [
  { imgUrl: `https://cdn.vuetifyjs.com/images/cards/docks.jpg` },
  { imgUrl: `https://cdn.vuetifyjs.com/images/cards/hotel.jpg` },
  { imgUrl: `https://cdn.vuetifyjs.com/images/cards/sunshine.jpg` },
];

const defectImgs = [
  { imgUrl: `https://cdn.vuetifyjs.com/images/cards/docks.jpg` },
  { imgUrl: `https://cdn.vuetifyjs.com/images/cards/hotel.jpg` },
  { imgUrl: `https://cdn.vuetifyjs.com/images/cards/sunshine.jpg` },
  { imgUrl: `https://cdn.vuetifyjs.com/images/cards/sunshine.jpg` },
  { imgUrl: `https://cdn.vuetifyjs.com/images/cards/sunshine.jpg` },
  { imgUrl: `https://cdn.vuetifyjs.com/images/cards/sunshine.jpg` },
  { imgUrl: `https://cdn.vuetifyjs.com/images/cards/sunshine.jpg` },
];

const items = [{ title: `삭제하기` }, { title: `신고하기` }];

const productContent = {
  category: `전자제품`,
  createDate: `2023-07-14`,
};

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
  margin-top: 50px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.imgSlide {
  display: flex;
  justify-content: center;
  gap: 50px;

  .productContent {
    display: flex;
    justify-content: space-between;
    flex-direction: column;
    width: 400px;

    .productContent__price {
      margin-top: 15px;
      display: flex;
      align-items: flex-end;
      gap: 20px;
    }

    .v-card-item {
      .productContent__item-title {
        display: flex;
        justify-content: space-between;
      }

      .productContent__side-tooltips {
        width: 48px;

        .v-btn {
          box-shadow: none;
        }
      }

      .v-table {
        width: 70%;

        td {
          padding: 0;
          border: none;
          color: gray;
        }
      }
    }

    .productContent__profile {
      padding: 0.625rem 1rem;
      display: flex;
      align-items: center;
      justify-content: space-between;

      .productContent__profile-content {
        display: flex;
        align-items: center;
        gap: 20px;
      }
    }

    .v-btn {
      width: 180px;
    }
  }
}

.v-carousel {
  width: 500px;
  border-radius: 25px;
}

.v-card-actions {
  justify-content: space-between;
  .v-btn-toggle {
    height: 36px;
    border: thin solid currentColor;
  }
}

.productDetail {
  width: 940px;
  display: flex;
  flex-direction: column;
  margin-top: 100px;
  .productDetail__alert {
    margin-bottom: 30px;
    display: flex;
    justify-content: space-around;
    .v-alert {
      width: 600px;
      font-size: 20px;
      text-align: center;
    }
  }

  .productDetail__content {
    margin-top: 60px;

    .v-table {
      font-size: 20px;
    }
  }

  .productDetail__defect {
    margin-top: 60px;
    div {
      margin-top: 30px;
    }
    .productDetail__defect {
      margin-top: 0px;
      display: flex;
      gap: 20px;
      overflow: auto;
      white-space: nowrap;
    }
  }
}

.v-menu {
  .v-overlay__content {
    .v-list {
      background: rgb(var(--v-theme-mainGray));
      border-radius: 10px;
      color: white;
      padding: 0;
    }
  }
}
</style>
