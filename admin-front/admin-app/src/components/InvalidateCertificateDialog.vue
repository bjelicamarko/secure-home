<template>
  <div>
    <el-dialog
      title="Invalidate certificate"
      :visible.sync="centerDialogVisible"
      width="40%"
      center
    >
      <h4>Possible reasons:</h4>
      <ul>
        <li
          v-for="reasonItem in reasons"
          :key="reasonItem"
          @click="setReason(reasonItem)"
          style="cursor: pointer"
        >
          {{ reasonItem }}
        </li>
      </ul>
      <div class="form-floating mb-3">
        <input
          type="text"
          class="form-control"
          id="floatingInput"
          v-model="reason"
        />
        <label for="floatingInput">Reason</label>
      </div>
      <span>
        <el-button class="command-button" @click="closeModal"
          >Invalidate</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  props: ["certificates"],
  data() {
    return {
      reasons: [
        "Loss of a private key corresponding to the public key on the certificate",
        "Unintentional issuance of a certificate as a result of an error or attack on a certification body",
        "Improper conduct of the owner of the issued certificate",
        "Detection of defects in the application for a certificate subsequently",
      ],
      centerDialogVisible: false,
      reason: "",
    };
  },
  methods: {
    closeModal: function () {
      this.centerDialogVisible = false;
      this.$emit("closed", this.reason);
    },
    openModal: function () {
      this.reason = "";
      this.centerDialogVisible = true;
    },
    setReason(reasonItem) {
      this.reason = reasonItem;
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
