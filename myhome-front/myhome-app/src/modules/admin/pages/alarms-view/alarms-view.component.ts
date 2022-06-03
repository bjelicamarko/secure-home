import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { DeviceService } from 'src/modules/shared/services/device.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { AlarmRuleExtendedDTO } from '../../models/AlarmRuleDTO';
import { AlarmService } from '../../services/alarm.service';

@Component({
  selector: 'app-alarms-view',
  templateUrl: './alarms-view.component.html',
  styleUrls: ['./alarms-view.component.scss']
})
export class AlarmsViewComponent implements AfterViewInit {

  dataSource;
  displayedColumns: string[] = ['rulePattern', 'alarmType', 'deviceName', 'delete'];
  _liveAnnouncer: any;

  deviceNames: string[];
  alarmRuleForm = this.formBuilder.group({
    rulePattern: '',
    deviceName: 'LOG'
  });
  alarmRules: AlarmRuleExtendedDTO[];

  constructor(private formBuilder: FormBuilder,
    private deviceService: DeviceService,
    private snackBarService: SnackBarService,
    private alarmService: AlarmService) {
    this.deviceNames = ['LOG'];
    this.alarmRules = [];
    this.dataSource = new MatTableDataSource(this.alarmRules);
  }

  @ViewChild(MatSort)
  sort: MatSort = new MatSort;

  ngAfterViewInit(): void {
    this.deviceService.getAllDeviceNames().subscribe((response: any) => {
      this.deviceNames = [...this.deviceNames, ...response.body];
    },
      (error) => {
        if (error.status == 500)
          this.snackBarService.openSnackBar("Error ocured while loading device names.");
        else if (error.status == 401)
          this.snackBarService.openSnackBar("Unaothorized.");
      })
    this.getAlarmRules();
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
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
      this.getAlarmRules();
    });
  }

  getAlarmRules() {
    this.alarmService.getAllExistingAlarmRules().subscribe((response: any) => {
      this.alarmRules = response.body;
      console.log(response.body);

      this.dataSource = new MatTableDataSource(this.alarmRules);
      this.dataSource.sort = this.sort;
    },
      (error) => {
        if (error.status == 500)
          this.snackBarService.openSnackBar("Error ocured while loading device names.");
        else if (error.status == 401)
          this.snackBarService.openSnackBar("Unaothorized.");
      })
  }

  deleteAlarmRule(id: number) {
    this.deviceService.deleteAlarmRule(id).subscribe((response) => {
      this.snackBarService.openSnackBar("Alarm rule successfully deleted.");
      this.getAlarmRules();
    },
      (error) => {
        this.snackBarService.openSnackBar(error.error);
      })
  }
}
