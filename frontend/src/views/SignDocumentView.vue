<template>
  <aside class="usecase-aside">
    <h1>Information</h1>
    <p>
      Upload the document and the private key here and enter the password for
      the private key to create a signing file. You can then download this
      signing file. Now you can share your document with the signing file and
      the public key. It is advisable to transfer the public key in another way.
    </p>
    <p>
      Don't worry, we don't store the private key. This is only a PoC. It is
      only used to sign the document.
    </p>
    <h1>Procedure</h1>
    <ul>
      <li>Upload a ZIP File</li>
      <li>Enter Password</li>
      <li>Click "Sign Document" Button</li>
    </ul>
  </aside>
  <section class="usecase-section">
    <h1>Sign a document</h1>
    <form @submit.prevent="signFile">
      <FileUpload
        v-model="signModule.documentFiles"
        title="Document"
        id="documentFiles"
      />
      <br />
      <FileUpload
        v-model="signModule.secretKeyFiles"
        title="Secret Key"
        id="secrectKexFiles"
      />
      <br />
      <Password id="password" v-model="signModule.password" />
      <div class="submit-container">
        <Button label="Sign Document" />
      </div>
    </form>
  </section>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import FileUpload from '@/components/FileUpload.vue';
import { vxm } from '@/store';
import Password from '@/components/Password.vue';
import Button from '@/components/Button.vue';

@Options({
  components: {
    FileUpload,
    Password,
    Button,
  },
  props: {},
})
export default class SignDocumentView extends Vue {
  private signModule = vxm.sign;

  private signFile() {
    this.signModule.signFile();
  }
}
</script>

<style scoped>
.submit-container {
  padding-top: 15px;
}
</style>
