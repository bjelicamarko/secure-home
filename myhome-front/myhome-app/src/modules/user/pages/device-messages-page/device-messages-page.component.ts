import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { DeviceMessageDTO } from 'src/modules/shared/models/deviceMessageDTO';
import { DeviceService } from 'src/modules/shared/services/device.service';
import { SharedDatePickerService } from 'src/modules/shared/services/shared-data-picker.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';

@Component({
  selector: 'app-device-messages-page',
  templateUrl: './device-messages-page.component.html',
  styleUrls: ['./device-messages-page.component.scss']
})
export class DeviceMessagesPageComponent implements AfterViewInit {

  name: string | null;

  deviceMessages: DeviceMessageDTO[] = [];
  dataSource = new MatTableDataSource(this.deviceMessages);
  displayedColumns: string[] = ['deviceName', 'timestamp', 'messageStatus', 'message'];
  _liveAnnouncer: any;

  selectedStatus: string = 'all';
  startDate: string = '';
  endDate: string = '';

  public range = new FormGroup({
    start: new FormControl(),
    end: new FormControl(),
  });
  
  constructor(private route: ActivatedRoute, private deviceService: DeviceService, 
    private sharedDatePickerService: SharedDatePickerService, private snackBarService: SnackBarService) { 
    this.name = route.snapshot.paramMap.get("deviceName");
  }

  @ViewChild(MatSort)
  sort: MatSort = new MatSort;
  
  ngAfterViewInit(): void {
    if (!this.name) return;

    this.name = decodeURIComponent(this.name);

    console.log(this.name);
    this.deviceService.getAllMessagesFromDevice(this.name)
    .subscribe((response : any) => {
      this.deviceMessages = response.body as DeviceMessageDTO[];
      this.dataSource = new MatTableDataSource(this.deviceMessages);
      this.dataSource.sort = this.sort;
      console.log(this.deviceMessages);
    })
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }
  
  changeDate(): void {
    this.startDate = this.sharedDatePickerService.checkDate(this.range.value.start);
    this.endDate = this.sharedDatePickerService.checkDate(this.range.value.end);

    console.log(this.startDate);
    console.log(this.endDate);
  }

  filterMessages(): void {
    if (!this.startDate)
      this.startDate = '';
    if (!this.endDate)
      this.endDate = '';
    
    this.deviceService.filterMessages(this.startDate, this.endDate, this.selectedStatus)
      .subscribe((response : any) => {
        this.deviceMessages = response.body as DeviceMessageDTO[];
        this.dataSource = new MatTableDataSource(this.deviceMessages);
        this.dataSource.sort = this.sort;
        console.log(this.deviceMessages);
    }, (error) => {
        console.log(error);
        this.snackBarService.openSnackBar("Empty list");
    })
  }
}
