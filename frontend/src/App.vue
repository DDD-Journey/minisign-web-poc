<template>
  <Header />
  <router-view />
  <footer>
    <Footer />
  </footer>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import Header from '@/views/Header.vue';
import Footer from '@/views/Footer.vue';

@Options({
  components: {
    Header,
    Footer,
  },
})
export default class extends Vue {}
</script>

<style lang="scss">
// https://kulturbanause.de/blog/responsive-sticky-footer/
html {
  height: 100%;
}
body {
  height: 100%;
  margin: 0 auto;
}
#app {
  font-family: 'Roboto', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  height: 100%;
  display: grid;
  grid-template-rows: auto 1fr auto;
}
</style>
