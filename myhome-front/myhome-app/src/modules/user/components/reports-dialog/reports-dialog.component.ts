import { AfterViewInit, Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { PaginationComponent } from 'src/modules/shared/components/pagination/pagination.component';
import { DeviceMessageDTO } from 'src/modules/shared/models/deviceMessageDTO';
import { DeviceService } from 'src/modules/shared/services/device.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';

export interface Temp {
  deviceName: string,
  selectedStatus: string,
  startDate: string,
  endDate: string
}

@Component({
  selector: 'app-reports-dialog',
  templateUrl: './reports-dialog.component.html',
  styleUrls: ['./reports-dialog.component.scss']
})
export class ReportsDialogComponent implements AfterViewInit {

  deviceMessages: DeviceMessageDTO[];
  dataSource;
  displayedColumns: string[] = ['deviceName', 'timestamp', 'messageStatus', 'message'];
  _liveAnnouncer: any;
  
  @ViewChild(PaginationComponent) pagination?: PaginationComponent;
  pageSize: number;
  currentPage: number;
  totalSize: number;

  constructor(
    public dialogRef: MatDialogRef<ReportsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Temp, private deviceService: DeviceService, 
    private snackBarService: SnackBarService
  ) {
    this.pageSize = 10;
    this.currentPage = 1;
    this.totalSize = 1;
    this.deviceMessages = [];
    this.dataSource = new MatTableDataSource(this.deviceMessages);
  }

  @ViewChild(MatSort)
  sort: MatSort = new MatSort;
  
  
  ngAfterViewInit(): void {
    if (!this.data.startDate)
      this.data.startDate = '';
    if (!this.data.endDate)
      this.data.endDate = '';

    this.deviceService.filterMessages(this.data.deviceName, this.data.startDate, this.data.endDate, this.data.selectedStatus,
      this.currentPage - 1, this.pageSize)
      .subscribe((response : any) => {
        this.deviceMessages = response.body as DeviceMessageDTO[];
        this.dataSource = new MatTableDataSource(this.deviceMessages);
        this.dataSource.sort = this.sort;
        this.totalSize = Number(response.headers.get("total-elements"));
        this.setPagination(response.headers.get('total-elements'), response.headers.get('current-page'));
    }, (error) => {
        console.log(error);
        this.snackBarService.openSnackBar("Empty list");
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
   

    this.deviceService.filterMessages(this.data.deviceName, this.data.startDate, this.data.endDate, this.data.selectedStatus,
      newPage - 1, this.pageSize)
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

  createReport(): void {
    this.deviceService.createReport(this.data.deviceName, this.data.startDate, this.data.endDate, this.data.selectedStatus)
    .subscribe((response) => {
      if (response.body != null) {
        console.log(response.body);
        let blob = new Blob([response.body], { type: 'text' });
        let pdfUrl = window.URL.createObjectURL(blob);

        var text_link = document.createElement('a');
        text_link.href = pdfUrl;

        //   TO OPEN PDF ON BROWSER IN NEW TAB
        window.open(pdfUrl, '_blank');

        //   TO DOWNLOAD PDF TO YOUR COMPUTER
        text_link.download = 'reports' + ".txt";
        text_link.click();
      }
    })
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}
