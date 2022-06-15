import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { PaginationComponent } from 'src/modules/shared/components/pagination/pagination.component';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { LogDTO } from '../../models/LogDTO';
import { LogService } from '../../services/log.service';
import * as moment from 'moment';
import { MatDialog } from '@angular/material/dialog';
import { StackTraceComponent } from '../../components/stack-trace/stack-trace.component';
import { FormGroup, FormControl, FormBuilder, Form } from '@angular/forms';
import { SharedDatePickerService } from 'src/modules/shared/services/shared-data-picker.service';

@Component({
  selector: 'app-logs-view',
  templateUrl: './logs-view.component.html',
  styleUrls: ['./logs-view.component.scss']
})
export class LogsViewComponent implements AfterViewInit {

  dataSource;
  displayedColumns: string[] = ['timestamp', 'logLevel', 'loggerName', 'message', 'stackTrace'];
  _liveAnnouncer: any;

  selectedLevel: string = 'all';
  startDate: string = '';
  endDate: string = '';

  public range = new FormGroup({
    start: new FormControl(),
    end: new FormControl(),
  });

  public searchFormGroup: FormGroup;

  @ViewChild(PaginationComponent) pagination?: PaginationComponent;
  pageSize: number;
  currentPage: number;
  totalSize: number;
  logs: LogDTO[];

  constructor(
    private snackBarService: SnackBarService,
    private logService: LogService,
    public dialog: MatDialog,
    private sharedDatePickerService: SharedDatePickerService,
    private fb: FormBuilder) {
    this.logs = [];
    this.pageSize = 10;
    this.currentPage = 1;
    this.totalSize = 1;
    this.dataSource = new MatTableDataSource(this.logs);

    this.searchFormGroup = this.fb.group({
      searchValue: [''],
      messageRegex: ['']
    });
  }

  @ViewChild(MatSort)
  sort: MatSort = new MatSort;

  ngAfterViewInit(): void {
    this.logService.getAllLogs(this.currentPage - 1, this.pageSize)
      .subscribe((response: any) => {
        this.logs = response.body as LogDTO[];
        console.log(this.logs)
        this.dataSource = new MatTableDataSource(this.logs);
        this.dataSource.sort = this.sort;
        this.setPagination(response.headers.get('total-elements'), response.headers.get('current-page'));
      });
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
    this.logService.filterLogs(this.startDate, this.endDate, this.selectedLevel, this.getValueFromControl(this.searchFormGroup.value.searchValue),
      this.getValueFromControl(this.searchFormGroup.value.messageRegex), newPage - 1, this.pageSize)
      .subscribe((response: any) => {
        this.logs = response.body as LogDTO[];
        this.dataSource = new MatTableDataSource(this.logs);
        this.dataSource.sort = this.sort;
        this.totalSize = Number(response.headers.get("total-elements"));
        this.setPagination(response.headers.get('total-elements'), response.headers.get('current-page'));
      }, (error) => {
        console.log(error);
        this.snackBarService.openSnackBar("Empty list");
      })
  }

  formatDateTime(date: number) {
    var dateString = moment(date).format('DD. MM. YYYY. hh:mm');
    return dateString.toString();
  }

  changeDate(): void {
    this.startDate = this.sharedDatePickerService.checkDate(this.range.value.start);
    this.endDate = this.sharedDatePickerService.checkDate(this.range.value.end);
  }

  stackTraceDialog(logMessage: string, stackTrace: string) {
    this.dialog.open(StackTraceComponent, {
      data:
      {
        title: logMessage,
        body: stackTrace
      },
    })
  }

  filterMessages(): void {
    this.currentPage = 1;
    this.logService.filterLogs(this.startDate, this.endDate, this.selectedLevel, this.getValueFromControl(this.searchFormGroup.value.searchValue),
      this.getValueFromControl(this.searchFormGroup.value.messageRegex), this.currentPage - 1, this.pageSize)
      .subscribe((response: any) => {
        this.logs = response.body as LogDTO[];
        this.dataSource = new MatTableDataSource(this.logs);
        this.dataSource.sort = this.sort;
        this.totalSize = Number(response.headers.get("total-elements"));
        this.setPagination(response.headers.get('total-elements'), response.headers.get('current-page'));
      }, (error) => {
        console.log(error);
        this.snackBarService.openSnackBar("There is no logs for certain input.");
      })
  }

  getValueFromControl(value: any) {
    return value == null ? '' : value;
  }
}
