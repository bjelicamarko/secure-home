<template>
  <div class="top-margin">
    <h2>Verified certificate signin requests</h2>
    <table class="table" style="margin-top: 50px">
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
            <button
              type="button"
              class="btn btn-dark"
              @click="examine(csr.email)"
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
import CSRService from "../service/CSRService.js";
export default {
  data() {
    return {
      csrs: [],
    };
  },

  mounted() {
    CSRService.getAllVerifiedCSRs().then((response) => {
      this.csrs = response.data;
      console.log(response);
    });
  },

  methods: {
    examine: function (email) {
      alert(email);
    },
  },
};
</script>

<style>
.top-margin {
  margin-top: 4%;
}
</style>
