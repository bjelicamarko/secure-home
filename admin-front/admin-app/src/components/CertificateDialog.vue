<template>
  <div>
    <el-dialog
      title="Chain of selected certificate"
      :visible.sync="centerDialogVisible"
      width="30%"
      center
    >
      <p><b>Chain: </b></p>
      <ul id="example-1">
        <li v-for="item in certificates" :key="item.issuedTo">
          <div @click="handleClick(item.issuedTo)">
            <div v-if="item.issuedTo === 'Tim asf'">
              {{ item.issuedTo }}
            </div>
            <div style="margin-left: 20px" v-if="item.issuedTo === 'Admin'">
              {{ item.issuedTo }}
            </div>
            <div
              v-if="item.issuedTo !== 'Admin' && item.issuedTo !== 'Tim asf'"
              style="margin-left: 40px"
            >
              {{ item.issuedTo }}
            </div>
          </div>
        </li>
      </ul>

      <el-dialog
        title="Certificate Information"
        :visible.sync="secondDialogVisible"
        append-to-body
        width="30%"
        center
      >
        <p><b>Issued to:</b> {{ chainCertificate.issuedTo }}</p>
        <p><b>Issued by:</b> {{ chainCertificate.issuedBy }}</p>
        <p>
          <b>Valid from</b> {{ chainCertificate.validFrom }} <b>to</b>
          {{ chainCertificate.validTo }}
        </p>
        <p>------ More Details ------</p>
        <p><b>Serial Number:</b> {{ chainCertificate.serialNumber }}</p>
        <p>
          <b>Public key algorithm:</b> {{ chainCertificate.publicKeyAlgorithm }}
        </p>
        <p><b>Name Issuer: </b> {{ chainCertificate.complexNameIssuer }}</p>
        <p><b>Name Subject: </b> {{ chainCertificate.complexNameSubject }}</p>
      </el-dialog>
    </el-dialog>
  </div>
</template>

<script>
export default {
  props: ["certificates"],
  data() {
    return {
      centerDialogVisible: false,
      secondDialogVisible: false,
      chainCertificate: "",
    };
  },
  methods: {
    handleClick: function (cert) {
      for (let i = 0; i < this.certificates.length; i++) {
        if (this.certificates[i].issuedTo === cert) {
          this.chainCertificate = this.certificates[i];
          this.secondDialogVisible = true;
        }
      }
    },
    closeModal: function () {
      this.centerDialogVisible = false;
    },
    openModal: function () {
      this.centerDialogVisible = true;
    },
  },
};
</script>
