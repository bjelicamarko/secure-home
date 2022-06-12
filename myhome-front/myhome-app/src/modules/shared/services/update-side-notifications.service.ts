import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { AlarmNotification } from '../models/AlarmNotification';

@Injectable({
  providedIn: 'root'
})
export class UpdateSideNotificationsService {

  private notif = new Subject<AlarmNotification>();
  public notif$ = this.notif.asObservable();

  constructor() { }

  sendNotif(alarmNotification: AlarmNotification): void {
    this.notif.next(alarmNotification);
  }

}