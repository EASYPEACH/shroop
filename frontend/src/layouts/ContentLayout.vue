<template>
  <section>
    <slot />
  </section>
</template>

<style lang="scss" scoped>
section {
  width: 70%;
  margin: 0 auto;

  @media (max-width: 960px) {
    width: 90%;
  }
}
</style>
