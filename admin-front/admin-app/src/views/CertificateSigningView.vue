<template>
  <div class="top-margin bottom-margin">
    <h2>Certificate signing</h2>
    <div>
      <CSRequestViewer :csr="csr"></CSRequestViewer>
    </div>
    <div class="horizontal-line" style="margin-bottom: 2%"></div>
    <div class="row">
      <div class="form-check col-3">
        <label class="form-check-label">
          <input
            @click="selectCa()"
            type="radio"
            class="form-check-input"
            name="optradio"
          />CA
        </label>
      </div>
      <div class="form-check col-3">
        <label class="form-check-label">
          <input
            @click="selectSslServer()"
            type="radio"
            class="form-check-input"
            name="optradio"
          />SSL Server
        </label>
      </div>
      <div class="form-check col-3">
        <label class="form-check-label">
          <input
            @click="selectSslClient()"
            type="radio"
            class="form-check-input"
            name="optradio"
            checked
          />SSL Client
        </label>
      </div>
      <div class="form-check col-3">
        <label class="form-check-label">
          <input
            @click="selectCodeSigning()"
            type="radio"
            class="form-check-input"
            name="optradio"
          />Code Signing
        </label>
      </div>
    </div>
    <div class="horizontal-line"></div>
    <div>
      <CertificateFormComponent
        ref="csrForm"
        v-bind:csr="csr"
        @submit="signCertificate"
      ></CertificateFormComponent>
      <div class="horizontal-line"></div>
    </div>
    <div>
      <KeyUsageComponent ref="keyUsage"></KeyUsageComponent>
      <div class="horizontal-line"></div>
    </div>
    <div v-if="!caSelected">
      <ExtendedKeyUsageComponent
        ref="extendedKeyUsage"
      ></ExtendedKeyUsageComponent>
      <div class="horizontal-line"></div>
    </div>
    <div class="form-check row" v-if="caSelected">
      <h4 id="title-label">Basic constraints</h4>
      <div>
        <label
          >Certificate authority
          <input
            class="form-check-input"
            v-model="caCheckboxChecked"
            type="checkbox"
          />
        </label>
      </div>
      <div class="horizontal-line"></div>
    </div>
    <div>
      <button class="btn btn-success" @click="submit()" style="margin-top: 1%">
        Sign certificate
      </button>
    </div>
  </div>
</template>

<script>
import CSRService from "../service/CSRService.js";
import RedirectService from "../service/RedirectService.js";
import KeyUsageComponent from "../components/KeyUsageComponent.vue";
import ExtendedKeyUsageComponent from "../components/ExtendedKeyUsageComponent.vue";
import CSRequestViewer from "../components/CSRequestViewer.vue";
import CertificateFormComponent from "../components/CertificateFormComponent.vue";
import CertificateService from "@/service/CertificateService.js";
export default {
  components: {
    KeyUsageComponent,
    ExtendedKeyUsageComponent,
    CSRequestViewer,
    CertificateFormComponent,
  },
  data() {
    return {
      csrId: "",
      csr: {},
      caSelected: false,
      sslServerSelected: false,
      sslClientSelected: false,
      codeSigning: false,
      caCheckboxChecked: false,
    };
  },
  mounted() {
    this.csrId = this.$route.params.id;
    this.sslClientSelected = true;
    // Get csr from back
    CSRService.getCSR(this.csrId)
      .then((response) => {
        this.csr = response.data;
        console.log(this.csr);
      })
      .catch((error) => {
        if (error.response.status === 404) {
          this.$toasted.show("CSR not found", {
            theme: "toasted-primary",
            position: "top-center",
            duration: 3000,
          });
          RedirectService.redirectToName(this.$router, "admin-home");
        }
      });
  },
  methods: {
    signCertificate: function (certificateData) {
      let certDTO = {
        certificateDataDTO: null,
        keyUsageDTO: null,
        extendedKeyUsageDTO: null,
        ca: null,
      };
      if (!this.caSelected) {
        certDTO = {
          certificateDataDTO: certificateData,
          keyUsageDTO: this.$refs.keyUsage.model,
          extendedKeyUsageDTO: this.$refs.extendedKeyUsage.model,
        };
      } else {
        certDTO = {
          certificateDataDTO: certificateData,
          keyUsageDTO: this.$refs.keyUsage.model,
          ca: this.caCheckboxChecked,
        };
      }
      // Zahtev na bek
      this.sendRequest(certDTO);
    },
    sendRequest: function (certDTO) {
      console.log(certDTO);
      CertificateService.createCertificate(certDTO)
        .then(() => {
          this.$toasted.show("Certificate successfully created", {
            theme: "toasted-primary",
            position: "top-center",
            duration: 3000,
          });
          RedirectService.redirectToName(this.$router, "admin-home");
        })
        .catch((error) => {
          this.$toasted.show(error.response.data, {
            theme: "toasted-primary",
            position: "top-center",
            duration: 3000,
          });
        });
    },
    submit: function () {
      this.$refs.csrForm.clickTugaButton();
    },
    selectCa: function () {
      this.deselectAll();
      this.caSelected = true;
    },

    selectSslServer: function () {
      this.deselectAll();
      this.sslServerSelected = true;
    },

    selectSslClient: function () {
      this.deselectAll();
      this.sslClientSelected = true;
    },

    selectCodeSigning: function () {
      this.deselectAll();
      this.codeSigning = true;
    },

    deselectAll: function () {
      this.caSelected = false;
      this.sslServerSelected = false;
      this.sslClientSelected = false;
      this.codeSigning = false;
    },
  },
};
</script>

<style>
.top-margin {
  margin-top: 6%;
}
.horizontal-line {
  padding-bottom: 20px;
  border-bottom: 3px solid lightgray;
  margin-right: 2%;
  margin-left: 2%;
}
</style>
