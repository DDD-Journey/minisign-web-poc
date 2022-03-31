<template>
  <div>
    <label :for="id">Password: </label>
    <input
      :type="passwordFieldType"
      :id="id"
      :value="modelValue"
      @input="updateValue"
      class="pw-input"
      required
      minlength="3"
    />
    <button @click="switchVisibility" class="pw-btn-show-hide">show / hide</button>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {},
  props: {
    id: {
      type: String,
      required: true
    },
    modelValue: {
      type: String,
      required: true
    },
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
.pw-input:valid {
  padding: 8px;
  border: 1px solid lightgray;
  border-radius: 5px;
}
. pw-input:invalid {
  padding: 8px;
  border: 1px solid darkred;
  border-radius: 5px;
}
.pw-btn-show-hide {
  margin-left: 2px;
  padding: 8px;
  border: 1px solid lightgray;
  border-radius: 5px;
}
.pw-btn-show-hide:hover {
  background-color: #bfbfbf;
  cursor: pointer;
}
</style>
