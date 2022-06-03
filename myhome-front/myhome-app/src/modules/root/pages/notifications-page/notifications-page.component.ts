import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { AlarmNotification } from 'src/modules/shared/models/AlarmNotification';
import { NotificationService } from 'src/modules/shared/services/notification.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { UtilService } from 'src/modules/shared/services/util/util.service';

@Component({
  selector: 'app-notifications-page',
  templateUrl: './notifications-page.component.html',
  styleUrls: ['./notifications-page.component.scss']
})
export class NotificationsPageComponent implements OnInit {

  displayedColumns: string[] = ['deviceName', 'message', 'timestamp'];

  pageSize: number;
  currentPage: number;
  totalSize: number;
  notificationsList: AlarmNotification[];
  dataSource: MatTableDataSource<AlarmNotification>;

  constructor(private notificationService: NotificationService, private snackBarService: SnackBarService,
              private uitilService: UtilService) { 
    this.notificationsList = [];
    this.dataSource = new MatTableDataSource(this.notificationsList)
    this.pageSize = 2;
    this.currentPage = 1;
    this.totalSize = 1;
  }

  ngOnInit(): void {
    let username = this.uitilService.getLoggedUsername();
    this.notificationService.findAllForUser(username, this.currentPage - 1, this.pageSize).subscribe((response: any) => {
      this.notificationsList = response.body;
      this.dataSource = new MatTableDataSource(this.notificationsList);
      this.totalSize = Number(response.headers.get("total-elements"));
    },
    (error) => {
      if(error.status === 403)
        this.snackBarService.openSnackBar("Could not read notifications. Invalid username.");
        if(error.status === 500)
          this.snackBarService.openSnackBar("Unexpected error ocured while loading notifications");
    })
  }

  changePage(newPage: number) {
    let username = this.uitilService.getLoggedUsername();
    this.notificationService.findAllForUser(username, newPage - 1, this.pageSize).subscribe((response: any) => {
      this.notificationsList = response.body;
      this.dataSource = new MatTableDataSource(this.notificationsList);
      this.totalSize = Number(response.headers.get("total-elements"));
    });
  }

}
