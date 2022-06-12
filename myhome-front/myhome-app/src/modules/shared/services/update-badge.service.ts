import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

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