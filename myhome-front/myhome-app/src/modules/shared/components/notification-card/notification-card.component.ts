import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AlarmNotification } from '../../models/AlarmNotification';

@Component({
  selector: 'app-notification-card',
  templateUrl: './notification-card.component.html',
  styleUrls: ['./notification-card.component.scss']
})
export class NotificationCardComponent implements OnInit {

  @Input()
  notification: AlarmNotification;

  @Output() notificationSeen: EventEmitter<number>;

  constructor() {
    this.notification = { id: -1, message: "", deviceName: "", timestamp: -1 };
    this.notificationSeen = new EventEmitter();
  }

  ngOnInit(): void {
  }

  dissmisNotification(): void {
    this.notificationSeen.emit(this.notification.id); 
  }

}
