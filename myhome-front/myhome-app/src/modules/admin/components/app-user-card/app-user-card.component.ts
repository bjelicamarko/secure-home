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
    profilePhoto: '',
    verified: false,
    locked: false
  };

  @Output() renderList: EventEmitter<any> = new EventEmitter();
  @Output() assignEstate: EventEmitter<string> = new EventEmitter();
  @Output() showUserEstate: EventEmitter<string> = new EventEmitter();

  constructor(public dialog: MatDialog, private appUsersService: AppUsersService,
    private snackBarService: SnackBarService) { }

  openDialog(): void {

  }

  removeUser(): void {
    this.dialog.open(ConformationDialogComponent, {
      data:
      {
        title: "Removing user",
        message: "You want to remove " + this.user.firstName + " " + this.user.lastName + " ?"
      },
    }).afterClosed().subscribe(result => {
      if (result) {
        this.appUsersService.deleteUser(this.user.id)
          .subscribe((response) => {
            this.snackBarService.openSnackBar(response.body as string);
            this.renderList.emit(null);
          },
            (err) => {
              this.snackBarService.openSnackBar(err.error as string);
            })
      }
    })
  }

  unlockUser(): void {
    this.dialog.open(ConformationDialogComponent, {
      data:
      {
        title: "Unlocking user",
        message: "You want to unlock " + this.user.firstName + " " + this.user.lastName + " ?"
      },
    }).afterClosed().subscribe(result => {
      if (result) {
        this.appUsersService.unlockUser(this.user.id)
          .subscribe((response) => {
            this.snackBarService.openSnackBar(response.body as string);
           // this.renderList.emit(null);
           this.user.locked = false;
          },
            (err) => {
              console.log(err);
              //this.snackBarService.openSnackBar(err.error as string);
            })
      }
    })
  }

  showAssignEstatePage(username: string): void {
    this.assignEstate.emit(username);
  }

  showUserEstatePage(username: string): void {
    this.showUserEstate.emit(username);
  }
}
