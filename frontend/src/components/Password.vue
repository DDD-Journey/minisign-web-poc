<template>
  <div>
    <label :for="id">Password: </label>
    <input :type="passwordFieldType" :id="id" :value="modelValue" @input="updateValue"/>
    <button @click="switchVisibility">show / hide</button>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {},
  props: {
    id: String,
    modelValue: String
  }
})
export default class extends Vue {
  private passwordFieldType = "password"

  private switchVisibility() {
    this.passwordFieldType = this.passwordFieldType === "password" ? "text" : "password";
  }

  private updateValue(event: any) {
    this.$emit('update:modelValue', event.target.value);
  }
}
</script>

<style scoped>

</style>
