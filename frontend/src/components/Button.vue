<template>
  <div>
    <button  class="button">{{ label }}</button>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {},
  props: {
    label: String
  }
})
export default class Button extends Vue {
}
</script>

<style scoped>
.button {
  padding: 10px;
  background-color: cornsilk;
  border: solid 1px lightgray;
  border-radius: 5px;
}
</style>
