<template>
  <div>
    <h1>Create a key Pair</h1>
    <p>
      Description
    </p>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {},
  props: {}
})
export default class CreateKeyPair extends Vue {
}
</script>

<style scoped>
h3 {
  margin: 40px 0 0;
}
a {
  color: #42b983;
}
</style>