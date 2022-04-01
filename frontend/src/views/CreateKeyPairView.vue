<template>
  <aside class="usecase-aside">
    <h1>Information</h1>
    <p>
      Here you can create a new key pair. The key pair consists of a private key
      and a public key. Enter a password for the private key and filenames for
      the private and the public key. After confirming by clicking on the button
      you will get a download containing a ZIP archive with these two keys. The
      private key has the extension key and the public ke the extenseion pub. It
      is advisable to send the signing file and the public key to the recipient
      on different ways.
    </p>
    <p>
      Keep these keys safe and make sure that nobody can access your private
      key. You need the private key to sign your documents. You send the public
      key with your signed document and the sign file to the person who wants to
      verify the signing.It is advisable to send the signing file and the public
      key to the recipient on different ways.
    </p>
    <h1>Procedure</h1>
    <ul>
      <li>Enter a password</li>
      <li>Enter a file name</li>
      <li>Click "Create Key Pair" Button</li>
    </ul>
  </aside>
  <section class="usecase-section">
    <h1>Create a key Pair</h1>
    <form @submit.prevent="crateKeyPair">
      <div class="input-box">
        <Password id="password" v-model="keysModule.password" />
      </div>
      <div class="input-box">
        <Input id="file-name" label="File Names" v-model="keysModule.fileName" />
      </div>
      <div class="submitContainer">
        <Button label="Create Key Pair" />
      </div>
    </form>
  </section>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import { vxm } from '@/store';
import Password from '@/components/Password.vue';
import Button from '@/components/Button.vue';
import Input from '@/components/Input.vue';

@Options({
  components: {
    Password,
    Button,
    Input,
  },
  props: {},
})
export default class CreateKeyPairView extends Vue {
  private keysModule = vxm.keys;

  private crateKeyPair() {
    this.keysModule.createPrivateKeys();
  }
}
</script>

<style scoped>
.submitContainer {
  padding-top: 15px;
}
.input-box {
  padding: 5px 0;
}
</style>
