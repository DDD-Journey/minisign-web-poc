<template>
  <div class="home">
    <main-container msg="Welcome to Minisign-Web PoC" />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import MainContainer from '@/components/MainContainer.vue'; // @ is an alias to /src

@Options({
  components: {
    MainContainer,
  },
})
export default class HomeView extends Vue {}
</script>
