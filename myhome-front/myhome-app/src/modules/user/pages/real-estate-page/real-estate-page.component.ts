import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { RealEstateService } from 'src/modules/admin/services/real-estate.service';
import { PaginationComponent } from 'src/modules/shared/components/pagination/pagination.component';
import { DeviceMessageDTO } from 'src/modules/shared/models/deviceMessageDTO';
import { RealEstateWithHouseholdAndDevicesDTO } from 'src/modules/shared/models/RealEstateDTO';
import { DeviceService } from 'src/modules/shared/services/device.service';
import { SharedDatePickerService } from 'src/modules/shared/services/shared-data-picker.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { ReportsDialogComponent } from '../../components/reports-dialog/reports-dialog.component';

import { interval } from 'rxjs';

@Component({
  selector: 'app-real-estate-page',
  templateUrl: './real-estate-page.component.html',
  styleUrls: ['./real-estate-page.component.scss']
})
export class RealEstatePageComponent implements AfterViewInit {

  name: string | null;
  realEstate: RealEstateWithHouseholdAndDevicesDTO;

  isShowDivIf = true;

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

  constructor(private route: ActivatedRoute, private router: Router, private realEstateService: RealEstateService,
     private snackBarService: SnackBarService, private deviceService: DeviceService,
     public dialog: MatDialog, private sharedDatePickerService: SharedDatePickerService) {
    this.name = route.snapshot.paramMap.get("name");
    this.realEstate = { household: [], devices: [] }
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
    this.realEstateService.getRealEstateByName(this.name)
      .subscribe((response) => {
        this.realEstate = response.body as RealEstateWithHouseholdAndDevicesDTO;
      },
        (error) => {
          if (error.status === 400) {
            this.snackBarService.openSnackBar("You can't access real estate where you don't belong.")
            this.router.navigate(["mh-app/user/user-home-page"]);
          }
        });
    
    this.deviceService
    .getAllMessagesFromRealEstate(this.name, this.currentPage-1, this.pageSize)
    .subscribe((response: any) => {
      this.deviceMessages = response.body as DeviceMessageDTO[];
        this.dataSource = new MatTableDataSource(this.deviceMessages);
        this.dataSource.sort = this.sort;
        this.totalSize = Number(response.headers.get("total-elements"));
        this.setPagination(response.headers.get('total-elements'), response.headers.get('current-page'));
    })

    if (this.name)
      this.deviceService.findLowestReadPeriod(this.name).subscribe((response) => {
        interval(response.body as number).subscribe(() => {
          if (this.closedDialog) {
            if (!this.name) return;
            console.log("BLAAA");
            this.name = decodeURIComponent(this.name);
            this.deviceService
            .getAllMessagesFromRealEstate(this.name, this.currentPage-1, this.pageSize)
            .subscribe((response: any) => {
              this.deviceMessages = response.body as DeviceMessageDTO[];
                this.dataSource = new MatTableDataSource(this.deviceMessages);
                this.dataSource.sort = this.sort;
                this.totalSize = Number(response.headers.get("total-elements"));
                this.setPagination(response.headers.get('total-elements'), response.headers.get('current-page'));
            })
          }
        });
      })
  }

  changePage(newPage: number) {
    if (!this.name) return;

    this.name = decodeURIComponent(this.name);

    this.deviceService
    .getAllMessagesFromRealEstate(this.name, newPage - 1, this.pageSize)
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

  showMore(name: string | null) {
    this.router.navigate(["mh-app/user/real-estate-page/device-messages", name]);
  }

  toggleDisplayDivIf() {
    this.isShowDivIf = !this.isShowDivIf;
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
      indicator: false },
    });

    dialogRef.afterClosed().subscribe(() => {
      this.closedDialog = true;
    });
  }
}
