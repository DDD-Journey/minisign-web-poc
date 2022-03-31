<template>
  <aside class="usecase-aside">
    <h1>Information</h1>
    <p>
      Upload the document, the signing file and the public key of the signer.
      Immediately after sending you will receive a response whether the signing
      is valid or not.
    </p>
    <h1>Procedure</h1>
    <ul>
      <li>Upload a ZIP File</li>
      <li>Click "Verify Document" Button</li>
    </ul>
  </aside>
  <section class="usecase-section">
    <h1>Sign a document</h1>
    <form @submit.prevent="verifyFile">
      <file-upload
        v-model="verifyModule.documentFiles"
        title="Document"
        id="documentFiles"
      />
      <br />
      <file-upload
        v-model="verifyModule.signatureFiles"
        title="Signatur"
        id="signatureFiles"
      />
      <br />
      <file-upload
        v-model="verifyModule.publicKeyFiles"
        title="Public Key"
        id="publicKeyFiles"
      />
      <div class="submit-container">
        <Button label="Verify Document" />
      </div>
      <div v-if="verifyModule.isVerified">
        <h2>Result</h2>
        <p>{{ verifyModule.verificationResult }}</p>
      </div>
    </form>
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
