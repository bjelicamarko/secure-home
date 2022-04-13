<template>
  <div class="top-margin">
    <h2>Verified certificate signing requests</h2>
    <CSRequestsComponent></CSRequestsComponent>
  </div>
</template>

<script>
import CSRequestsComponent from "../components/CSRequestsComponent.vue";
export default {
  components: { CSRequestsComponent },
};
</script>

<style>
.top-margin {
  margin-top: 6%;
}
</style>
