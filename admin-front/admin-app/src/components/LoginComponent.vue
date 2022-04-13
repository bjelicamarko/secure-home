<template>
  <div class="row">
    <div class="col-3"></div>
    <div class="col-4">
      <form class="login">
        <vue-form-generator
          :schema="schema"
          :model="model"
          :options="formOptions"
        >
        </vue-form-generator>
      </form>
    </div>
  </div>
</template>

<script>
import LoginService from "../service/LoginService";
import UserService from "../service/UserService";
export default {
  data() {
    return {
      model: {
        username: "",
        password: "",
      },

      schema: {
        groups: [
          {
            legend: "Login ",
            fields: [
              {
                type: "input",
                inputType: "text",
                label: "Username",
                model: "username",
                placeholder: "User's username",
                required: true,
                validator: "string",
              },
              {
                type: "input",
                inputType: "password",
                label: "Password",
                model: "password",
                placeholder: "User's password",
                required: true,
                validator: "string",
              },
              {
                type: "submit",
                onSubmit: this.login,
                buttonText: "Login",
                styleClasses: "myLButton",
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
    login: function () {
      LoginService.login(this.model)
        .then((response) => {
          console.log(response);
          this.$toasted.show("Successfully logged in.", {
            theme: "toasted-primary",
            position: "top-center",
            duration: 3000,
          });
          UserService.saveUserInLocalStorage(response);
          // treba redirect na Admin home page
        })
        .catch(() => {
          this.$toasted.show("Bad credentials", {
            theme: "toasted-primary",
            position: "top-center",
            duration: 3000,
          });
        });
    },
  },
};
</script>

<style>
.login {
  margin-top: 8%;
  transform: translate(120px, 20px);
}
.myLButton {
  transform: translate(220px);
}
</style>
