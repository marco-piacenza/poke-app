<template>
  <div class="hello">
    <h1>{{ hellomsg }}</h1>
    
  </div>
</template>

<script>
export default {
  name: 'Landing',
  props: {
    hellomsg: {
      type: String,
      required: true }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
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
