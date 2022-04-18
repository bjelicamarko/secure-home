<template>
  <div class="row">
    <h4 id="title-label">Key usage</h4>
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
        certificateSigning: true,
        crlSign: true,
        dataEncipherment: false,
        decipherOnly: false,
        digitalSignature: false,
        encipherOnly: false,
        keyAgreement: false,
        keyEncipherment: false,
        nonRepudiation: false,
      },

      schema1: {
        groups: [
          {
            styleClasses: "firstCol",
            fields: [
              {
                type: "checkbox",
                label: "Certificate Signing",
                model: "certificateSigning",
              },
              {
                type: "checkbox",
                label: "CRL Sign",
                model: "crlSign",
              },
              {
                type: "checkbox",
                label: "Data Encipherment",
                model: "dataEncipherment",
              },
            ],
          },
        ],
      },
      schema2: {
        groups: [
          {
            styleClasses: "secondCol",
            fields: [
              {
                type: "checkbox",
                label: "Decipher Only",
                model: "decipherOnly",
              },
              {
                type: "checkbox",
                label: "Digital Signature",
                model: "digitalSignature",
              },
              {
                type: "checkbox",
                label: "Encipher Only",
                model: "encipherOnly",
              },
            ],
          },
        ],
      },
      schema3: {
        groups: [
          {
            styleClasses: "thirdCol",
            fields: [
              {
                type: "checkbox",
                label: "Key Agreement",
                model: "keyAgreement",
              },
              {
                type: "checkbox",
                label: "Key Encipherment",
                model: "keyEncipherment",
              },
              {
                type: "checkbox",
                label: "Non Repudiation",
                model: "nonRepudiation",
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

<style>
#title-label {
  margin-top: 2%;
  margin-bottom: 2%;
}
</style>
