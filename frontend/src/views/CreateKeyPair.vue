<template>
  <aside>
    <h1>Instruction</h1>
    <p>Wofür ist diese Funktion und was muss ich tun</p>
  </aside>
  <section>
    <h1>Create a key Pair</h1>
    <p>
      Enter a password and click for action
    </p>
    <div>
      <div>
        <Password id="2password" v-model="keysModule.password" />
        <br /><br />
        <Button @click="crateKeyPair" label="Create Key Pair" />
      </div>
    </div>
  </section>
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
</style>
