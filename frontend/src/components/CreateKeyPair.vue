<template>
  <div>
    <h1>Create a key Pair</h1>
    <p>
      Enter a password and click for action
    </p>
    <div class="outerForm">
      <div class="form">
        <label for="password">Password</label>
        <br />
        <input :type="passwordFieldType" id="password" v-model="keys.password"/>
        <button @click="switchVisibility">show / hide</button>
        <br /><br />
        <button @click="crateKeyPair" class="button">Create Key Pair</button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import { vxm } from "@/store";

@Options({
  components: {},
  props: {}
})
export default class CreateKeyPair extends Vue {

  private keys = vxm.keys;
  private passwordFieldType = "password"

  private switchVisibility() {
    this.passwordFieldType = this.passwordFieldType === "password" ? "text" : "password";
  }

  private crateKeyPair() {
    vxm.keys.createPrivateKeys();
  }
}
</script>

<style scoped>
h1 {
  margin: 40px 0 0;
}
.outerForm {
  display: flex;
  align-items: center;
  justify-content: center;
}
.form {
  text-align: left;
}
.button {
  padding: 10px;
  background-color: cornsilk;
  border: solid 1px lightgray;
  border-radius: 5px;
}
</style>