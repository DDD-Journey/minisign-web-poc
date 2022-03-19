<template>
  <header>
    <div class="logo">Minisign-WEB PoC</div>
    <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/create">Create a key pair</router-link> |
      <router-link to="/sign">Sign a document</router-link> |
      <router-link to="/verify">Verify a document signing</router-link> |
      <router-link to="/about">About</router-link>
    </nav>
  </header>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {},
  props: {},
})
export default class extends Vue {}
</script>

<style scoped lang="scss">
header {
  background-image: linear-gradient(
          to right top,
          #00497b,
          #005188,
          #005995,
          #0061a2,
          #0069b0
  );
  padding: 20px;
  text-align: center;
  font-size: 35px;
  color: white;
  display: grid;
  grid-template-columns: 300px auto;
}
nav {
  a {
    color: white;
    text-decoration: none;
    &.router-link-exact-active {
      color: #a9c4ef;
    }
  }
}
.logo {
  text-shadow: 3px 3px 4px #282a35;
}
</style>
