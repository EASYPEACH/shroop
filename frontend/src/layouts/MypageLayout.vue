<template>
  <section>
    <slot />
  </section>
</template>

<style lang="scss" scoped>
section {
  width: 77%;
  margin: 0 auto;

  @media (max-width: 960px) {
    width: 92%;
  }
}
</style>
