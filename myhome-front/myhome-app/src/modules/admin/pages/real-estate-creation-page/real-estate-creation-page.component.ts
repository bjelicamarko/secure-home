import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RealEstateWithDevicesDTO } from 'src/modules/shared/models/RealEstateDTO';
import { DeviceService } from 'src/modules/shared/services/device.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { BasicValidatorWithSpace } from 'src/modules/shared/validators/BasicValidatorWithSpace';
import { MaxLengthValidator } from 'src/modules/shared/validators/MaxLengthValidator';
import { MinLengthValidator } from 'src/modules/shared/validators/MinLengthValidator';
import { RealEstateService } from '../../services/real-estate.service';

@Component({
  selector: 'app-real-estate-creation-page',
  templateUrl: './real-estate-creation-page.component.html',
  styleUrls: ['./real-estate-creation-page.component.scss']
})
export class RealEstateCreationPageComponent implements OnInit {

  form: FormGroup;
  deviceNames: string[];

    constructor( private fb: FormBuilder, private realEstateService: RealEstateService,
               private router: Router, private snackBarService: SnackBarService,
               private deviceService: DeviceService) {

    this.form = this.fb.group({
      name: [null, [Validators.required, BasicValidatorWithSpace, MinLengthValidator, MaxLengthValidator]],
      isArray: this.fb.array([], [Validators.required])
    });
    this.deviceNames = [];
  }

  ngOnInit(): void {
    this.deviceService.getAllDeviceNames().subscribe((response: any) => {
      this.deviceNames = response.body;
      console.log(response.body);
    }, 
    (error) => {
      if(error.status == 500)
        this.snackBarService.openSnackBar("Error ocured while loading device names.");
      else if(error.status == 401)
        this.snackBarService.openSnackBar("Unaothorized.");
    })

  }

  onCbChange(e: any) {
    const isArray: FormArray = this.form.get('isArray') as FormArray;

    if (e.target.checked) {
      isArray.push(new FormControl(e.target.value));
    } 
    else {
      let i: number = 0;

      isArray.controls.forEach((item) => {
        if (item.value == e.target.value) {
          isArray.removeAt(i);
          return;
        }
        i++;
      });
    }
  }

  submit() {
    const realEstateDTO: RealEstateWithDevicesDTO = {
      name: this.form.value.name,
      devices: this.form.value.isArray
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
