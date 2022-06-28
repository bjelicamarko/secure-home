import { Component, OnInit } from '@angular/core';
import { AlarmNotification } from '../../models/AlarmNotification';
import { Notification } from '../../models/notification';
import { NotificationService } from '../../services/notification.service';
import { SnackBarService } from '../../services/snack-bar.service';
import { UpdateBadgeService } from '../../services/update-badge.service';
import { UpdateSideNotificationsService } from '../../services/update-side-notifications.service';

@Component({
  selector: 'app-notifications-container',
  templateUrl: './notifications-container.component.html',
  styleUrls: ['./notifications-container.component.scss']
})
export class NotificationsContainerComponent implements OnInit {

  notifications: AlarmNotification[];

  constructor(private sideNotificationsService: UpdateSideNotificationsService, private notificationService: NotificationService,
    private snackBarService: SnackBarService, private badgeService: UpdateBadgeService) {
    this.notifications = [];
  }

  ngOnInit(): void {
    this.loadNotifications();

    this.notificationService.notificationMessage$.subscribe(() => {
      this.snackBarService.openSnackBar("New notification arrived");
      this.loadNotifications();
    })

    this.sideNotificationsService.notif$.subscribe(() => {
      this.loadNotifications();
    })
  }

  loadNotifications(): void {
    this.notificationService.getNotSeenForUser(1 - 1, 6).subscribe((response: any) => {
      this.notifications = response.body;
    },
      (error) => {
        if (error.status == 400)
          this.snackBarService.openSnackBar("Non-existent/Locked/Deleted user, could not read notifications");
        if (error.status == 500)
          this.snackBarService.openSnackBar("An unkown error ocured while loading side notifications");
      });
  }

  setSeen($event: any) {
    let id = $event as number;

    this.notificationService.setSeen(id).subscribe((response) => {
      console.log(response.body);
      this.loadNotifications();
      this.badgeService.sendNotif();
    },
      (error) => {
        this.snackBarService.openSnackBar(error.error);
      })
  }

}
