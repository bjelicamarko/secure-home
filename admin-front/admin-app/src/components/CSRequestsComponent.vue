<template>
  <div class="top-margin">
    <table class="table">
      <thead class="table-light">
        <td>Common name</td>
        <td>Organization</td>
        <td>Email</td>
        <td>Country</td>
        <td></td>
      </thead>
      <tbody>
        <tr v-for="csr in csrs" :key="csr.email">
          <td>{{ csr.commonName }}</td>
          <td>{{ csr.organization }}</td>
          <td>{{ csr.email }}</td>
          <td>{{ csr.country }}</td>
          <td>
            <button type="button" class="btn btn-dark" @click="examine(csr.id)">
              Examine
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import CSRService from "../service/CSRService.js";
import RedirectService from "../service/RedirectService.js";
export default {
  data() {
    return {
      csrs: [],
    };
  },

  mounted() {
    CSRService.getAllVerifiedCSRs().then((response) => {
      this.csrs = response.data;
    });
  },

  methods: {
    examine: function (id) {
      RedirectService.redirectToUrl(this.$router, "certificate-signing/" + id);
    },
  },
};
</script>
