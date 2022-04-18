<template>
  <div class="topnav">
    <router-link to="/" v-if="isUserLoggedIn()">Login</router-link>
    <router-link v-if="isUserLoggedIn()" to="/certificate-signing-request"
      >Send CSR</router-link
    >
    <router-link v-if="!isUserLoggedIn()" to="/get-aliases"
      >Aliases</router-link
    >
    <router-link v-if="!isUserLoggedIn()" to="/admin-home"
      >Home Page</router-link
    >
    <a v-if="!isUserLoggedIn()" id="logoutNav" @click="logout()">Logout</a>
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
    logout: function () {
      localStorage.clear();
      this.$store.dispatch("changeLoggedUsername", "");
      this.$router.push("/").catch((err) => {
        if (err.name != "NavigationDuplicated") {
          console.error(err);
        }
      });
    },
    isUserLoggedIn: function () {
      return this.$store.getters.getLoggedUsername === "";
    },
  },
};
</script>

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
  color: #f2f2f2 !important;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 17px;
  z-index: 999;
  max-height: 100%;
  cursor: pointer;
}

.topnav a:hover {
  background-color: rgb(146, 142, 142);
  color: rgba(0, 0, 0, 0.8) !important;
}

.topnav a.active {
  background-color: #272327;
  color: white;
}

#logoutNav {
  float: right;
}
</style>
