<template>
  <main class="common-layout">
    <router-view />
  </main>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {},
  props: {},
})
export default class Common extends Vue {}
</script>

<style lang="scss">
main.common-layout {
  display: grid;
  grid-template-columns: auto;
}
section.common-section {
  text-align: center;
}
</style>
