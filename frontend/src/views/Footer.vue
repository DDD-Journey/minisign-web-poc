<template>
  <footer>
    by DDD-Journey
    <router-link to="/about" class="footer-link">About</router-link>
  </footer>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {},
  props: {},
})
export default class extends Vue {}
</script>

<style scoped lang="scss">
footer {
  background-color: #575759;
  padding: 10px 10px 10px 30px;
  text-align: left;
  color: white;
}
a.footer-link {
  color: white;
  &.router-link-exact-active {
    color: #a9c4ef;
  }
}
</style>
