import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConformationDialogComponent } from 'src/modules/shared/components/conformation-dialog/conformation-dialog.component';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { AppUserDTO } from '../../models/AppUserDTO';
import { AppUsersService } from '../../services/app-users.service';

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

  @Output() renderList: EventEmitter<any> = new EventEmitter();
  @Output() assignEstate: EventEmitter<string> = new EventEmitter();
  
  constructor(public dialog: MatDialog, private appUsersService: AppUsersService, 
    private snackBarService: SnackBarService) { }

  openDialog(): void {

  }

  removeUser(): void {
    this.dialog.open(ConformationDialogComponent, {
      data: 
      { 
        title: "Removing user",
        message: "You want to remove " + this.user.username + "?"
      },
    }).afterClosed().subscribe(result => {
      if (result) {
        this.appUsersService.deleteUser(this.user.id)
        .subscribe((response) => {
          console.log(response);
          this.snackBarService.openSnackBar(response.body as string);
          this.renderList.emit(null);
        },
        (err) => {
          this.snackBarService.openSnackBar(err.error as string);
        })
      }
    })
  }

  showAssignEstatePage(username: string): void {
    this.assignEstate.emit(username);
  }
}
