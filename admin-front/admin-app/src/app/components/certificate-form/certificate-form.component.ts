import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CertificateDataDTO } from 'src/app/models/CertificateDataDTO';
import { CSRDTO } from 'src/app/models/CSRDTO';
import { EmailValidator } from 'src/app/validators/EmailValidator';
import { LengthTwoValidator } from 'src/app/validators/LengthTwoValidator';
import { MaxLengthValidator } from 'src/app/validators/MaxLengthValidator';
import { MinLengthValidator } from 'src/app/validators/MinLengthValidator';

@Component({
  selector: 'app-certificate-form',
  templateUrl: './certificate-form.component.html',
  styleUrls: ['./certificate-form.component.scss']
})
export class CertificateFormComponent implements OnInit {

  @Input()
  csr: CSRDTO;

  @Output() 
  submitEvent: EventEmitter<CertificateDataDTO>;

  form: FormGroup;

  constructor(private fb: FormBuilder) { 
    this.submitEvent = new EventEmitter<CertificateDataDTO>();

    this.csr = { 
      id: -1,
      email: "",
      commonName: "",
      organizationUnit: "",
      organization: "",
      city: "",
      state: "",
      country: "",
    };

    this.form = this.fb.group({
      email: ['', [Validators.required, EmailValidator]],
      commonName: ['', [Validators.required, MinLengthValidator, MaxLengthValidator]],
      organization: ['', [Validators.required, MinLengthValidator, MaxLengthValidator]],
      organizationUnit: ['', [Validators.required, MinLengthValidator, MaxLengthValidator]],
      country: ['', [Validators.required, LengthTwoValidator]],
      state: ['', [Validators.required, MinLengthValidator, MaxLengthValidator]],
      city: ['', [Validators.required, MinLengthValidator, MaxLengthValidator]],
    });
  }

  ngOnInit(): void {}

  submit(): void {
    let csr: CertificateDataDTO = {
      email: this.form.value.email,
      commonName: this.form.value.commonName,
      organization: this.form.value.organization,
      organizationUnit: this.form.value.organizationUnit,
      country: this.form.value.country,
      state: this.form.value.state
    }
    this.submitEvent.emit(csr);
  }

}
