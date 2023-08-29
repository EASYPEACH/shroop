<template>
  <section>
    <slot />
  </section>
</template>

<style lang="scss" scoped>
section {
  width: 60%;
  margin: 0 auto;
  @media (max-width: 1200px) {
    width: 70%;
  }
  @media (max-width: 960px) {
    width: 90%;
  }
}
</style>
