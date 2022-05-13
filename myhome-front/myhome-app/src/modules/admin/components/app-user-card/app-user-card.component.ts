import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AppUserDTO } from '../../models/AppUserDTO';

@Component({
  selector: 'app-app-user-card',
  templateUrl: './app-user-card.component.html',
  styleUrls: ['./app-user-card.component.scss']
})
export class AppUserCardComponent {

  @Input() user: AppUserDTO = {
    id: 0,
    firstName: '',
    lastName: '',
    email: '',
    username: '',
    role: '',
    profilePhoto: ''
  };

  constructor(public dialog: MatDialog) { }

  openDialog(): void {
  }

}
