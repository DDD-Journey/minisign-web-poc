<template>
  <div>
    <label :for="id">Password: </label>
    <input
      :type="passwordFieldType"
      :id="id"
      :value="modelValue"
      @input="updateValue"
      class="pwInput"
    />
    <button @click="switchVisibility" class="showButton">show / hide</button>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {},
  props: {
    id: String,
    modelValue: String,
  },
})
export default class Password extends Vue {
  private passwordFieldType = 'password';

  private switchVisibility() {
    this.passwordFieldType =
      this.passwordFieldType === 'password' ? 'text' : 'password';
  }

  private updateValue(event: any) {
    this.$emit('update:modelValue', event.target.value);
  }
}
</script>

<style scoped>
.pwInput {
  padding: 8px;
  border: 1px solid lightgray;
  border-radius: 5px;
}
.showButton {
  margin-left: 2px;
  padding: 8px;
  border: 1px solid lightgray;
  border-radius: 5px;
}
.showButton:hover {
  background-color: #f2f2f2;
  cursor: pointer;
}
</style>
