<template>
  <div class="service">
    <h1>{{ msg }}</h1>
    <form @submit.prevent="submitForm">
      <input v-model="pokemonSpecies" placeholder="edit me" type="text">
      <!--button @click="callApi({ pokemonSpecies })">CALL API</button-->
      <button>CALL API</button>
    </form>
    <h4>Backend response: {{ backendResponse }}</h4>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import api from "../api/backend-api";
import {AxiosError} from "axios";

interface State {
  msg: string;
  backendResponse: string;
  pokemonSpecies: string,
  errors: AxiosError[]
}

export default defineComponent({
  name: 'Service',

  data: (): State => {
    return {
      msg: 'Search a Pokemon species:',
      backendResponse: '',
      pokemonSpecies: '',
      errors: []
    }
  },
  methods: {
    // Fetches posts when the component is created.
    submitForm () {
      api.getPokemonSpeciesDescription(this.pokemonSpecies).then(response => {
          console.log(response.data)
          if (response.data.errorMessage != null) {
            this.backendResponse = response.data.errorMessage;
          } else {
            this.backendResponse = response.data.description;
          }
      })
      .catch((error: AxiosError) => {
        this.errors.push(error)
      })
    }
  }
});
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }
</style>
