// import Vue from "vue";
// import Vuex from "vuex";
// // import createPersistedState from "vuex-persistedstate";

// // export default new Vuex.Store({
// //   state: {},
// //   getters: {},
// //   mutations: {},
// //   actions: {},
// //   modules: {},
// // });

// Vue.use(Vuex);

// const store = new Vuex.Store({
//   // plugins: [
//   //   createPersistedState({
//   //     storage: window.sessionStorage, //TODO Use sessionStorage.clear(); when user logs out manually.
//   //   }),
//   // ],

//   state: {
//     loggedUsername: "peraperic",
//   },

//   actions: {
//     changeLoggedUsername({ commit }, newUsername) {
//       console.log("pera");
//       commit("SET_USERNAME", newUsername);
//     },
//   },
//   mutations: {
//     SET_USERNAME(state, username) {
//       state.loggedUsername = username;
//     },
//   },

//   getters: {
//     getLoggedUsername: (state) => {
//       return state.loggedUsername;
//     },
//   },
// });

// export default store;
