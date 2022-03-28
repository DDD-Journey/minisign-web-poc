<template>
  <section class="common-section">
    <h1>Welcome to Minisign-Web PoC</h1>
    <img src="../assets/letter-576461_1280.png" height="300" alt="signletter"/>
    <p>Choose one of the action below by clicking on it.</p>
    <div class="actionBox">
      <router-link to="/create">Create a key pair</router-link>
      <router-link to="/sign">Sign a document</router-link>
      <router-link to="/verify">Verify a document signing</router-link>
    </div>
  </section>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {},
})
export default class HomeView extends Vue {}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.actionBox {
  display: flex;
  justify-content: center;
  align-content: space-between;
  column-gap: 6em;
  padding-top: 50px;
}
a:link,
a:visited {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px;
  height: 100px;
  width: 200px;
  color: white;
  border: solid 1px lightgray;
  box-shadow: 5px 5px 10px lightgray;
  border-radius: 10px;
  background-color: #517cc8;
  text-align: center;
  text-decoration: none;
}
a:hover,
a:active {
  box-shadow: 5px 5px 10px #0070ad inset;
}
</style>
