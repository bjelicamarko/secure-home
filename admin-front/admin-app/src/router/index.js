import Vue from "vue";
import VueRouter from "vue-router";
import LoginView from "../views/LoginView.vue";
import CSRView from "../views/CSRView.vue";
import AllCertificatesView from "../views/AllCertificatesView.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "login",
    component: LoginView,
  },
  {
    path: "/certificate-signing-request",
    name: "csr",
    component: CSRView,
  },
  {
    path: "/get-aliases",
    name: "aliases",
    component: AllCertificatesView,
  },
];

const router = new VueRouter({
  routes,
});

export default router;
