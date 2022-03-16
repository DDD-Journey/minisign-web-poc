<template>
  <div>
    <h1>Sign a document</h1>
    <p>
      Some description
    </p>
    <file-upload v-model="verifyModule.files" />
    <Button @click="verifyFile" label="Verify Document" />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import FileUpload from "@/components/FileUpload.vue";
import Button from "@/components/Button.vue";
import { vxm } from "@/store";

@Options({
  components: {
    FileUpload,
    Button
  },
  props: {}
})
export default class VerifyDocument extends Vue {
  private verifyModule = vxm.verify;

  private verifyFile() {
    this.verifyModule.verifyFile()
  }
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
