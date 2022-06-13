import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { AlarmNotification } from 'src/modules/shared/models/AlarmNotification';
import { NotificationService } from 'src/modules/shared/services/notification.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { UtilService } from 'src/modules/shared/services/util/util.service';
import { UpdateBadgeService } from '../../services/update-badge.service';
import { UpdateSideNotificationsService } from '../../services/update-side-notifications.service';

@Component({
  selector: 'app-notifications-page',
  templateUrl: './notifications-page.component.html',
  styleUrls: ['./notifications-page.component.scss']
})
export class NotificationsPageComponent implements OnInit {

  displayedColumns: string[];

  pageSize: number;
  currentPage: number;
  totalSize: number;
  notificationsList: AlarmNotification[];
  dataSource: MatTableDataSource<AlarmNotification>;
  userRole: string;

  constructor(private notificationService: NotificationService, private snackBarService: SnackBarService,
              private uitilService: UtilService, private updateBadgeService: UpdateBadgeService,
              private sideNotificationService: UpdateSideNotificationsService) { 
    this.notificationsList = [];
    this.dataSource = new MatTableDataSource(this.notificationsList)
    this.pageSize = 10;
    this.currentPage = 1;
    this.totalSize = 1;
    this.userRole = this.uitilService.getLoggedUserRoles()[0];
    this.displayedColumns = (this.userRole !== 'ROLE_ADMIN') ? ['deviceName', 'message', 'timestamp'] : ['message', 'timestamp'];
  }

  ngOnInit(): void {
    this.notificationService.findAllForUser(this.currentPage - 1, this.pageSize).subscribe((response: any) => {
      this.notificationsList = response.body;
      this.dataSource = new MatTableDataSource(this.notificationsList);
      this.totalSize = Number(response.headers.get("total-elements"));

      this.updateBadgeService.sendNotif();
      this.sideNotificationService.sendNotif();
    },
    (error) => {
      if(error.status === 403)
        this.snackBarService.openSnackBar("Could not read notifications. Invalid username.");
      if(error.status === 500)
        this.snackBarService.openSnackBar("Unexpected error ocured while loading notifications");
    })
  }

  changePage(newPage: number) {
    this.notificationService.findAllForUser(newPage - 1, this.pageSize).subscribe((response: any) => {
      this.notificationsList = response.body;
      this.dataSource = new MatTableDataSource(this.notificationsList);
      this.totalSize = Number(response.headers.get("total-elements"));

      this.updateBadgeService.sendNotif();
      this.sideNotificationService.sendNotif();
    });
  }

}
