import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { JwtHelperService } from "@auth0/angular-jwt";
import { Router } from '@angular/router';
import { Login } from 'src/modules/shared/models/login';
import { AuthService } from '../../services/auth.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';

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
      username: [null, Validators.required],
      password: [null, Validators.required],
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
      localStorage.setItem("user", token);

      const jwt: JwtHelperService = new JwtHelperService();
      const info = jwt.decodeToken(token);
      const role = info.role;

      if (role === "ROLE_ADMIN") {
        this.router.navigate(["mh-app/admin/home-page"]);
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
        if (err.status === 401)
          this.snackBarService.openSnackBar(err.error.exception);
        if (err.status === 403)
          this.snackBarService.openSnackBar("You must verify yor account to login.");
      }
    );
  }

}
