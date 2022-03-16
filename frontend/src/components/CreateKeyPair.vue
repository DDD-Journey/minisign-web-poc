<template>
  <div>
    <h1>Create a key Pair</h1>
    <p>
      Enter a password and click for action
    </p>
    <div class="outerForm">
      <div class="form">
        <Password id="password" v-model="keysModule.password" />
        <br /><br />
        <Button @click="crateKeyPair" label="Create Key Pair" />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import { vxm } from "@/store";
import Password from "@/components/Password.vue";
import Button from "@/components/Button.vue";

@Options({
  components: {
    Password,
    Button
  },
  props: {}
})
export default class CreateKeyPair extends Vue {

  private keysModule = vxm.keys;

  private crateKeyPair() {
    this.keysModule.createPrivateKeys();
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
