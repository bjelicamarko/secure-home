<template>
  <div class="row">
    <div class="col-3"></div>
    <div class="col-5">
      <form class="myForm">
        <vue-form-generator
          :schema="schema"
          :model="model"
          :options="formOptions"
        >
        </vue-form-generator>
        <button
          class="btn command-button"
          @click="onSubmitAction()"
          type="submit"
          style="margin-top: 10px"
        >
          Send request
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import CSRService from "../service/CSRService.js";
export default {
  data() {
    return {
      model: {
        commonName: "",
        organization: "",
        organizationUnit: "",
        email: "",
        city: "",
        state: "",
        country: "",
      },

      schema: {
        groups: [
          {
            legend: "Certificate Signing Request",
            fields: [
              {
                type: "input",
                inputType: "text",
                label: "Common name",
                model: "commonName",
                placeholder: "User's common name",
                required: true,
                validator: "string",
              },
            ],
          },
          {
            legend: "Organization Info",
            fields: [
              {
                type: "input",
                inputType: "text",
                label: "Organization",
                model: "organization",
                placeholder: "User's organization",
                validator: "string",
                required: true,
              },
              {
                type: "select",
                label: "Organization unit",
                model: "organizationUnit",
                values: ["IT", "IT Services", "IT Department"],
                required: true,
              },
            ],
          },
          {
            legend: "User Private Info",
            fields: [
              {
                type: "input",
                inputType: "email",
                label: "E-mail",
                model: "email",
                placeholder: "User's e-mail address",
                required: true,
                validator: "string",
              },
              {
                type: "input",
                inputType: "text",
                label: "City",
                model: "city",
                placeholder: "User's city",
                required: true,
                validator: "string",
              },
              {
                type: "input",
                inputType: "text",
                label: "State",
                model: "state",
                placeholder: "User's state",
                required: true,
                validator: "string",
              },
              {
                type: "input",
                inputType: "text",
                label: "Country",
                model: "country",
                placeholder: "User's country",
                required: true,
                validator: "string",
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
  methods: {
    onSubmitAction: function () {
      CSRService.saveCSR(this.model)
        .then((response) => {
          this.resetForm();
          this.$toasted.show(response.data, {
            theme: "toasted-primary",
            position: "top-center",
            duration: 3000,
          });
        })
        .catch((response) => {
          this.$toasted.show(response.data, {
            theme: "toasted-primary",
            position: "top-center",
            duration: 3000,
          });
        });
    },
    resetForm: function () {
      this.model = {
        commonName: "",
        organization: "",
        organizationUnit: "",
        email: "",
        city: "",
        state: "",
        country: "",
      };
    },
  },
};
</script>
<style scoped>
.myForm {
  margin-top: 8%;
  transform: translate(70px);
}
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
