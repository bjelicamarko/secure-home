import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { PaginationComponent } from 'src/modules/shared/components/pagination/pagination.component';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { AppUserDTO } from '../../models/AppUserDTO';
import { AppUsersService } from '../../services/app-users.service';

@Component({
  selector: 'app-users-view',
  templateUrl: './users-view.component.html',
  styleUrls: ['./users-view.component.scss']
})
export class UsersViewComponent implements AfterViewInit {

  @ViewChild(PaginationComponent) pagination?: PaginationComponent;
  pageSize: number;
  currentPage: number;
  totalSize: number;
  employees: AppUserDTO[];
  
  messages: string[] = [];

  public searchFormGroup: FormGroup;

  constructor(private fb: FormBuilder, private appUsersService: AppUsersService, private snackBarService: SnackBarService) {
    this.employees = [];
    this.pageSize = 4;
    this.currentPage = 1;
    this.totalSize = 1;
    this.searchFormGroup = this.fb.group({
      searchField: [''],
      userType: ['']
    });
   
  }

  ngAfterViewInit(): void {
    this.appUsersService.getAllUsersButAdmin(this.currentPage - 1, this.pageSize)
      .subscribe((response) => {
        this.employees = response.body as AppUserDTO[];
        console.log(this.employees);
        this.totalSize = Number(response.headers.get("total-elements"));
        this.setPagination(response.headers.get('total-elements'), response.headers.get('current-page'));
      });

      //this.onChanges();
  }

  setPagination(totalItemsHeader: string | null, currentPageHeader: string | null) {
    if (totalItemsHeader) {
      this.totalSize = parseInt(totalItemsHeader);
    }
    if (currentPageHeader) {
      this.currentPage = parseInt(currentPageHeader);
    }
  }


}
