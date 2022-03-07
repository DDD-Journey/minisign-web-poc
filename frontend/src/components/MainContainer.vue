<template>
  <div>
    <h1>{{ msg }}</h1>
    <p>
      Choose one of the action below by clicking on it.
    </p>
    <div class="actionBox">
      <div class="action"><router-link to="/create">Create a key pair</router-link></div>
      <div class="action"><router-link to="/sign">Sign a document</router-link></div>
      <div class="action"><router-link to="/verify">Verify a document signing</router-link></div>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import FileUpload from "@/components/FileUpload.vue";

@Options({
  components: {
    FileUpload,
  },
  props: {
    msg: String
  }
})
export default class MainContainer extends Vue {
  msg!: string;
}
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
.action {
  padding: 10px;
  height: 100px;
  width: 200px;
  border: solid 1px lightgray;
  box-shadow: 5px 5px 10px silver;
  border-radius: 10px;
  background-color: cornsilk;
  text-align: center;
  justify-content: center;
}
h1 {
  margin: 40px 0 0;
  text-shadow: 3px 3px 4px #777
}
a {
  color: #42b983;
}
</style>
