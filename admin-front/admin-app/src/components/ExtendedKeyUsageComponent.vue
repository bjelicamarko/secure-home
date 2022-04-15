<template>
  <div class="row">
    <h4 id="title-label">Extended Key usage</h4>
    <div class="col-4">
      <form>
        <vue-form-generator
          :schema="schema1"
          :model="model"
          :options="formOptions"
        >
        </vue-form-generator>
      </form>
    </div>
    <div class="col-4">
      <form>
        <vue-form-generator
          :schema="schema2"
          :model="model"
          :options="formOptions"
        >
        </vue-form-generator>
      </form>
    </div>
    <div class="col-4">
      <form>
        <vue-form-generator
          :schema="schema3"
          :model="model"
          :options="formOptions"
        >
        </vue-form-generator>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      model: {
        adobePdfSigning: false,
        documentSigning: false,
        ipSecurityEndSystem: false,
        ocspSigning: false,
        tlsWebClientAuthentication: false,
        timeStamping: false,
        tlsWebServerAuthentication: false,
        emailProtection: false,
        tslSigning: false,
      },

      schema1: {
        groups: [
          {
            fields: [
              {
                type: "checkbox",
                label: "Adobe Pdf Signing",
                model: "adobePdfSigning",
                disabled: true,
              },
              {
                type: "checkbox",
                label: "Document Signing",
                model: "documentSigning",
                disabled: true,
              },
              {
                type: "checkbox",
                label: "Ip Security End System",
                model: "ipSecurityEndSystem",
              },
            ],
          },
        ],
      },
      schema2: {
        groups: [
          {
            fields: [
              {
                type: "checkbox",
                label: "Ocsp Signing",
                model: "ocspSigning",
              },
              {
                type: "checkbox",
                label: "Tls Web Client Authentication",
                model: "tlsWebClientAuthentication",
              },
              {
                type: "checkbox",
                label: "Time stamping",
                model: "timeStamping",
              },
            ],
          },
        ],
      },
      schema3: {
        groups: [
          {
            fields: [
              {
                type: "checkbox",
                label: "Tls Web Server Authentication",
                model: "tlsWebServerAuthentication",
              },
              {
                type: "checkbox",
                label: "Email Protection",
                model: "emailProtection",
              },
              {
                type: "checkbox",
                label: "Tsl Signing",
                model: "tslSigning",
                disabled: true,
              },
            ],
          },
        ],
      },
      formOptions: {
        validateAfterLoad: false,
        validateAfterChanged: true,
        validateAsync: true,
      },
    };
  },
};
</script>
