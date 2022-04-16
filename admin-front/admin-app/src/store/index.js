import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {},
});

// state: {
//   lastCertificateMailToSign: "",
// },
// getters: {
//   lastCertificateMailToSign: (state) => state.lastCertificateMailToSign,
// },
// mutations: {
//   setLastCertificateMailToSign(state, mail) {
//     state.lastCertificateMailToSign = mail;
//   },
// },
// actions: {},
// modules: {},

// Pristupanja i izmena
// this.$store.getters.lastCertificateMailToSign
// this.$store.commit("setLastCertificateMailToSign", this.csrMail);
