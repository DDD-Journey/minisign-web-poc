<template>
  <header>
    <Header />
  </header>
  <main>
    <router-view/>
  </main>
  <footer>
   <Footer />
  </footer>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import Header from "@/views/Header.vue";
import Footer from "@/views/Footer.vue";

@Options({
  components: {
    Header,
    Footer
  }
})
export default class extends Vue {
}
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
header {
  background-image: linear-gradient(to right top, #00497b, #005188, #005995, #0061a2, #0069b0);
  padding: 20px;
  text-align: center;
  font-size: 35px;
  color: white;
  display: grid;
  grid-template-columns: 300px auto;
}
main {
  display: grid;
  grid-template-columns: 20% auto;
}
section {
  padding: 20px;
}
aside {
  padding: 20px;
  background-color: #eaeaea;
  text-align: left;
}
footer {
  background-color: #575759;
  padding: 10px 10px 10px 30px;
  text-align: left;
  color: white;
}
</style>
