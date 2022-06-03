import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { AlarmNotification } from '../models/AlarmNotification';
import { Notification } from '../models/notification';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private notificationMessageSource = new Subject<Notification>();
  public notificationMessage$ = this.notificationMessageSource.asObservable();

  constructor(private httpClient: HttpClient) { }

  findAllForUser(username: string, page: number, size: number): Observable<HttpResponse<AlarmNotification[]>> {

    let queryParams = {};

    queryParams = {
      headers: new HttpHeaders({ "Content-Type": 'application/json' }),
      observe: 'response',
      params: new HttpParams()
        .set("page", String(page))
        .append("size", String(size))
        .append("sort", "id,desc")
    };

    return this.httpClient.get<HttpResponse<AlarmNotification[]>>(`myhome/api/alarmNotifications`, queryParams);
  }

  sendNotification(message: any): void {
    this.notificationMessageSource.next(message);
  }

}