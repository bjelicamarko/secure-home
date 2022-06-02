import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { DeviceService } from 'src/modules/shared/services/device.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { AlarmService } from '../../services/alarm.service';

@Component({
  selector: 'app-alarms-view',
  templateUrl: './alarms-view.component.html',
  styleUrls: ['./alarms-view.component.scss']
})
export class AlarmsViewComponent implements AfterViewInit {

  deviceNames: string[];
  alarmRuleForm = this.formBuilder.group({
    rulePattern: '',
    deviceName: 'LOG'
  });

  constructor(private formBuilder: FormBuilder,
    private deviceService: DeviceService,
    private snackBarService: SnackBarService,
    private alarmService: AlarmService) {
    this.deviceNames = ['LOG'];
  }


  ngAfterViewInit(): void {
    this.deviceService.getAllDeviceNames().subscribe((response: any) => {
      this.deviceNames = [...this.deviceNames, ...response.body];
      console.log(response.body);
    },
      (error) => {
        if (error.status == 500)
          this.snackBarService.openSnackBar("Error ocured while loading device names.");
        else if (error.status == 401)
          this.snackBarService.openSnackBar("Unaothorized.");
      })
  }

  onSubmit() {
    if (this.alarmRuleForm.value.rulePattern === '') {
      this.snackBarService.openSnackBar("Rule pattern cannot be empty.")
      return;
    }
    console.log(this.alarmRuleForm.value)
    let alarmRule = {
      "rulePattern": this.alarmRuleForm.value.rulePattern,
      "deviceName": this.alarmRuleForm.value.deviceName
    }

    this.alarmService.saveAlarmRule(alarmRule).subscribe((response) => {
      this.snackBarService.openSnackBar(response.body as string)
    });
  }

}
