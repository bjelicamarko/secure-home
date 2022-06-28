import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RealEstateWithDevicesDTO } from 'src/modules/shared/models/RealEstateDTO';
import { DeviceService } from 'src/modules/shared/services/device.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { RealEstateService } from '../../services/real-estate.service';

@Component({
  selector: 'app-real-estate-page',
  templateUrl: './real-estate-page.component.html',
  styleUrls: ['./real-estate-page.component.scss']
})
export class RealEstatePageComponent implements OnInit {

  name: string;
  form: FormGroup;
  deviceNames: string[];
  deviceNamesRealEstate: string[];

  constructor(private route: ActivatedRoute, private fb: FormBuilder, private router: Router,
              private realEstateService: RealEstateService, private snackBarService: SnackBarService,
              private deviceService: DeviceService) {

    this.name = this.route.snapshot.params['name'];
    this.form = this.fb.group({
      isArray: this.fb.array([], [Validators.required])
    });
    this.deviceNames = [];
    this.deviceNamesRealEstate = [];
   }

  ngOnInit(): void {
    this.loadDeviceNamesRealEstate();
  }

  loadDeviceNamesForm(): void {
    this.deviceService.getAllDeviceNames().subscribe((response: any) => {
      this.deviceNames = response.body;
    }, 
    (error) => {
      if(error.status == 500)
        this.snackBarService.openSnackBar("Error ocured while loading device names.");
      else if(error.status == 401)
        this.snackBarService.openSnackBar("Unaothorized.");
    })
  }

  loadDeviceNamesRealEstate(): void {
    this.realEstateService.findDevicesByRealEstateName(this.name).subscribe((response: any) => {
      this.deviceNamesRealEstate = response.body;
      this.loadDeviceNamesForm();

      const isArray: FormArray = this.form.get('isArray') as FormArray;
      for(let dev of this.deviceNamesRealEstate) {
        isArray.push(new FormControl(dev));
      }
    }, 
    (error) => {
      if(error.status == 500) {
        this.snackBarService.openSnackBar('An error ocured while loading devices for this real estate.')
      }
      else if(error.status == 403) {
        this.snackBarService.openSnackBar('Invalid request.')
      }
    })
  }

  submit() {
    const realEstateDTO: RealEstateWithDevicesDTO = {
      name: this.name,
      devices: this.form.value.isArray
    };

    this.realEstateService.updateRealEstate(realEstateDTO).subscribe((result: any) => {
      this.snackBarService.openSnackBar(result.body);
      this.router.navigate(["mh-app/admin/real-estates"]);
    },
      (err: any) => {
        this.snackBarService.openSnackBar(err.error);
      }
    );
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

  isInRealEstateDevices(deviceName: string) {
    for(let dev of this.deviceNamesRealEstate) {
      if (deviceName === dev)
        return true; 
    }
    return false;
  }


}
