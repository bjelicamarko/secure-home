import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { AlarmNotification } from '../models/AlarmNotification';
import { IdDTO } from '../models/Id';
import { Notification } from '../models/notification';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private notificationMessageSource = new Subject<Notification>();
  public notificationMessage$ = this.notificationMessageSource.asObservable();

  constructor(private httpClient: HttpClient) { }

  findAllForUser(page: number, size: number): Observable<HttpResponse<AlarmNotification[]>> {

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

  countNotSeenForUser(): Observable<HttpResponse<number>> {
    let queryParams = {};

    queryParams = {
      headers: new HttpHeaders({ "Content-Type": 'application/json' }),
      observe: 'response'
    };

    return this.httpClient.get<HttpResponse<number>>(`myhome/api/alarmNotifications/countNotSeen`, queryParams);
  }

  sendNotification(message: any): void {
    this.notificationMessageSource.next(message);
  }

  getNotSeenForUser(page: number, size: number): Observable<HttpResponse<AlarmNotification[]>> {

    let queryParams = {};

    queryParams = {
      headers: new HttpHeaders({ "Content-Type": 'application/json' }),
      observe: 'response',
      params: new HttpParams()
        .set("page", String(page))
        .append("size", String(size))
        .append("sort", "id,desc")
    };

    return this.httpClient.get<HttpResponse<AlarmNotification[]>>(`myhome/api/alarmNotifications/notSeen`, queryParams);
  }

  setSeen(id: number): Observable<HttpResponse<string>> {
    let queryParams = {};

    queryParams = {
      headers: new HttpHeaders({ "Content-Type": 'application/json' }),
      observe: "response",
      responseType: "text"
    };

    let idDTO: IdDTO = {
      id: id
    }

    return this.httpClient.post<HttpResponse<string>>("myhome/api/alarmNotifications/setSeen", idDTO, queryParams);
  }

}