<template>
  <div
    class="dropzone"
    @dragover="onDragOver"
    @dragleave="onDragLeave"
    @dragenter="onDragEnter"
    @drop="onDrop"
    v-bind:class="{ hightlight: hightlight }"
  >
    <div class="dropzone-text">Drag and Drop to upload</div>
    <input
      id="fileElem"
      type="file"
      multiple
      accept="image/*"
      class="visually-hidden"
      @change="onFileChange"
    />
    <label for="fileElem" class="dropzone-button"> or, browse files </label>
    <div class="dropzone-filename-box">
      {{ fileName }}
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {},
  props: {
    modelValue: FileList,
  },
})
// https://malcoded.com/posts/vue-file-upload-ts/ Good example
export default class FileUpload extends Vue {
  private files!: FileList;
  private fileName = '';
  private hightlight = false;

  private onFileChange(event: any) {
    this.files = event.target.files || event.dataTransfer.files;
    if (this.files) {
      this.fileName = this.files[0].name;
      this.$emit('update:modelValue', this.files);
    }
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
    this.onFileChange(event);
  }
}
</script>

<style scoped lang="scss">
.dropzone {
  width: 300px;
  height: 200px;
  outline: 2px dashed grey; /* the dash box */
  outline-offset: -10px;
  background: #ecf0f9;
  color: dimgray;
  padding: 30px;
  text-align: center;
}
.dropzone-text {
  margin-top: 50px;
  margin-bottom: 50px;
}
.hightlight {
  background-color: #c5d3ed;
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
.dropzone-button {
  padding: 8px 12px;
  margin-top: 50px;
  background-color: #3d6ec2;
  border: solid 1px lightgray;
  border-radius: 5px;
  color: white;
  cursor: pointer;
}
.dropzone-button:hover {
  background-color: #31589b;
}
.dropzone-filename-box {
  margin-top: 20px;
  padding: 5px;
}
</style>
