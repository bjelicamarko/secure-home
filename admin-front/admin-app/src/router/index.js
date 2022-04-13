import Vue from "vue";
import VueRouter from "vue-router";
import LoginView from "../views/LoginView.vue";
import CSRView from "../views/CSRView.vue";
import AdminHomeView from "../views/AdminHomeView.vue";
import CertificateSigningView from "../views/CertificateSigningView.vue";

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
    path: "/admin-home",
    name: "admin-home",
    component: AdminHomeView,
  },
  {
    path: "/certificate-signing/:id",
    name: "certificate-signing",
    component: CertificateSigningView,
  },
  // {
  //   path: "/about",
  //   name: "about",
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () =>
  //     import(/* webpackChunkName: "about" */ "../component/AboutView.vue"),
  // },
];

const router = new VueRouter({
  routes,
});

export default router;
