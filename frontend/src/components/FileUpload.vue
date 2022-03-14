<template>
  <div>
    <div class="file-upload-container">
      <input type="file" id="fileElem" multiple accept="image/*" class="visually-hidden" @change="onFileChange" />
      <label for="fileElem" class="file-upload">Click for File upload</label>
      <div id="dropbox">Dropzone</div>
    </div>
    <div v-if="files" class="file-info">
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
.file-upload-container {
  padding-top: 50px;
}
.visually-hidden {
  position: absolute !important;
  height: 1px;
  width: 1px;
  overflow: hidden;
  clip: rect(1px, 1px, 1px, 1px);
}
/* Separate rule for compatibility, :focus-within is required on modern Firefox and Chrome */
input.visually-hidden:focus + label {
  outline: thin dotted;
}
input.visually-hidden:focus-within + label {
  outline: thin dotted;
}
.file-upload {
  border: solid 1px black;
  outline: thin dotted;
  padding: 20px;
  background: lightgray;
}

#dropbox {
  width: 100px;
  height: 100px;
  padding: 20px;
  border: solid 1px black;
  outline: thin dotted;
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