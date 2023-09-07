<template>
  <section class="page_404">
    <div class="container">
      <div class="row">
        <div class="col-sm-12">
          <div class="col-sm-10 col-sm-offset-1 text-center">
            <div class="four_zero_four_bg">
              <h1 class="text-center">404</h1>
            </div>

            <div class="contant_box_404">
              <h1 class="h2">NOT FOUND ERROR</h1>

              <p>페이지를 찾을 수 없습니다.</p>

              <router-link to="/">
                <a href="" class="link_404">홈으로</a>
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup></script>

<style lang="scss" scoped>
.page_404 {
  padding: 40px 0;
  background: #fff;
  font-family: "Arvo", serif;
}

.page_404 img {
  width: 100%;
}

.four_zero_four_bg {
  background-image: url(https://i.gifer.com/origin/78/787899e9d4e4491f797aba5c61294dfc_w200.webp);
  height: 400px;
  background-position: center;
}

.four_zero_four_bg h1 {
  font-size: 80px;
}

.four_zero_four_bg h3 {
  font-size: 80px;
}

.link_404 {
  color: #fff !important;
  padding: 10px 20px;
  background: rgb(var(--v-theme-mainGray));
  margin: 20px 0;
  display: inline-block;
}
.contant_box_404 {
  margin-top: -90px;
}

.h2 {
  font-size: 30px;
}
</style>
