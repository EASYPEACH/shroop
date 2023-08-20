<template>
  <section>
    <slot />
  </section>
</template>

<style lang="scss" scoped>
section {
  width: 60%;
  margin: 0 auto;
  @media (max-width: 1080px) {
    width: 90%;
  }
}
</style>
