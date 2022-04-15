<template>
  <div>
    <DropdownComponent
      @changeItem="changeAlias"
      :items="aliases"
    ></DropdownComponent>
    <CertificateDialog ref="dialog" :certificates="selectedCertificates">
    </CertificateDialog>
    <el-button style="margin-top: 10px" @click="openModal"
      >Open certificate</el-button
    >
  </div>
</template>

<script>
import CertificateDialog from "../components/CertificateDialog.vue";
import DropdownComponent from "../components/DropdownComponent.vue";
import CertificateService from "../service/CertificateService";
export default {
  components: { DropdownComponent, CertificateDialog },
  data: function () {
    return {
      aliases: [],
      selectedAllies: "root",
      selectedCertificates: {},
      showModal: false,
    };
  },
  mounted: function () {
    CertificateService.getAliases().then((response) => {
      this.aliases = response.data;
    });
  },
  methods: {
    changeAlias: function (alias) {
      this.selectedAllies = alias;
      CertificateService.getCertificate(this.selectedAllies).then(
        (response) => {
          console.log(response.data);
          this.selectedCertificates = response.data;
        }
      );
    },
    openModal: function () {
      this.$refs.dialog.openModal();
    },
  },
};
</script>

<style></style>
