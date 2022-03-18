<template>
  <aside>
    <h1>Information</h1>
    <p>
      Upload the document and the private key in a ZIP file here and enter the
      password for the private key to create a signing file. You can then
      download this signing file. Now you can share your document with the
      signing file and the public key. It is advisable to transfer the public
      key in another way.
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
  <section>
    <h1>Sign a document</h1>
    <FileUpload v-model="signModule.files" />
    <br>
    <Password id="password" v-model="signModule.password" />
    <div class="submit-container">
      <Button @click="signFile" label="Sign Document" />
    </div>
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
export default class SignDocument extends Vue {
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
