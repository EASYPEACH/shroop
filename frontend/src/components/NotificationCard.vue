<template>
  <div class="mx-auto notification-card">
    <div class="title-wrap">
      <div class="icon-wrap">
        <div>
          <v-icon icon="mdi-bell"></v-icon>
        </div>
        <h3>{{ props.title }}</h3>
      </div>

      <v-btn>
        <v-icon icon="mdi-close"></v-icon>
      </v-btn>
    </div>
    <p>
      {{ props.content }}
    </p>
  </div>
</template>

<script setup>
const props = defineProps({
  title: String,
  content: String,
});
</script>

<style lang="scss" scoped>
.notification-card {
  box-shadow: none;
  border-bottom: 1px solid rgba(var(--v-theme-mainGray), 0.2);
  padding: 10px 0;
  .title-wrap {
    display: flex;
    align-items: center;
    padding-bottom: 5px;
    justify-content: space-between;

    .icon-wrap {
      display: flex;
      align-items: center;
      gap: 10px;
      > div {
        width: 30px;
        aspect-ratio: 1 / 1;
        background: rgba(var(--v-theme-mainGray));
        display: flex;
        justify-content: center;
        align-items: center;
        border-radius: 50%;
        .mdi {
          color: rgba(var(--v-theme-background));
        }
      }
    }
  }
  h3 {
    font-size: 15px;
    font-weight: 600;
  }
}
</style>
