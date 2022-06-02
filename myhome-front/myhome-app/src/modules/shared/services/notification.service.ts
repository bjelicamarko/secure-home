import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Notification } from '../models/notification';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private notificationMessageSource = new Subject<Notification>();
  public notificationMessage$ = this.notificationMessageSource.asObservable();

  constructor(private httpClient: HttpClient) { }

  // getAllNotifications(employeeId: number): Observable<HttpResponse<Notification[]>> {

  //   let queryParams = {};

  //   queryParams = {
  //     headers: new HttpHeaders({ "Content-Type": 'application/json' }),
  //     observe: 'response'
  //   };

  //   return this.httpClient.get<HttpResponse<Notification[]>>(`restaurant/api/orderNotifications/` + employeeId, queryParams);
  // }

  sendNotification(message: any): void {
    this.notificationMessageSource.next(message);
  }

}
