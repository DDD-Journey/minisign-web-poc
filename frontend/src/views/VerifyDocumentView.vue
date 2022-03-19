<template>
  <aside class="usecase-aside">
    <h1>Information</h1>
    <p>
      Upload the document, the signing file and the public key of the signer in
      a ZIP file. Immediately after sending you will receive a response whether
      the signing is valid or not.
    </p>
    <h1>Procedure</h1>
    <ul>
      <li>Upload a ZIP File</li>
      <li>Click "Verify Document" Button</li>
    </ul>
  </aside>
  <section class="usecase-section">
    <h1>Sign a document</h1>
    <file-upload v-model="verifyModule.files" />
    <div class="submit-container">
      <Button @click="verifyFile" label="Verify Document" />
    </div>
    <div v-if="verifyModule.isVerified">
      <h2>Result</h2>
      <p>{{ verifyModule.verificationResult }}</p>
    </div>
  </section>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import FileUpload from '@/components/FileUpload.vue';
import Button from '@/components/Button.vue';
import { vxm } from '@/store';

@Options({
  components: {
    FileUpload,
    Button,
  },
  props: {},
})
export default class VerifyDocumentView extends Vue {
  private verifyModule = vxm.verify;

  private verifyFile() {
    this.verifyModule.verifyFile();
  }
}
</script>

<style scoped>
.submit-container {
  padding-top: 15px;
}
</style>
