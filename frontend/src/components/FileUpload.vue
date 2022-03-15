<template>
    <div class="file-upload-container">
      <input id="fileElem" type="file" multiple accept="image/*" class="visually-hidden" @change="onFileChange" />
      <label
          id="dropbox"
          for="fileElem"
          class="dropzone"
          @dragover="onDragOver"
          @dragleave="onDragLeave"
          @dragenter="onDragEnter"
          @drop="onDrop"
          v-bind:class="{hightlight: hightlight, disabled: !enabled}"
      >
        Click for File upload or drop a file
      </label>
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
// https://malcoded.com/posts/vue-file-upload-ts/ Good example
export default class FileUpload extends Vue {
  private files = null;
  hightlight = false;
  enabled = true;

  private onFileChange(event: any) {
    this.files = event.target.files || event.dataTransfer.files;
    if (this.files == null) {
      return;
    }
    this.$emit('update:modelValue', this.files);
  }

  private onDragEnter(event: DragEvent) {
    event.stopPropagation();
    event.preventDefault();
  }

  private onDragOver(event: DragEvent) {
    event.stopPropagation();
    event.preventDefault();
    this.hightlight = true;
  }

  private onDragLeave(event: DragEvent): void {
    event.stopPropagation();
    event.preventDefault();
    this.hightlight = false;
  }

  private onDrop(event: DragEvent) {
    event.stopPropagation();
    event.preventDefault();
    this.onFileChange(event)
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
  .dropzone {
    outline: 2px dashed grey; /* the dash box */
    outline-offset: -10px;
    background: lightcyan;
    color: dimgray;
    padding: 30px;
    height: 200px;
    cursor: pointer;
  }
  .dropzone:hover {
    background: #b3dbc9; /* when mouse over to the drop zone, change color */
  }
  .hightlight {
    background-color: #b3dbc9;
  }

</style>
