<template>
  <header>
    <div class="logo">
      <router-link to="/" class="logo-link">Minisign-WEB PoC</router-link>
    </div>
    <nav>
      <router-link to="/create" class="nav-link">Create Keys</router-link>
      <router-link to="/sign" class="nav-link">Sign</router-link>
      <router-link to="/verify" class="nav-link">Verify</router-link>
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
  //background-image: linear-gradient(to right top, #000428, #031a40, #062a5a, #073b75, #004e92);
  background-color: #294982;
  display: flex;
}

div.logo {
  font-size: 35px;
  padding: 15px 0px;
  text-shadow: 3px 3px 4px #282a35;
  flex: 0 1 20%;
}
nav {
  display: flex;
  a {
    width: 150px;
    display: flex;
    justify-content: center;
    align-items: center;
    color: white;
    text-decoration: none;
    border: 1px solid #1f3761;
    &.router-link-exact-active {
      background-color: #1f3761;
      color: #a9c4ef;
    }
  }
}

.nav-link:hover {
  background-color: #1f3761;
  color: #a9c4ef;
}

a.logo-link:link,
a.logo-link:visited,
a.logo-link:hover,
a.logo-link:active {
  color: white;
  text-decoration: none;
}
.box {
  //background-color: #575759;
}
</style>
