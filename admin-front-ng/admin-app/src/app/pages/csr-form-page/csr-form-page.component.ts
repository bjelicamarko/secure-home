import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BasicValidatorWithSpace } from 'src/app/validators/BasicValidatorWithSpace';
import { EmailValidator } from 'src/app/validators/EmailValidator';
import { MaxLengthValidator } from 'src/app/validators/MaxLengthValidator';
import { TwoLengthValidator } from 'src/app/validators/TwoLengthValidator';
import { MinLengthValidator3 } from 'src/app/validators/MinLengthValidator3';
import { CertificateService } from 'src/app/services/certificate.service';
import { SnackBarService } from 'src/app/services/snack-bar.service';
import { CSRDTO } from 'src/app/models/CSRDTO';

@Component({
  selector: 'app-csr-form-page',
  templateUrl: './csr-form-page.component.html',
  styleUrls: ['./csr-form-page.component.scss']
})
export class CsrFormPageComponent {

  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private certificateService: CertificateService,
    private snackBarService: SnackBarService
  ) {
    this.form = this.fb.group({
      commonName: ['', [Validators.required, BasicValidatorWithSpace, MinLengthValidator3, MaxLengthValidator]],
      organization: ['', [Validators.required, BasicValidatorWithSpace, MinLengthValidator3, MaxLengthValidator]],
      organizationUnit: ['', [Validators.required]],
      email: ['', [Validators.required, EmailValidator]],
      city: ['', [Validators.required, BasicValidatorWithSpace, MinLengthValidator3, MaxLengthValidator]],
      state: ['', [Validators.required, BasicValidatorWithSpace, MinLengthValidator3, MaxLengthValidator]],
      country: ['', [Validators.required, BasicValidatorWithSpace, TwoLengthValidator]],
    })
  }

  onSubmitAction() {
    let certificateDTO: CSRDTO = {
      commonName: this.form.value.commonName,
      organization: this.form.value.organization,
      organizationUnit: this.form.value.organizationUnit,
      email: this.form.value.email,
      city: this.form.value.city,
      state: this.form.value.state,
      country: this.form.value.country,
    }
    this.certificateService.saveCSR(certificateDTO).subscribe(
      (response) => {
        if (response.body) {
          this.snackBarService.openSnackBar(response.body);
          this.form.reset();
        }
      },
      (error) => {
        this.snackBarService.openSnackBar(error.error);
      })
  }
}
