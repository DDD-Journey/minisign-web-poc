<template>
  <header>
<!--    <img alt="Vue logo" src="./assets/logo.png">-->
    <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/create">Create a key pair</router-link> |
      <router-link to="/sign">Sign a document</router-link> |
      <router-link to="/verify">Verify a document signing</router-link> |
      <router-link to="/about">About</router-link>
    </nav>
  </header>
  <main>
    <router-view/>
  </main>
  <footer>
    Footer
  </footer>
</template>

<style lang="scss">
// https://kulturbanause.de/blog/responsive-sticky-footer/
html {
  height: 100%;
}
body {
  height: 100%;
  margin: 0 auto;
}
#app {
  font-family: 'Roboto', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  height: 100%;
  display: grid;
  grid-template-rows: auto 1fr auto;
}
header {
  background-image: linear-gradient(to right top, #00497b, #005188, #005995, #0061a2, #0069b0);
  padding: 20px;
  text-align: center;
  font-size: 35px;
  color: white;
}
main {
  display: grid;
  grid-template-columns: 20% auto;
}
section {
  padding: 20px;
}
aside {
  padding: 20px;
  background-color: #eaeaea;
  text-align: left;
}
footer {
  background-color: #575759;
  padding: 10px 10px 10px 30px;
  text-align: left;
  color: white;
}
nav {
  a {
    color: white;
    text-decoration: none;
    &.router-link-exact-active {
      color: #a9c4ef;
    }
  }
}
h1 {
}
</style>
