<template>
  <div class="top-margin">
    <h2>Certificate signing</h2>
  </div>
</template>

<script>
export default {
  data() {
    return {
      csrId: "",
    };
  },
  mounted() {
    this.csrId = this.$route.params.id;
    // Ucitaj csr
  },
};
</script>

<style>
.top-margin {
  margin-top: 6%;
}
</style>
