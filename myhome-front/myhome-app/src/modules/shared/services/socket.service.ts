import { Injectable } from '@angular/core';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

import { SnackBarService } from './snack-bar.service';
import { NotificationService } from './notification.service';
import { Notification } from '../models/notification';
import { UpdateSideNotificationsService } from './update-side-notifications.service';
import { AlarmNotification } from '../models/AlarmNotification';

@Injectable({
  providedIn: 'root'
})
export class SocketService {

  url: string = '/socket';

  private stompClient: any;
  initialized: boolean = false;

  constructor(private snackBarService: SnackBarService, private notificationService: NotificationService, 
              private sideNotificationService: UpdateSideNotificationsService) { }

  connect(username: any): void {
    if (this.initialized) {
      return;
    }
    this.initialized = true;

    const socket = new SockJS("https://localhost:8081/socket/");
    this.stompClient = Stomp.over(socket);

    const that = this;
    this.stompClient.connect({}, function (frame: string) {
      that.initialized = true;
      console.log('Connected: ' + frame);

      that.stompClient.subscribe('/socket-publisher', function (message: { body: any }) {
        that.showMessage(message);
      });

      that.subscribeToLocalSocket(username);
    });

    console.log(this.stompClient)
  }

  disconnect(): void {
    if (this.stompClient != null) {
      this.stompClient.disconnect();
    }

    this.initialized = false;
    console.log('Disconnected!');
  }

  subscribeToLocalSocket(username: any): void {
    this.stompClient.subscribe("/socket-publisher/" + username, (message: { body: string; }) => {
      // this.showMessage(message);
      let notification = JSON.parse(message.body);

      this.notificationService.sendNotification(notification as Notification);
      // this.sideNotificationService.sendNotif(notification as AlarmNotification);
    });
  }

  sendMessage(message: any): void {
    this.stompClient.send('/socket-subscriber/send/message', {}, JSON.stringify(message));
  }

  showMessage(message: { body: any; }): void {
    let messageResult: Notification = JSON.parse(message.body);

    this.snackBarService.openSnackBarFast(messageResult.message);
  }

}
