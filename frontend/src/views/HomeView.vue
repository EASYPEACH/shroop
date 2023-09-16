<template>
  <div>
    <section class="main">
      <div>
        <div>
          <h2>
            <span class="color-text service-title">슈룹</span>: <br />중고거래의
            안심우산
          </h2>
          <br />
          <p><span class="color-text">슈룹</span>은 우산의 순 우리말 입니다</p>
          <p>중고거래가 불안한 여러분께</p>
          <p>안전한 우산이 되어드리겠습니다</p>
          <v-btn @click="() => $router.push('/products')"
            >매물 보러가기 <v-icon icon="mdi-chevron-right"
          /></v-btn>
        </div>
      </div>
      <div v-if="isLaptop">
        <img src="@/assets/image/logo-white.png" alt="슈룹 로고" />
      </div>
      <div class="arrowBtn" @click="handleGoContent">
        <p>scroll</p>
        <v-btn variant="plain">
          <div>
            <span></span>
          </div>
        </v-btn>
      </div>
    </section>
    <section class="subContent sc1" ref="contentRef">
      <div class="textBox">
        <p data-aos="fade-up">
          <span class="color-text">슈룹 방울</span>과 함께 하는
        </p>
        <p data-aos="fade-up" data-aos-delay="100">
          안전 <span class="text-orange">비대면 중고거래</span>
        </p>
      </div>
      <div class="transactionBox">
        <div
          class="userBox"
          data-aos="fade-down"
          data-aos-delay="500"
          data-aos-offset="-50"
        >
          <p>판매자</p>
          <v-icon icon="mdi-account"></v-icon>
        </div>
        <v-icon
          icon="mdi-arrow-left"
          data-aos="fade-left"
          data-aos-offset="-50"
          data-aos-delay="250"
        ></v-icon>
        <div
          class="shroop"
          data-aos="fade-up"
          data-aos-offset="100"
          data-aos-delay="150"
        >
          <p>슈룹 방울</p>
          <v-icon icon="mdi-water"></v-icon>
        </div>
        <v-icon
          icon="mdi-arrow-right"
          data-aos="fade-right"
          data-aos-offset="-50"
          data-aos-delay="250"
        ></v-icon>
        <div
          class="userBox"
          data-aos="fade-down"
          data-aos-delay="500"
          data-aos-offset="-50"
        >
          <p>구매자</p>
          <v-icon icon="mdi-account"></v-icon>
        </div>
      </div>
    </section>
    <section class="subContent sc2">
      <div class="textBox" data-aos="fade-up" data-aos-offset="100">
        <span class="color-text">슈룹</span>은 <br />
        <span class="text-orange">판매자</span>와
        <span class="text-orange">구매자</span>
        모두를 보호합니다
      </div>
      <div class="reportBox">
        <div class="shroop" data-aos="flip-left" data-aos-delay="500">
          <p>슈룹</p>
          <img src="@/assets/image/logo-black.png" alt="슈룹 로고" />
        </div>
        <v-icon
          v-if="isLaptop"
          icon="mdi-arrow-right"
          data-aos="fade-right"
          data-aos-offset="50"
          data-aos-delay="600"
        ></v-icon>
        <v-icon
          v-if="!isLaptop"
          icon="mdi-arrow-down"
          data-aos="fade-down"
          data-aos-offset="50"
          data-aos-delay="500"
        ></v-icon>
        <div class="reportResultBox">
          <div
            :data-aos="isLaptop ? 'fade-right' : 'fade-up'"
            data-aos-offset="50"
            :data-aos-delay="isLaptop ? 700 : 500"
          >
            <p>
              사기 피의 사용자
              <v-icon color="red" icon="mdi-emoticon-devil-outline"></v-icon>
            </p>
            <p>
              <span class="text-red">서비스 이용 및 추가 가입 차단</span>
            </p>
          </div>
          <div
            :data-aos="isLaptop ? 'fade-right' : 'fade-up'"
            data-aos-offset="50"
            :data-aos-delay="isLaptop ? 700 : 500"
          >
            <p>
              사기 피해 사용자
              <v-icon
                color="light-blue"
                icon="mdi-emoticon-sad-outline"
              ></v-icon>
            </p>
            <p>
              <span class="text-light-blue">피해 금액 복원</span>
            </p>
          </div>
        </div>
      </div>
    </section>

    <content-layout>
      <main-title title="최근 판매" data-aos="fade-up" data-aos-offset="100" />
      <div class="products">
        <ul class="products__list">
          <li
            v-for="product in productCardData"
            :key="product.id"
            data-aos="fade-up"
            data-aos-offset="100"
          >
            <product-card
              v-if="isLaptop"
              :productCardData="product"
              @handle-click-like="() => handleClickLike(product)"
            />
            <product-banner
              v-else
              :product="product"
              @handle-click-like="() => handleClickLike(product)"
              isHeart
            />
          </li>
        </ul>
        <v-btn
          class="plusbtn"
          variant="outlined"
          color="mainGreen"
          @click="() => $router.push('/products')"
        >
          더보기 <v-icon icon="mdi-chevron-right"></v-icon>
        </v-btn>
      </div>
    </content-layout>
  </div>
</template>

<script setup>
import { onBeforeMount, ref } from "vue";
import { useDisplay } from "vuetify";
import { getApi } from "@/api/modules";
import { toggleLikesProduct } from "@/utils";
import router from "@/router";

import { MainTitle } from "@/components/Title";
import ProductCard from "@/components/ProductCard.vue";
import ContentLayout from "@/layouts/ContentLayout.vue";
import { ProductBanner } from "@/components/Banner";

const productCardData = ref([]);
const display = useDisplay();
const isLaptop = ref(display.mdAndUp);
const contentRef = ref(null);

onBeforeMount(async () => {
  try {
    const response = await getApi({
      url: `/api/products/search?size=8&page=0&hasNotTransaction=true`,
    });
    productCardData.value = response.productList;
  } catch (error) {
    console.error(error);
  }
});

// 상품 좋아요 버튼 클릭 이벤트
const handleClickLike = async (product) => {
  try {
    await toggleLikesProduct(product, productCardData);
  } catch (err) {
    if (err.response.status === 403) {
      router.push("/login");
    }
  }
};

// 컨텐트로 가는 이벤트
const handleGoContent = () => {
  contentRef.value.scrollIntoView({
    behavior: "smooth",
    block: "start",
  });
};
</script>

<style lang="scss" scoped>
h2 {
  margin-top: 50px;
}
.userBox {
  text-align: center;
  font-size: 20px;
  font-weight: 600;
  .v-icon {
    font-size: 60px;
  }
}
.color-text {
  color: rgb(var(--v-theme-subGreen));
  &.service-title {
    font-size: 55px;
  }
}

section {
  &:nth-child(1),
  &:nth-child(3) {
    background: #000;
  }

  &:nth-child(2) {
    background: #151515;
  }
  &.main {
    position: relative;
    width: 100%;
    height: 100vh;
    display: flex;
    gap: 200px;
    align-items: center;
    color: rgb(var(--v-theme-background));
    padding: 100px;
    background: #000;
    .arrowBtn {
      position: absolute;
      bottom: 80px;
      left: 50%;
      transform: translateX(-50%);
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 5px;
      @media (max-width: 750px) {
        bottom: 20px;
      }
      .v-btn {
        width: 60px;
        height: 60px;
        border-radius: 50%;
        div {
          width: 30px;
          height: 40px;
          border-radius: 20px;
          border: 1px solid #fff;
          position: relative;
          padding: 5px;
          span {
            width: 5px;
            height: 5px;
            display: inline-block;
            border-radius: 50%;
            background: #fff;
            position: absolute;
            left: 50%;
            transform: translateX(-50%);
            animation: yoyo 2s linear infinite;
          }
        }
      }

      @keyframes yoyo {
        0% {
          top: 5px;
        }
        50% {
          top: 30px;
        }
        100% {
          top: 5px;
        }
      }
    }

    h2 {
      font-weight: 600;
      font-size: 36px;
    }
    > div:nth-child(1) {
      flex-basis: 50%;
      display: flex;
      justify-content: end;
      > div {
        width: fit-content;
      }
      .v-btn {
        margin-top: 30px;
        font-weight: 600;
      }
    }
    > div:nth-child(2) {
      flex-basis: 50%;
      display: flex;
      align-items: center;
      justify-content: flex-start;
      img {
        animation: bounce 1.5s linear infinite;
      }
      @keyframes bounce {
        0% {
          transform: translateY(0) rotate(20deg);
        }
        50% {
          transform: translateY(-10px) rotate(20deg);
        }
        100% {
          transform: translateY(0) rotate(20deg);
        }
      }
    }

    @media (max-width: 960px) {
      padding: 50px 30px;
      h2 {
        word-break: keep-all;
        margin: 0;
        margin-bottom: 30px;
        font-size: 40px;
      }
      > div:nth-child(1) {
        flex-basis: 100%;
        justify-content: center;
      }
    }
  }

  &.subContent {
    color: #fff;
    width: 100%;
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 150px;

    &.sc1 {
      flex-direction: column;

      .textBox {
        font-size: 20px;
        display: flex;
        flex-direction: column;
        text-align: center;
        gap: 10px;
        span {
          font-size: 30px;
          @media (max-width: 750px) {
            font-size: 25px;
          }
        }
      }

      .transactionBox {
        display: flex;
        align-items: center;
        gap: 100px;

        .shroop {
          text-align: center;
          font-size: 20px;
          .v-icon {
            color: rgb(var(--v-theme-subGreen));
          }
        }
      }
    }

    &.sc2 {
      flex-direction: column;
      .textBox {
        font-size: 20px;
        text-align: center;
        span {
          font-size: 30px;
          @media (max-width: 750px) {
            font-size: 25px;
          }
        }
      }
      .reportBox {
        display: flex;
        gap: 100px;
        align-items: center;
        text-align: center;

        .shroop {
          p {
            color: rgb(var(--v-theme-subGreen));
            font-weight: 600;
            font-size: 20px;
          }
          img {
            margin-top: 10px;
            width: 100px;
            height: 100px;
            background: #fff;
            border-radius: 50%;
            padding: 5px;
          }
        }
      }
      .reportResultBox {
        display: flex;
        flex-direction: column;
        gap: 20px;
        > div {
          border: 1px dotted rgba(255, 255, 255, 0.5);
          padding: 10px;
          border-radius: 5px;
        }
      }
    }

    @media (max-width: 750px) {
      gap: 80px;

      &.sc1 {
        .transactionBox {
          gap: 20px;
        }
      }
      &.sc2 {
        .reportBox {
          flex-direction: column;
          gap: 20px;
        }
      }
    }
  }
}

.products {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 50px;
  .products__list {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    gap: 50px;
    li {
      flex: 1;
    }
  }
  .plusbtn {
    color: #fff;
    place-self: flex-center;
    margin-top: 30px;
    padding: 0 20px;
    font-weight: 600;
  }

  @media (max-width: 960px) {
    .products__list {
      display: flex;
      flex-direction: column;
      width: 100%;
    }
  }
}
</style>
