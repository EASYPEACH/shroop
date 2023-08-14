<template>
  <div>
    <section class="main">
      <div>
        <div>
          <h2><span class="color-text">슈룹</span>: 중고거래의 안심우산</h2>
          <br />
          <p><span class="color-text">슈룹</span>은 우산의 순 우리말 입니다</p>
          <p>중고거래가 불안한 여러분께</p>
          <p>안전한 우산이 되어드리겠습니다</p>
        </div>
      </div>
      <div>
        <img src="@/assets/image/white_logo.png" alt="슈룹 로고" />
      </div>
    </section>
    <section>
      <Title title="최근 거래" />
      <div class="products">
        <ul class="products__list">
          <li v-for="(item, idx) in 6" :key="idx">
            <product-card-main />
          </li>
        </ul>
        <v-btn class="plusbtn" variant="text"
          >더보기 <v-icon icon="mdi-chevron-right"></v-icon
        ></v-btn>
      </div>
    </section>
  </div>
</template>

<script setup>
import Title from "@/components/Title.vue";
import ProductCardMain from "@/components/ProductCardMain.vue";
</script>

<style lang="scss" scoped>
h2 {
  margin-top: 50px;
}
.main {
  width: 100%;
  height: 100%;
  display: flex;
  gap: 50px;
  align-items: center;
  color: rgb(var(--v-theme-background));
  padding: 100px;
  background: #000;
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
        transform: translateY(0);
      }
      50% {
        transform: translateY(-10px);
      }
      100% {
        transform: translateY(0);
      }
    }
  }

  .color-text {
    color: rgb(var(--v-theme-info));
  }
}
.products {
  width: fit-content;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  .products__list {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
  }
  .plusbtn {
    color: rgb(var(--v-theme-subBlue));
    place-self: flex-end;
    margin-right: -20px;
    margin-top: 11px;
  }
}
@media (max-width: 700px) {
  .main {
    > div:nth-child(1) {
      flex: 1;
    }
    > div:nth-child(2) {
      display: none;
    }
  }
}
</style>
