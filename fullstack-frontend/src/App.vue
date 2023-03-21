<template>
  <main>
    <div id="layout">
      <div div class="wrapper">
        <nav>
          <RouterLink to="/">Home</RouterLink> |
          <RouterLink to="/calculator">Calculator</RouterLink> |
          <RouterLink to="/contact">Contact</RouterLink>
        </nav>
      </div>
    </div>
    <RouterView />
  </main>
</template>

<style scoped>
#layout {
  font-family: Avenir, Arial, Helvetica, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>


<script>
  export default {


  }
</script>