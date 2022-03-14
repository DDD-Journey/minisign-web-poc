<template>
    <div class="file-upload-container">
      <input id="fileElem" type="file" multiple accept="image/*" class="visually-hidden" @change="onFileChange" />
      <label id="dropbox" for="fileElem"  class="file-upload">Click for File upload or drop a file</label>
    </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import { vxm } from "@/store";

@Options({
  components: {
  },
  props: {
    modelValue: FileList
  }
})
export default class FileUpload extends Vue {
  private files = null

  private onFileChange(event: any) {
    this.files = event.target.files || event.dataTransfer.files;
    if (this.files == null) {
      return;
    }
    this.$emit('update:modelValue', this.files);
  }

  private dragenter(event: Event) {
    event.stopPropagation();
    event.preventDefault();
  }

  private dragover(event: Event) {
    event.stopPropagation();
    event.preventDefault();
  }

  private drop(event: any) {
    event.stopPropagation();
    event.preventDefault();
    this.onFileChange(event)
  }

  mounted() {
    let dropbox;
    dropbox = document.getElementById("dropbox");
    if(dropbox) {
      dropbox.addEventListener("dragenter", this.dragenter, false);
      dropbox.addEventListener("dragover", this.dragover, false);
      dropbox.addEventListener("drop", this.drop, false);
    }
  }
}
</script>

<style scoped lang="scss">
  .file-upload-container {
    padding-top: 50px;
    height: 50px;
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
    border: solid 1px darkgray;
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
</style>