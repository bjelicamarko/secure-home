import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { PaginationComponent } from 'src/modules/shared/components/pagination/pagination.component';
import { DeviceMessageDTO } from 'src/modules/shared/models/deviceMessageDTO';
import { DeviceService } from 'src/modules/shared/services/device.service';
import { SharedDatePickerService } from 'src/modules/shared/services/shared-data-picker.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';

import { interval } from 'rxjs';
import { MatDialog } from '@angular/material/dialog'
import { ReportsDialogComponent } from '../../components/reports-dialog/reports-dialog.component';
import { Device } from 'src/modules/shared/models/Device';

@Component({
  selector: 'app-device-messages-page',
  templateUrl: './device-messages-page.component.html',
  styleUrls: ['./device-messages-page.component.scss']
})
export class DeviceMessagesPageComponent implements AfterViewInit {

  name: string | null;
  device: Device | null;

  deviceMessages: DeviceMessageDTO[];
  dataSource;
  displayedColumns: string[] = ['deviceName', 'timestamp', 'messageStatus', 'message'];
  _liveAnnouncer: any;

  selectedStatus: string = 'all';
  startDate: string = '';
  endDate: string = '';

  closedDialog: boolean = true;

  public range = new FormGroup({
    start: new FormControl(),
    end: new FormControl(),
  });

  @ViewChild(PaginationComponent) pagination?: PaginationComponent;
  pageSize: number;
  currentPage: number;
  totalSize: number;

  constructor(public dialog: MatDialog, private route: ActivatedRoute, private deviceService: DeviceService,
    private sharedDatePickerService: SharedDatePickerService, private snackBarService: SnackBarService) {
    this.name = route.snapshot.paramMap.get("deviceName");
    this.device = null;
    this.pageSize = 20;
    this.currentPage = 1;
    this.totalSize = 1;
    this.deviceMessages = [];
    this.dataSource = new MatTableDataSource(this.deviceMessages);
  }

  @ViewChild(MatSort)
  sort: MatSort = new MatSort;

  ngAfterViewInit(): void {
    if (!this.name) return;

    this.name = decodeURIComponent(this.name);

    this.deviceService.getAllMessagesFromDevice(this.name, this.currentPage - 1, this.pageSize)
      .subscribe((response: any) => {
        this.deviceMessages = response.body as DeviceMessageDTO[];
        this.dataSource = new MatTableDataSource(this.deviceMessages);
        this.dataSource.sort = this.sort;
        this.totalSize = Number(response.headers.get("total-elements"));
        this.setPagination(response.headers.get('total-elements'), response.headers.get('current-page'));
      })

    if (this.name)
      this.deviceService.getOneByName(this.name).subscribe((response) => {
        this.device = response.body as Device;
        interval(this.device.readPeriod).subscribe(() => {
          if (this.closedDialog)
            window.location.reload();
        });
      })
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }

  setPagination(totalItemsHeader: string | null, currentPageHeader: string | null) {
    if (totalItemsHeader) {
      this.totalSize = parseInt(totalItemsHeader);
    }
    if (currentPageHeader) {
      this.currentPage = parseInt(currentPageHeader);
    }
  }

  changePage(newPage: number) {
    if (!this.name) return;

    this.name = decodeURIComponent(this.name);

    this.deviceService.getAllMessagesFromDevice(this.name, newPage - 1, this.pageSize)
      .subscribe((response) => {
        if (response.body != null) {
          this.deviceMessages = response.body as DeviceMessageDTO[];
          this.dataSource = new MatTableDataSource(this.deviceMessages);
          this.dataSource.sort = this.sort;
          this.setPagination(response.headers.get('total-elements'), response.headers.get('current-page'));
        }
      }, (err) => {
        if (err.error)
          this.snackBarService.openSnackBar(String(err.console));
      });
  }

  changeDate(): void {
    this.startDate = this.sharedDatePickerService.checkDate(this.range.value.start);
    this.endDate = this.sharedDatePickerService.checkDate(this.range.value.end);

    console.log(this.startDate);
    console.log(this.endDate);
  }

  filterMessages(): void {
    if (!this.name) return;

    this.name = decodeURIComponent(this.name);

    this.closedDialog = false;
    const dialogRef = this.dialog.open(ReportsDialogComponent, {
      width: '100%',
      data: { deviceName: this.name, selectedStatus: this.selectedStatus, startDate: this.startDate, endDate: this.endDate, 
      indicator: true },
    });

    dialogRef.afterClosed().subscribe(() => {
      this.closedDialog = true;
    });
  }
}
