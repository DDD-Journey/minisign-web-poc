<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <p>
      For the Project Repository,<br>
      check out the
      <a href="https://github.com/DDD-Journey/minisign-web-poc" target="_blank" rel="noopener">Github Project of the Minisign-Web PoC</a>.
    </p>
    <h3>First Vue Component to Upload a file</h3>
    <file-upload />
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
export default class HelloWorld extends Vue {
  msg!: string;
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
h3 {
  margin: 40px 0 0;
}
a {
  color: #42b983;
}
</style>
