<template>
  <aside>
  </aside>
  <section>
    <h1>Welcome to Minisign-Web PoC</h1>
    <p>
      Choose one of the action below by clicking on it.
    </p>
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
  components: {
  },
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
h1 {
  text-shadow: 3px 3px 4px #777
}
a:link, a:visited {
  display: block;
  padding: 10px;
  height: 100px;
  width: 200px;
  color: #42b983;
  border: solid 1px lightgray;
  box-shadow: 5px 5px 10px silver;
  border-radius: 10px;
  background-color: cornsilk;
  text-align: center;
  text-decoration: none;
}
a:hover, a:active {
  box-shadow: 5px 5px 10px silver inset;
}
</style>
