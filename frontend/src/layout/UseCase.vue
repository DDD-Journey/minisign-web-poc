<template>
  <main class="usecase-layout">
    <router-view />
  </main>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {},
  props: {},
})
export default class UseCase extends Vue {}
</script>

<style lang="scss">
main.usecase-layout {
  display: grid;
  grid-template-columns: 20% auto;
}
section.usecase-section {
  padding: 20px 60px;
  text-align: left;
}
aside.usecase-aside {
  padding: 20px 30px;
  background-color: #eaeaea;
  text-align: left;
}
</style>
