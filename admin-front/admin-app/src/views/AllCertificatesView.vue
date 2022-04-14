<template>
  <div>
    <DropdownComponent
      @changeItem="changeAlias"
      :items="aliases"
    ></DropdownComponent>
  </div>
</template>

<script>
import DropdownComponent from "../components/DropdownComponent.vue";
import CertificateService from "../service/CertificateService";
export default {
  components: { DropdownComponent },
  data: function () {
    return {
      aliases: [],
      selectedAllies: "root",
    };
  },
  mounted: function () {
    CertificateService.getAliases().then((response) => {
      console.log(response.data);
      this.aliases = response.data;
    });
  },
  methods: {
    changeAlias: function (alias) {
      this.selectedAllies = alias;
      console.log(this.selectedAllies);
      CertificateService.getCertificate(this.selectedAllies).then(
        (response) => {
          console.log(response.data);
        }
      );
    },
  },
};
</script>

<style></style>
