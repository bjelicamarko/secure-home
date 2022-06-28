import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { RealEstateService } from 'src/modules/admin/services/real-estate.service';
import { PaginationComponent } from 'src/modules/shared/components/pagination/pagination.component';
import { RealEstateCardComponent } from 'src/modules/shared/components/real-estate-card/real-estate-card.component';
import { RealEstateWithPhotoAndRoleDTO } from 'src/modules/shared/models/RealEstateDTO';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { UtilService } from 'src/modules/shared/services/util/util.service';

@Component({
  selector: 'app-user-home-page',
  templateUrl: './user-home-page.component.html',
  styleUrls: ['./user-home-page.component.scss']
})
export class UserHomePageComponent implements OnInit {

  @ViewChild(PaginationComponent) pagination?: PaginationComponent;
  pageSize: number;
  currentPage: number;
  totalSize: number;
  realEstates: RealEstateWithPhotoAndRoleDTO[];

  constructor(
    private router: Router,
    private snackBarService: SnackBarService,
    private realEstateService: RealEstateService,
    private utilsService: UtilService) {
    this.realEstates = [];
    this.pageSize = 4;
    this.currentPage = 1;
    this.totalSize = 1;
  }

  ngOnInit(): void {
    let username = this.utilsService.getLoggedUsername();
    this.realEstateService.getAllRealEstatesOfUser(this.currentPage - 1, this.pageSize)
      .subscribe((response) => {
        this.realEstates = response.body as RealEstateWithPhotoAndRoleDTO[];
        this.setPagination(response.headers.get('total-elements'), response.headers.get('current-page'));
      });
  }

  setPagination(totalItemsHeader: string | null, currentPageHeader: string | null) {
    if (totalItemsHeader) {
      this.totalSize = parseInt(totalItemsHeader);
    }
    if (currentPageHeader) {
      this.currentPage = parseInt(currentPageHeader);
    }
  }

  changePage(newPage: number) {
    let username = this.utilsService.getLoggedUsername();
    this.realEstateService.getAllRealEstatesOfUser(newPage - 1, this.pageSize).subscribe((res) => {
      if (res.body != null) {
        this.realEstates = res.body as RealEstateWithPhotoAndRoleDTO[];
        this.setPagination(res.headers.get('total-elements'), res.headers.get('current-page'));
      }
    }, (err) => {
      if (err.error)
        this.snackBarService.openSnackBar(String(err.console));
    });
  }

  showMore(name: string) {
    this.router.navigate(["mh-app/user/real-estate-page", name]);
  }
}
