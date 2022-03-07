<template>
  <div>
    <h1>Sign a document</h1>
    <p>
      Some description
    </p>
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
  props: {}
})
export default class SignDocument extends Vue {
}
</script>

<style scoped>
h3 {
  margin: 40px 0 0;
}
a {
  color: #42b983;
}
</style>