import { Component, OnInit } from '@angular/core';
import { AlarmNotification } from '../../models/AlarmNotification';
import { UpdateSideNotificationsService } from '../../services/update-side-notifications.service';

@Component({
  selector: 'app-notifications-container',
  templateUrl: './notifications-container.component.html',
  styleUrls: ['./notifications-container.component.scss']
})
export class NotificationsContainerComponent implements OnInit {

  notifications: AlarmNotification[];

  constructor(private sideNotificationsService: UpdateSideNotificationsService) { 
    this.notifications = [];
  }

  ngOnInit(): void {
    this.sideNotificationsService.notif$.subscribe((notification: AlarmNotification) => {
      console.log(notification);
      this.notifications.push(notification);
    })
  }

}
