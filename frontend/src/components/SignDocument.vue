<template>
  <div>
    <h1>Sign a document</h1>
    <p>
      Upload the document, the private key and type the password for the private key.
    </p>
    <file-upload v-model="signModule.files"/>
    <button @click="signFile" class="button">Sign Document</button>
    <div v-if="signModule.files" class="file-info">
      <h4>File Information</h4>
      <ul>
        <li>name: {{ signModule.files[0].name }}</li>
        <li>size: {{ signModule.files[0].size }}</li>
        <li>type: {{ signModule.files[0].type }}</li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import FileUpload from "@/components/FileUpload.vue";
import { vxm } from "@/store";

@Options({
  components: {
    FileUpload,
  },
  props: {
  }
})
export default class SignDocument extends Vue {
  private signModule = vxm.sign;

  private signFile() {
    this.signModule.signFile();
  }
}
</script>

<style scoped>
h1 {
  margin: 40px 0 0;
}
a {
  color: #42b983;
}
.button {
  padding: 10px;
  background-color: cornsilk;
  border: solid 1px lightgray;
  border-radius: 5px;
}
.file-info {
  padding-top: 30px;
}

ul {
  list-style-type: none;
  padding: 0;
}
li {
  margin: 0 10px;
}
</style>
