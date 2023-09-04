<template>
  <button
    :class="{ active: mobileNavStore.isMobileNav }"
    class="hamburger__button"
    @click="() => mobileNavStore.setIsMobileNav(!mobileNavStore.isMobileNav)"
  >
    <span className="bar"></span>
  </button>
</template>

<script setup>
import { useMobileNav } from "@/store/useMobileNav";

const mobileNavStore = useMobileNav();
</script>

<style lang="scss" scoped>
/* 햄버거 메뉴 */
.hamburger__button {
  position: fixed;
  right: 0;
  top: 10px;
  z-index: 20000;
  width: 40px;
  height: 40px;
  cursor: pointer;
  border-radius: 3px;

  &.active .bar {
    margin-top: 12px;
  }

  .bar {
    position: relative;
    display: block;
    width: 0px;
    height: 3px;
    border-radius: 3px;
    background-color: rgb(var(--v-theme-mainGray));
    margin-top: 28px;
    margin-left: 9px;

    &::before {
      content: "";
      width: 20px;
      height: 3px;
      border-radius: 3px;
      background-color: rgb(var(--v-theme-mainGray));
      position: absolute;
      left: 0;
      bottom: 7px;
      transition: transform 0.3s;
    }

    &::after {
      content: "";
      width: 20px;
      height: 3px;
      border-radius: 3px;
      background-color: rgb(var(--v-theme-mainGray));
      position: absolute;
      left: 0;
      bottom: 7px;
      transition: transform 0.3s;
    }
  }

  &.active span::before {
    transform: rotate(45deg);
    transition: transform 0.3s 0.3s;
    background-color: rgb(var(--v-theme-mainGray));
  }

  &.active span::after {
    transform: rotate(-45deg);
    transition: transform 0.3s 0.3s;
    background-color: rgb(var(--v-theme-mainGray));
  }

  &::before {
    content: "";
    width: 20px;
    height: 3px;
    background-color: rgb(var(--v-theme-mainGray));
    border-radius: 3px;
    position: absolute;
    right: 11px;
    bottom: 25px;
    transition: width 0.3s;
  }

  &::after {
    content: "";
    width: 20px;
    height: 3px;
    background-color: rgb(var(--v-theme-mainGray));
    border-radius: 3px;
    position: absolute;
    left: 9px;
    top: 19px;
    transition: width 0.3s;
  }

  &.active::before {
    width: 0;
    right: 9px;
    bottom: 17px;
    background-color: rgb(var(--v-theme-mainGray));
  }

  &.active::after {
    width: 0;
    left: 9px;
    top: 17px;
    background-color: rgb(var(--v-theme-mainGray));
  }
}
</style>
