import { AfterViewInit, Component } from '@angular/core';
import { Device } from 'src/modules/shared/models/Device';
import { DeviceService } from 'src/modules/shared/services/device.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';

@Component({
  selector: 'app-devices-view',
  templateUrl: './devices-view.component.html',
  styleUrls: ['./devices-view.component.scss']
})
export class DevicesViewComponent implements AfterViewInit {

  devices: Device[] = [];
  
  constructor(private deviceService: DeviceService,  private snackBarService: SnackBarService) { }

  ngAfterViewInit(): void {
    this.deviceService.getAllDevices()
    .subscribe(response => {
      this.devices = response.body as Device[];
      console.log(this.devices);
    })
  }

  changeReadPeriod(i: number, device: Device) {
    var inputValue = (<HTMLInputElement>document.getElementById(i.toString())).value;

    if (Number(inputValue) <= 0) {
      this.snackBarService.openSnackBar("Invalid value!");
    } else {
      device.readPeriod = Number(inputValue);
      this.deviceService.updateDeviceReadPeriod(device)
      .subscribe((response) => {
        this.snackBarService.openSnackBar(response.body as string);
      })
    }
    
  }
}
