<template>
  <div class="top-margin">
    <table class="table">
      <thead class="table-light">
        <th>Common name</th>
        <th>Organization</th>
        <th>Email</th>
        <th>Country</th>
        <th></th>
      </thead>
      <tbody>
        <tr v-for="csr in csrs" :key="csr.email">
          <td>{{ csr.commonName }}</td>
          <td>{{ csr.organization }}</td>
          <td>{{ csr.email }}</td>
          <td>{{ csr.country }}</td>
          <td>
            <button
              type="button"
              class="btn command-button"
              @click="examine(csr.id)"
            >
              Examine
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import RedirectService from "../service/RedirectService.js";
export default {
  props: ["csrs"],
  methods: {
    examine: function (id) {
      RedirectService.redirectToUrl(this.$router, "certificate-signing/" + id);
    },
  },
};
</script>

<style scoped>
.command-button {
  margin-top: 1%;
  background-color: rgb(22, 117, 90);
  color: white;
}

.command-button:hover {
  background-color: rgb(146, 142, 142);
  color: rgb(0, 0, 0);
}
</style>
