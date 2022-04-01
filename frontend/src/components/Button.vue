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
    label: {
      type: String,
      required: true,
    },
  },
})
export default class Button extends Vue {}
</script>

<style scoped lang="scss">
.button {
  padding: 14px 20px;
  background-color: #3d6ec2;
  border: solid 1px lightgray;
  border-radius: 5px;
  color: white;
  cursor: pointer;
}
.button:hover {
  background-color: #31589b;
}
</style>
