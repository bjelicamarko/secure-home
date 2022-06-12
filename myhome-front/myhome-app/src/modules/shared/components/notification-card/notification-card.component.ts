import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-notification-card',
  templateUrl: './notification-card.component.html',
  styleUrls: ['./notification-card.component.scss']
})
export class NotificationCardComponent implements OnInit {

  @Input()
  idx: number;

  @Input()
  deviceName: string;
  
  @Input()
  message: string;

  @Output() notificationDismissed: EventEmitter<number>;

  constructor() {
    this.idx = -1;
    this.deviceName = "";
    this.message = "";
    this.notificationDismissed = new EventEmitter();
  }

  ngOnInit(): void {
  }

  dissmisNotification(): void {
    alert(this.idx);
    
    this.notificationDismissed.emit(this.idx); 
  }

}
