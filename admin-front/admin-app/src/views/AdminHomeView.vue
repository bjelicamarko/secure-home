<template>
  <div class="top-margin">
    <h2>Verified certificate signing requests</h2>
    <CSRequestsComponent :csrs="csrs"></CSRequestsComponent>
  </div>
</template>

<script>
import CSRService from "../service/CSRService.js";
import CSRequestsComponent from "../components/CSRequestsComponent.vue";
export default {
  components: { CSRequestsComponent },
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
};
</script>

<style>
.top-margin {
  margin-top: 6%;
}
</style>
