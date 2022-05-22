import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { JwtHelperService } from "@auth0/angular-jwt";
import { Router } from '@angular/router';
import { Login } from 'src/modules/shared/models/login';
import { AuthService } from '../../services/auth.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { BasicValidator } from 'src/modules/shared/validators/BasicValidator';
import { PasswordValidator } from 'src/modules/shared/validators/PasswordValidator';
import { MinLengthValidator } from 'src/modules/shared/validators/MinLengthValidator';
import { MinLengthPasswordValidator } from 'src/modules/shared/validators/MinLengthPasswordValidator';
import { MaxLengthValidator } from 'src/modules/shared/validators/MaxLengthValidator';
import { UsernameValidator } from 'src/modules/shared/validators/UsernameValidator';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackBarService: SnackBarService
  ) {
    this.form = this.fb.group({
      username: [null, [Validators.required, UsernameValidator, MinLengthValidator, MaxLengthValidator]],
      password: [null, [Validators.required, PasswordValidator, MinLengthPasswordValidator, MaxLengthValidator]],
    });
  }

  ngOnInit(): void {
  }

  submit() {
    const auth: Login = {
      username: this.form.value.username,
      password: this.form.value.password,
    };

    this.authService.login(auth).subscribe((result: any) => {
      this.snackBarService.openSnackBar("Successful login!");

      const token = JSON.stringify(result);
      sessionStorage.setItem("user", token);

      const jwt: JwtHelperService = new JwtHelperService();
      const info = jwt.decodeToken(token);
      const role = info.role;

      if (role === "ROLE_ADMIN") {
        this.router.navigate(["mh-app/admin/users-view"]);
      }
      else if (role === "ROLE_OWNER") {
        this.router.navigate(["mh-app/user/user-home-page"]);
      }
      else if (role === "ROLE_TENANT") {
        this.router.navigate(["mh-app/user/user-home-page"]);
      }
      else if (role === "ROLE_UNASSIGNED") {
        this.router.navigate(["mh-app/user/user-home-page"]);
      }
    },
      (err: any) => {
        this.snackBarService.openSnackBar(err.error.exception);
      }
    );
  }

}
