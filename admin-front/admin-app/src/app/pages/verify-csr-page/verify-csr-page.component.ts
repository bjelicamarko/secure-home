import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CertificateService } from 'src/app/services/certificate.service';
import { SnackBarService } from 'src/app/services/snack-bar.service';
import { D15LengthValidator } from 'src/app/validators/D15LengthValidator';

@Component({
  selector: 'app-verify-csr-page',
  templateUrl: './verify-csr-page.component.html',
  styleUrls: ['./verify-csr-page.component.scss']
})
export class VerifyCsrPageComponent implements OnInit {

  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private certificateService: CertificateService,
    private snackBarService: SnackBarService,
    private router: Router) {
    this.form = this.fb.group({
      securityCode: ['', [Validators.required, D15LengthValidator]]
    })
  }

  ngOnInit(): void {
  }

  onSubmitAction() {
    this.certificateService.verifyCSR(this.form.value.securityCode).subscribe(
      (response) => {
        if (response.body) {
          this.snackBarService.openSnackBar(response.body);
          this.router.navigate(["/adm-app/csr-form"]);
        }
      },
      (error) => {
        this.snackBarService.openSnackBar(error.error)
      })
  }

}
