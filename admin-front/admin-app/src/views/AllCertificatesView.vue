<template>
  <div>
    <DropdownComponent
      @changeItem="changeAlias"
      :items="aliases"
    ></DropdownComponent>
    <CertificateDialog ref="dialog1" :certificates="selectedCertificates">
    </CertificateDialog>
    <InvalidateCertificateDialog
      ref="dialog2"
      @closed="invalidateCertificate"
    ></InvalidateCertificateDialog>
    <el-button class="command-button" @click="openModalInvalidateCert"
      >Invalidate certificate</el-button
    >
    <el-button class="command-button" @click="openModal"
      >Open certificate</el-button
    >
    <el-button class="command-button" @click="checkCertificate"
      >Check certificate</el-button
    >
  </div>
</template>

<script>
import CertificateDialog from "../components/CertificateDialog.vue";
import InvalidateCertificateDialog from "../components/InvalidateCertificateDialog.vue";
import DropdownComponent from "../components/DropdownComponent.vue";
import CertificateService from "../service/CertificateService";
export default {
  components: {
    DropdownComponent,
    CertificateDialog,
    InvalidateCertificateDialog,
  },
  data: function () {
    return {
      aliases: [],
      invalidAlias: "----------",
      selectedAlias: "----------",
      selectedCertificates: {},
      showModal: false,
    };
  },
  mounted: function () {
    CertificateService.getAliases().then((response) => {
      this.aliases = [this.invalidAlias];
      this.aliases = [...this.aliases, ...response.data];
    });
  },
  methods: {
    changeAlias: function (alias) {
      this.selectedAlias = alias;
      CertificateService.getCertificate(this.selectedAlias).then((response) => {
        this.selectedCertificates = response.data;
      });
    },
    openModal: function () {
      if (this.selectedAlias !== this.invalidAlias)
        this.$refs.dialog1.openModal();
      else
        this.$toasted.show("Certificate not selected!", {
          theme: "toasted-primary",
          position: "top-center",
          duration: 1700,
        });
    },
    openModalInvalidateCert: function () {
      if (this.selectedAlias !== this.invalidAlias)
        this.$refs.dialog2.openModal();
      else
        this.$toasted.show("Certificate not selected!", {
          theme: "toasted-primary",
          position: "top-center",
          duration: 1700,
        });
    },
    checkCertificate: function () {
      if (this.selectedAlias === this.invalidAlias)
        this.$toasted.show("Certificate not selected!", {
          theme: "toasted-primary",
          position: "top-center",
          duration: 1700,
        });
      else
        CertificateService.checkCertificate(this.selectedAlias)
          .then((response) => {
            this.$toasted.show(response.data, {
              theme: "toasted-primary",
              position: "top-center",
              duration: 3000,
            });
          })
          .catch((response) => console.log(response.data));
    },
    invalidateCertificate(reason) {
      if (this.selectedAlias === this.invalidAlias)
        this.$toasted.show("Certificate not selected!", {
          theme: "toasted-primary",
          position: "top-center",
          duration: 1700,
        });
      else {
        let revokedCertificateDTO = {
          alias: this.selectedAlias,
          reason: reason,
        };
        CertificateService.invalidateCertificate(revokedCertificateDTO).then(
          (response) => {
            this.$toasted.show(response.data, {
              theme: "toasted-primary",
              position: "top-center",
              duration: 3000,
            });
          }
        );
      }
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
