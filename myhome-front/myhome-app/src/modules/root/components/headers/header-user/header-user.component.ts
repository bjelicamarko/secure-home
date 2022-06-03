import { Component, OnInit } from '@angular/core';
import { NotificationService } from 'src/modules/shared/services/notification.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { UpdateBadgeService } from 'src/modules/shared/services/update-badge.service';
import { UtilService } from 'src/modules/shared/services/util/util.service';

@Component({
  selector: 'app-header-user',
  templateUrl: './header-user.component.html',
  styleUrls: ['./header-user.component.scss']
})
export class HeaderUserComponent implements OnInit {

  notSeenCount: number;

  constructor(private notificationService: NotificationService, private utilService: UtilService,
              private snackBarService: SnackBarService, private updateBadgeService: UpdateBadgeService) { 
    this.notSeenCount = 0;
  }

  ngOnInit(): void {
    let username = this.utilService.getLoggedUsername();

    this.countNotifications(username);

    this.notificationService.notificationMessage$.subscribe((notification: any) => {
      this.countNotifications(username);
    })

    this.updateBadgeService.notif$.subscribe((notification) => {
      this.countNotifications(username);
    })
  }

  countNotifications(username: string) {
    this.notificationService.countNotSeenForUser(username).subscribe((response: any) => {
      this.notSeenCount = response.body;
    }, 
    (error) => {
      if(error.status === 403)
        this.snackBarService.openSnackBar("Could not count notifications. Invalid username.");
      if(error.status === 500)
        this.snackBarService.openSnackBar("Unexpected error ocured while counting not seen notifications");
    })
  }

}
