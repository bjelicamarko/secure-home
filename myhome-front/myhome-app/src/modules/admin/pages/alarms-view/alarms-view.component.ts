import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { DeviceService } from 'src/modules/shared/services/device.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { RuleDTO } from '../../models/RuleDTO';
import { AlarmService } from '../../services/alarm.service';

@Component({
  selector: 'app-alarms-view',
  templateUrl: './alarms-view.component.html',
  styleUrls: ['./alarms-view.component.scss']
})
export class AlarmsViewComponent implements AfterViewInit {

  dataSource;
  displayedColumns: string[] = ['regexPattern', 'ruleType', 'logLevel', 'deviceName', 'delete'];
  _liveAnnouncer: any;

  deviceNames: string[];
  ruleForm = this.formBuilder.group({
    regexPattern: '',
    deviceName: 'Air conditioner',
    logLevel: 'INFO',
    ruleType: 'LOG'
  });
  rules: RuleDTO[];


  constructor(private formBuilder: FormBuilder,
    private deviceService: DeviceService,
    private snackBarService: SnackBarService,
    private ruleService: AlarmService) {
    this.deviceNames = [];
    this.rules = [];
    this.dataSource = new MatTableDataSource(this.rules);
  }

  @ViewChild(MatSort)
  sort: MatSort = new MatSort;

  ngAfterViewInit(): void {
    this.deviceService.getAllDeviceNames().subscribe((response: any) => {
      this.deviceNames = response.body as string[];
    },
      (error) => {
        if (error.status == 500)
          this.snackBarService.openSnackBar("Error ocured while loading device names.");
        else if (error.status == 401)
          this.snackBarService.openSnackBar("Unaothorized.");
      })
    this.getAllRules();
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }

  onSubmit() {
    if (this.ruleForm.value.regexPattern === '') {
      this.snackBarService.openSnackBar("Rule pattern cannot be empty.")
      return;
    }

    console.log(this.ruleForm.value)

    if (this.ruleForm.value.ruleType === 'ALARM')
      this.ruleForm.value.logLevel = ''


    if (this.ruleForm.value.ruleType === 'LOG')
      this.ruleForm.value.deviceName = ''

    let ruleDTO = {
      "id": -1,
      "regexPattern": this.ruleForm.value.regexPattern,
      "ruleType": this.ruleForm.value.ruleType,
      "logLevel": this.ruleForm.value.logLevel,
      "deviceName": this.ruleForm.value.deviceName
    }

    this.ruleService.saveRule(ruleDTO).subscribe(
      (response) => {
        this.snackBarService.openSnackBar(response.body as string)
        this.ruleForm.reset();
        this.getAllRules();
      },
      (error) => {
        this.snackBarService.openSnackBar(error.error);
      });
  }

  getAllRules() {
    this.ruleService.getAllRules().subscribe((response: any) => {
      this.rules = response.body;
      console.log(response.body);

      this.dataSource = new MatTableDataSource(this.rules);
      this.dataSource.sort = this.sort;
    },
      (error) => {
        if (error.status == 500)
          this.snackBarService.openSnackBar("Error ocured while loading rules.");
        else if (error.status == 401)
          this.snackBarService.openSnackBar("Unaothorized.");
      })
  }

  deleteAlarmRule(id: number) {
    this.ruleService.deleteAlarmRule(id).subscribe((response) => {
      this.snackBarService.openSnackBar("Rule successfully deleted.");
      this.getAllRules();
    },
      (error) => {
        this.snackBarService.openSnackBar(error.error);
      })
  }
}
