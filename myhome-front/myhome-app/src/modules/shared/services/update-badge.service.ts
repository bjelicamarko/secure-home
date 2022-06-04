import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { AlarmNotification } from '../models/AlarmNotification';
import { Notification } from '../models/notification';

@Injectable({
  providedIn: 'root'
})
export class UpdateBadgeService {

  private notif = new Subject<boolean>();
  public notif$ = this.notif.asObservable();

  constructor() { }

  sendNotif(): void {
    this.notif.next(true);
  }

}