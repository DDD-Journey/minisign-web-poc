<template>
  <div>
    <input type="file" @change="onFileChange">
    <div v-if="files">
      <h4>File Information</h4>
      <ul>
        <li>name: {{ files[0].name }}</li>
        <li>size: {{ files[0].size }}</li>
        <li>type: {{ files[0].type }}</li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from 'vue-class-component';

export default class FileUpload extends Vue {
  files = null;

  onFileChange(event: any) {
    this.files = event.target.files || event.dataTransfer.files;
    if (this.files == null) {
      return;
    }
    console.log(this.files);
  }
}
</script>

<style scoped lang="scss">
h4 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  margin: 0 10px;
}
</style>