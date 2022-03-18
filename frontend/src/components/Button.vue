<template>
  <div>
    <button class="button">{{ label }}</button>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {},
  props: {
    label: String,
  },
})
export default class Button extends Vue {}
</script>

<style scoped lang="scss">
.button {
  padding: 14px 20px;
  background-color: #3b85e5;
  border: solid 1px lightgray;
  border-radius: 5px;
  color: white;
  cursor: pointer;
}
.button:hover {
  background-color: #1a67cb;
}
</style>
