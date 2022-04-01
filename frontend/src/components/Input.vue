<template>
  <div>
    <label :for="id">{{ label }}: </label>
    <input
      type="text"
      :id="id"
      :value="modelValue"
      @input="updateValue"
      class="txt-input"
      required
      minlength="3"
    />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {},
  props: {
    id: {
      type: String,
      required: true,
    },
    modelValue: {
      type: String,
      required: true,
    },
    label: {
      type: String,
      required: true,
    },
  },
})
export default class Password extends Vue {
  private updateValue(event: any) {
    this.$emit('update:modelValue', event.target.value);
  }
}
</script>

<style scoped>
.txt-input {
  padding: 8px;
  border: 1px solid lightgray;
  border-radius: 5px;
}
.txt-input:focus:invalid {
  padding: 8px;
  border: 1px solid darkred;
  border-radius: 5px;
}
</style>
