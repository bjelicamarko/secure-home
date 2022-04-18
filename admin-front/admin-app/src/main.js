import Vue from "vue";
import Vuex from "vuex";
import App from "./App.vue";
import router from "./router";
// import store from "./store/index";

import VueFormGenerator from "vue-form-generator";
//import "vue-form-generator/dist/vfg.css";

import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";

import "vue2-toast/lib/toast.css";

import Toasted from "vue-toasted";

import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";

Vue.use(ElementUI);

Vue.use(Toasted, {
  duration: 9000,
  position: "top-right",
  action: {
    text: "Okay",
    onClick: (e, toastObject) => {
      toastObject.goAway(0);
    },
  },
});

Vue.use(VueFormGenerator);

Vue.config.productionTip = false;

Vue.use(Vuex);

const store = new Vuex.Store({
  // plugins: [
  //   createPersistedState({
  //     storage: window.sessionStorage, //TODO Use sessionStorage.clear(); when user logs out manually.
  //   }),
  // ],

  state: {
    loggedUsername: "peraperic",
  },

  actions: {
    changeLoggedUsername({ commit }, newUsername) {
      commit("SET_USERNAME", newUsername);
    },
  },
  mutations: {
    SET_USERNAME(state, username) {
      state.loggedUsername = username;
    },
  },

  getters: {
    getLoggedUsername: (state) => {
      return state.loggedUsername;
    },
  },
});

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
