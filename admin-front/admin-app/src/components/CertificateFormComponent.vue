<template>
  <div class="row">
    <div class="col-3"></div>
    <div class="col-5">
      <form class="myForm form" @submit="onSubmitAction">
        <div class="mb-3">
          <label class="form-label">Email address</label>
          <input
            type="email"
            class="form-control"
            placeholder="Email address"
            required
            v-model="csrData.email"
          />
        </div>
        <div class="mb-3">
          <label class="form-label">Common name</label>
          <input
            type="text"
            class="form-control"
            placeholder="Common name"
            required
            v-model="csrData.commonName"
          />
        </div>
        <div class="mb-3">
          <label class="form-label">Organization</label>
          <input
            type="text"
            class="form-control"
            placeholder="Organization"
            required
            v-model="csrData.organization"
          />
        </div>
        <div class="mb-3">
          <label class="form-label">Organization unit</label>
          <select @change="onChange($event)" class="form-select form-control">
            <option value="IT">IT</option>
            <option value="IT Services">IT Services</option>
            <option value="IT Department">IT Department</option>
          </select>
        </div>
        <div class="mb-3">
          <label class="form-label">Country</label>
          <input
            type="text"
            class="form-control"
            placeholder="Country"
            required
            v-model="csrData.country"
          />
        </div>
        <div class="mb-3">
          <label class="form-label">State</label>
          <input
            type="text"
            class="form-control"
            placeholder="State"
            required
            v-model="csrData.state"
          />
        </div>
        <div class="mb-3">
          <label class="form-label">City</label>
          <input
            type="text"
            class="form-control"
            placeholder="City"
            required
            v-model="csrData.city"
          />
        </div>
        <input
          id="tuga"
          hidden
          class="btn btn-success"
          type="submit"
          style="margin-top: 10px"
        />
      </form>
    </div>
  </div>
</template>

<script>
import $ from "jquery";
export default {
  props: ["csr"],
  methods: {
    onSubmitAction: function () {
      this.$emit("submit", this.csrData);
    },
    clickTugaButton: function () {
      $("#tuga").click();
    },
    onChange: function (e) {
      this.csrData.organizationUnit = e.target.value;
    },
  },
  computed: {
    csrData() {
      return { ...this.csr };
    },
  },
};
</script>
<style>
.myForm {
  margin-top: 8%;
  transform: translate(70px);
}
</style>
