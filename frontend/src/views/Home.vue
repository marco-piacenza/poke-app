<template>
  <div class="home">
    <img alt="Vue with Spring logo" src="../assets/spring-boot-vuejs-logo.png">
    <Landing hellomsg="Welcome to Poke-App!"/>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import Landing from '@/components/Landing.vue'; // @ is an alias to /src

export default defineComponent({
  name: 'Home',
  components: {
    Landing,
  },
});
</script>
