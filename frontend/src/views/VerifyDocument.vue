<template>
  <aside>
    <h1>Information</h1>
    <p>Wofür ist diese Funktion und was muss ich tun</p>
  </aside>
  <section>
    <h1>Sign a document</h1>
    <p>Some description</p>
    <file-upload v-model="verifyModule.files" />
    <div class="submit-container">
      <Button @click="verifyFile" label="Verify Document" />
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
export default class VerifyDocument extends Vue {
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
