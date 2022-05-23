import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { BasicValidator } from 'src/modules/shared/validators/BasicValidator';
import { EmailValidator } from 'src/modules/shared/validators/EmailValidator';
import { MaxLengthValidator } from 'src/modules/shared/validators/MaxLengthValidator';
import { MinLengthValidator } from 'src/modules/shared/validators/MinLengthValidator';
import { UsernameValidator } from 'src/modules/shared/validators/UsernameValidator';
import { RegistrationDTO } from '../../models/RegistrationDTO';
import { AuthService } from '../../services/auth.service';
import { MatchValidator } from '../../validators/MatchValidator';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  form: FormGroup;

  constructor( private fb: FormBuilder, private authService: AuthService,
               private router: Router, private snackBarService: SnackBarService) {
    this.form = this.fb.group({
      username: [null, [Validators.required, UsernameValidator, MinLengthValidator, MaxLengthValidator]],          
      password: new FormControl('', [Validators.required, Validators.pattern("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#&()\-+=<>])([a-zA-Z0-9!@#&()–+=<>]){8,20}$")]),
      repeatPassword: new FormControl('', Validators.required),
      email: [null, [Validators.required, EmailValidator]],
      firstname: [null, [Validators.required, BasicValidator, MinLengthValidator, MaxLengthValidator]],
      lastname: [null, [Validators.required , BasicValidator, MinLengthValidator, MaxLengthValidator]],
    },
    { validator: MatchValidator('password', 'repeatPassword')});
  }

  get f() {
    return this.form.controls;
  }

  ngOnInit(): void {
  }

  submit() {
    const regDTO: RegistrationDTO = {
      username: this.form.value.username,
      password: this.form.value.password,
      email: this.form.value.email,
      firstname: this.form.value.firstname,
      lastname: this.form.value.lastname,
    };

    this.authService.register(regDTO).subscribe((result: any) => {
      this.snackBarService.openSnackBar(result.body);
      this.router.navigate(["mh-app/auth/login"]);
    },
      (err: any) => {
        this.snackBarService.openSnackBar(err.error);
      }
    );
  }

}
