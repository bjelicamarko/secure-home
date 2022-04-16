<template>
  <div class="topnav">
    <router-link to="/">Login</router-link>
    <router-link to="/certificate-signing-request">Send CSR</router-link>
    <router-link to="/get-aliases">Aliases</router-link>
    <router-link to="/admin-home">Home Page</router-link>
    <router-link id="logoutNav" to="#" @click="logout()">Logout</router-link>
  </div>
</template>

<script>
export default {
  name: "NavigationBar",

  data() {
    return {};
  },

  props: {},

  methods: {
    homeRedirect: function () {
      //TODO: promeni putanju kasnije kada budu kreirane kommponente
      if (localStorage.getItem("USER_TYPE") === "PHARMACY_ADMIN")
        this.$router.push({ name: "PharmacyView" }).catch((err) => {
          // Ignore the vuex err regarding  navigating to the page they are already on.
          if (err.name != "NavigationDuplicated") {
            // But print any other errors to the console
            console.error(err);
          }
        });
      else
        this.$router.push({ name: "Home" }).catch((err) => {
          // Ignore the vuex err regarding  navigating to the page they are already on.
          if (err.name != "NavigationDuplicated") {
            // But print any other errors to the console
            console.error(err);
          }
        });
    },

    loginRedirect: function () {
      //TODO: promeni putanju kasnije kada budu kreirane kommponente

      this.$router.push({ name: "LoginPage" }).catch((err) => {
        // Ignore the vuex err regarding  navigating to the page they are already on.
        if (err.name != "NavigationDuplicated") {
          // But print any other errors to the console
          console.error(err);
        }
      });
    },

    registerRedirect: function () {
      //TODO: promeni putanju kasnije kada budu kreirane kommponente

      this.$router.push({ name: "UserRegistrationPage" }).catch((err) => {
        // Ignore the vuex err regarding  navigating to the page they are already on.
        if (err.name != "NavigationDuplicated") {
          // But print any other errors to the console
          console.error(err);
        }
      });
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.topnav {
  background-color: rgba(15, 95, 72, 1);
  overflow: hidden;
  width: 100%;
  position: fixed;
  top: 0;
  z-index: 999;
  height: 50px;
}

.topnav a {
  float: left;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 17px;
  z-index: 999;
  max-height: 100%;
}

.topnav a:hover {
  background-color: #ddd;
  color: rgba(10, 66, 50, 0.8);
}

.topnav a.active {
  background-color: #272327;
  color: white;
}

#logoutNav {
  float: right;
}
</style>
