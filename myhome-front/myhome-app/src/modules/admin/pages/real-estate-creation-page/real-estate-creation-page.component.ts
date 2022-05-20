import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { BasicValidator } from 'src/modules/shared/validators/BasicValidator';
import { BasicValidatorWithSpace } from 'src/modules/shared/validators/BasicValidatorWithSpace';
import { MaxLengthValidator } from 'src/modules/shared/validators/MaxLengthValidator';
import { MinLengthValidator } from 'src/modules/shared/validators/MinLengthValidator';
import { RealEstateDTO } from '../../models/RealEstateDTO';
import { RealEstateService } from '../../services/real-estate.service';

@Component({
  selector: 'app-real-estate-creation-page',
  templateUrl: './real-estate-creation-page.component.html',
  styleUrls: ['./real-estate-creation-page.component.scss']
})
export class RealEstateCreationPageComponent implements OnInit {

  form: FormGroup;

    constructor( private fb: FormBuilder, private realEstateService: RealEstateService,
               private router: Router, private snackBarService: SnackBarService) {

    this.form = this.fb.group({
      name: [null, [Validators.required, BasicValidatorWithSpace, MinLengthValidator, MaxLengthValidator]],
    });
  }

  ngOnInit(): void {
  }

  submit() {
    const realEstateDTO: RealEstateDTO = {
      name: this.form.value.name,
    };

    this.realEstateService.createRealEstate(realEstateDTO).subscribe((result: any) => {
      this.snackBarService.openSnackBar(result.body);
      this.router.navigate(["mh-app/admin/users-view"]);
    },
      (err: any) => {
        this.snackBarService.openSnackBar(err.error);
      }
    );
  }

}
