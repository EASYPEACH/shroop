<template>
  <section>
    <slot />
  </section>
</template>

<style lang="scss" scoped>
section {
  width: 70%;
  margin: 0 auto;
  padding-top: 50px;
  overflow: visible;

  @media (max-width: 960px) {
    padding-top: 50px;
    width: 92%;
  }
}
</style>
